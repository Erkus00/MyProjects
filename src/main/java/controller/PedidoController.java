package controller;

import entity.CartaEntity;
import entity.CartaPedidoEntity;
import entity.PedidoEntity;
import model.Fecha;
import model.Pedido;
import org.hibernate.Session;
import utils.HibernateUtils;
import org.hibernate.query.Query;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static dao.CartaDAO.carta;
import static dao.CartaDAO.infoProducto;
import static dao.PedidoDAO.*;
import static view.View.*;
import static view.View.clean;
import dao.CartaDAO.*;
import dao.PedidoDAO.*;

public class PedidoController {

    /**
     * Permite la gestion para la insercion de un pedido. El programa pide por consola un nombre para el pedido, muestra la carta disponible y permite al
     * usuario modificar la comanda antes de enviarla. Antes de guardarla en la base de datos (hacer el 'INSERT') se pide una ultima verificacion.
     * Imprime por pantalla el recibo de la compra y permite volver a hacer otro pedido o volver a la pantalla anterior
     */

    public static void insercionPedido() throws Exception {

        // Lista que guardará como Key: el producto y como segundo valor el numero de elementos que se desea
        ArrayList<CartaPedidoEntity> comanda = new ArrayList<>();
        PedidoEntity pedido = new PedidoEntity();

        System.out.println("Indique un nombre de pedido");
        String nombre = leerString().toUpperCase();

        clean(1);
        Integer id_producto;
        Integer cant = 0;
        String salir = "";

        while (!salir.equals("exit")) {
            System.out.println("--------------------------------");
            carta().forEach((v) -> {
                System.out.println(v.cartaView());
            });
            System.out.println("--------------------------------");


            clean(1);
            System.out.println("Indiqueme el numero de producto");
            id_producto = leerInt();
            System.out.println("Cantidad: ");
            cant = leerInt();

            if (!comanda.isEmpty()) {
                Integer checked_producto = id_producto;
                Integer finalCant = cant;
                AtomicBoolean nuevo = new AtomicBoolean(true);

                comanda.forEach((k) -> {
                    Integer temp = k.getIdProducto();
                    if (Objects.equals(temp, checked_producto)) {
                        nuevo.set(false);
                        Integer actualizar_cant = k.getCantidad() + finalCant;
                        k.setCantidad(actualizar_cant);
                    }
                });
                if (nuevo.get()) {
                    CartaPedidoEntity prod = new CartaPedidoEntity();
                    prod.setIdProducto(id_producto);
                    prod.setCantidad(cant);
                    comanda.add(prod);
                }
            } else {
                CartaPedidoEntity prod = new CartaPedidoEntity();
                prod.setIdProducto(id_producto);
                prod.setCantidad(cant);
                comanda.add(prod);
            }

            clean(1);
            System.out.println("/////////////////////////////////////////////////////");
            clean(1);
            System.out.println("Productos guardados:");
            cleanDot(1);
            comanda.forEach((k) -> {
                if (k.getCantidad() > 0) {
                    System.out.println(infoProducto(k.getIdProducto()).getNombre() + " -> Cantidad: " + k.getCantidad());
                }
            });
            clean(1);

            System.out.println("Pulse enter para realizar otra comanda. Escriba 'exit' para pasar al proceso de confirmacion");
            System.out.println("<< Nota: En caso de que quiera editar, usar un '-' delante del valor para quitar. Introducir el valor sin signo, habiendo seleccionado previamente el producto, para sumar a lo ya pedido >>");
            salir = leerString();
        }

        cleanDot(4);
        System.out.println("Verifique que los datos a insertar son correctos: ");
        clean(1);
        System.out.println("/////////////////////////////////////////////////////");
        cleanDot(1);
        System.out.println("Nombre de pedido: " + nombre);
        comanda.forEach((k) -> {
            if (k.getCantidad() > 0) {
                System.out.println(infoProducto(k.getIdProducto()).getNombre() + " -> Cantidad: " + k.getCantidad());
            }
        });
        cleanDot(1);
        System.out.println("/////////////////////////////////////////////////////");
        clean(1);
        System.out.println("Pulse 1 para imprimir el recibo. Pulse 0 para salir al menu de ingreso");
        Integer lect = leerInt();
        if (lect == 1) {

            Integer insertado;
            Date now = new Date();
            java.sql.Date sqlDate = new java.sql.Date(now.getTime());

            pedido.setCliente(nombre);
            pedido.setEstado("PENDIENTE");
            pedido.setFecha(sqlDate);
            clean(2);
            Pedido pedidoLocal = new Pedido(pedido,comanda);
            insertado = insertarPedido(pedidoLocal);
            pedidoLocal.recibo();
            if (insertado!=null) {
                System.out.println("Pedido realizado correctamente");
            } else {
                System.out.println("Problema al realizar el pedido. Intentelo de Nuevo por favor y disculpe las molestias");
            }
            clean(1);
        }
    }


    /**
     * Crea la interfaz grafica que permite al usuario eliminar los pedidos que desee. Para ello, muestra los pedidos existentes hasta la fecha. Da la opcion de
     * eleccion y, posteriormente, se pide una verificacion. Sea cual fuere la opcion elegida, da oportunidad de seguir eliminando pedidos o de volver al
     * menu previo
     */
    public static void gestorEliminacionPedido() throws Exception {
        boolean salir = false;
        while (!salir) {
            ArrayList<Pedido> lista_pedidos = listarAllPedidos(false);
            clean(2);
            System.out.println("--- Que pedido desea eliminar");
            if (!lista_pedidos.isEmpty()) {
                lista_pedidos.forEach((v) -> {
                    System.out.println(v.infoView());
                });
            } else {
                System.out.println("No hay Pedidos");
            }
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Indiqueme el numero de identificacion que desea eliminar");
            Integer eleccion = leerInt();
            System.out.println("¿Desea eliminar el siguiente pedido de manera permanente? [y/n]");
            System.out.println(infoPedido(eleccion).infoView());
            System.out.println("y -> Si");
            System.out.println("n -> No");
            String aceptar = leerString().toLowerCase();

            if (aceptar.equals("y")) {
                boolean eliminado = eliminarPedido(eleccion);
                lista_pedidos = listarAllPedidos(false);
                if (!lista_pedidos.isEmpty() && eliminado) {
                    lista_pedidos.forEach((v) -> {
                        System.out.println(v.infoView());
                    });
                } else {
                    if (!eliminado) {
                        System.out.println("Error de eliminacion");
                    } else {
                        clean(2);
                        System.out.println("No hay Pedidos");
                        clean(3);
                    }
                }

                System.out.println("¿Desea continuar eliminando pedidos?");
                System.out.println("y -> Si");
                System.out.println("n -> No");
                aceptar = leerString().toLowerCase();
                if (aceptar.equals("n")) {
                    salir = true;
                } else {
                    salir = false;
                }
            }
        }
    }

    /**
     * Permite marcar un pedido como recogido y guia al usuario en la eleccion
     * Una vez finalizada la funcion, interactua con el usuario para volver a ejecutarse en el caso que este lo desee
     */
    public static void marcarPedidoRecogido() throws Exception {
        boolean salir = false;
        while (!salir) {
            pedidosPendientes();
            System.out.println("Indique el Id del pedido que desea marcar como recogido");
            Integer identificador_pedido = leerInt();
            System.out.println("¿Desea marcar como recogido este pedido?");
            System.out.println(infoPedido(identificador_pedido).infoView());
            System.out.println("y -> Si");
            System.out.println("n -> No");
            String eleccion = leerString().toLowerCase();
            if (eleccion.equals("y")) {
                clean(3);
                recogerPedido(identificador_pedido);
                System.out.println("Situacion actual de los pedidos");
                listarAllPedidos(true).forEach(k -> {
                    System.out.println(k.infoView());
                });
                cleanDot(3);
            }
            System.out.println("¿Desea continuar marcando pedidos recogidos?");
            System.out.println("y -> Si");
            System.out.println("n -> No");
            eleccion = leerString().toLowerCase();
            if (eleccion.equals("n")) {
                salir = true;
            }
        }
    }

    /**
     * Gestiona la entrada por teclado de una fecha.
     * Dependiendo de la opcion, se introducen 2 fechas (caso 0) o 1 sola (caso 1)
     *
     * @param selector Caso que se desea mostrar al usuario
     */
    public static void gestorFechas(Integer selector) {
        switch (selector) {
            case 0 -> {
                //Pedidos de un intervalo de fechas [f1,f2]
                System.out.println("Siga los pasos para indicar la fecha de inicio de consulta");
                Fecha fecha_inicio = ControllerFecha.leerFecha();
                System.out.println("Siga los pasos para indicar la fecha de fin de consulta");
                Fecha fecha_fin = ControllerFecha.leerFecha();
                listarPedidosFecha(fecha_inicio.getSql_date(), fecha_fin.getSql_date(),false).forEach(k -> {
                    System.out.println(k.infoView());
                });
            }
            case 1 -> {
                //Pedidos pendientes de Fecha
                System.out.println("Siga los pasos para indicar la fecha de consulta");
                Fecha fecha = ControllerFecha.leerFecha();
                listarPedidosFecha(fecha.getSql_date(),fecha.getSql_date(),true).forEach(k -> {
                    System.out.println(k.infoView());
                });
            }
        }
    }


    /**
     * Muestra los diferentes clientes y permite
     */
    public static void gestorConsultaAlumno() {
        clean(3);
        System.out.println("Listado de los Alumnos: ");
        clean(1);
        listarAlumnos();
        System.out.println("Indique el nombre del alumno");
        String alumno = leerString().toUpperCase();
        pedidosCliente(alumno).forEach(k -> {
            System.out.println(k.infoView());
        });
    }

    /**
     * Lista y muestra los clientes que han efectuado uno o mas pedidos
     */
    private static void listarAlumnos() {
        ArrayList<String> alumnos = new ArrayList<>();
        String hql_query = "SELECT DISTINCT cliente FROM PedidoEntity ";
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query query = session.createQuery(hql_query,String.class);
            alumnos= (ArrayList<String>) query.list();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        alumnos.forEach(k -> {
            System.out.println("-- " + k);
        });
    }
}

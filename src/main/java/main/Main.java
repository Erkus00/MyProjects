package main;

import static controller.PedidoDAO.*;
import static controller.ProductoDAO.*;
import static controller.ResumenDAO.info;
import static view.View.*;

public class Main {

    /**
     * Clase principal del programa de comandas de los desayunos.
     * En él, se encuentran los menus con las llamadas a las funciones gestoras
     * de cada subparte o eleccion
     *
     * @author Carlos Aragon Garcia
     */
    public static void main(String[] args) throws Exception {

        boolean salir = false;
        while (!salir) {
            // Se muestra el menu visual al Usuario y se recoge su eleccion
            Integer opcion = menuInicio();
            boolean salir_sub = false;
            clean(5);

            switch (opcion) {
                case 0:
                    salir = true;
                    salir();
                    break;
                case 1:
                    while (!salir_sub) {
                        salir_sub = inicio();
                    }
                    break;
                case 2:
                    while (!salir_sub) {
                        salir_sub = modificacion();
                    }
                    break;
                case 3:
                    while (!salir_sub) {
                        salir_sub = ingreso();
                    }
                    break;
                case 4:
                    resumenEstadistico();
                    break;
                default:
                    clean(3);
                    System.out.println("Opcion no disponible");
                    break;
            }
        }
    }



//------------------------------------------------------------------------
    // Funciones que llaman a otras funciones para facilitar la lectura del codigo

    /**
     * Gestiona la eleccion del usuario en el menu de Consulta
     *
     * @return <ul>
     * <li>TRUE: Si se desea salir del subMenu</li>
     * <li>FALSE: Si se desea continuar en el subMenu</li>
     * </ul>
     */
    static boolean inicio() {
        clean(2);
        Integer elector = menuConsulta();
        cleanDot(3);
        boolean salir_sub = false;

        switch (elector) {
            // Salir
            case 0 -> salir_sub = true;
            // Listar comandas de un dia
            case 1 -> gestorFechas(1);
            //Mostrar todas los pedidos pendientes
            case 2 -> pedidosPendientes();
            // Listar las comandas pendientes y
            case 3 -> gestorFechas(0);
            case 4 -> {
                System.out.println("--------------------------------");
                carta().forEach(k -> {
                    System.out.println(k.cartaView());
                });
                System.out.println("--------------------------------");
            }
            case 5 -> gestorConsultaAlumno();
            default -> {
                clean(5);
                System.out.println("Opcion no disponible");
                clean(3);
            }
        }
        return salir_sub;
    }

    /**
     * Gestiona la eleccion del usuario en el menu de Modificacion
     *
     * @return <ul>
     * <li>TRUE: Si se desea salir del subMenu</li>
     * <li>FALSE: Si se desea continuar en el subMenu</li>
     * </ul>
     */
    static boolean modificacion() {
        Integer elector = menuMod();
        cleanDot(3);
        boolean salir_sub = false;

        switch (elector) {
            case 0 -> salir_sub = true;
            case 1 -> gestorEliminacionPedido();
            case 2 -> marcarPedidoRecogido();
            default -> {
                clean(5);
                System.out.println("Opcion no disponible");
                clean(3);
            }
        }
        return salir_sub;
    }

    /**
     * Gestiona la eleccion del usuario en el menu de Infreso
     *
     * @return <ul>
     * <li>TRUE: Si se desea salir del subMenu</li>
     * <li>FALSE: Si se desea continuar en el subMenu</li>
     * </ul>
     */
    static boolean ingreso() throws Exception {
        Integer elector = menuIngreso();
        clean(3);
        boolean salir_sub = false;

        switch (elector) {
            case 0 -> salir_sub = true;
            case 1 -> insercionPedido();
            case 2 -> insercionProducto();
            default -> {
                clean(5);
                System.out.println("Opcion no disponible");
                clean(3);
            }
        }
        return salir_sub;
    }

    static void resumenEstadistico(){
        info();
    }
}
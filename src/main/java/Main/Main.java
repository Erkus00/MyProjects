package Main;

import static Controller.PedidoDAO.*;
import static Controller.ProductoDAO.*;
import static View.View.*;

public class Main {


    public static void main(String[] args) throws Exception {

        boolean salir = false;

        while (!salir) {
            Integer opcion = menuInicio();
            int elector;
            boolean salir_sub = false;
            cleanDot(5);

            switch (opcion) {
                case 0:
                    salir = true;
                    salir();
                    break;
                case 1:
                    while (!salir_sub) {
                        elector = menuConsulta();
                        cleanDot(3);
                        switch (elector) {
                            case 0 -> salir_sub = true;
                            case 1 -> System.out.println("Listar Comandas pendientes de un dia");
                            case 2 -> System.out.println("Mostrar todas las Comandas Pendientes");
                            case 3 -> System.out.println("Mostrar comandas Pendientes y recogidas de un tramo");
                            case 4 -> System.out.println("Ver carta");
                            case 5 -> System.out.println("Ver pedidos de un Alumno");
                            default -> {
                                clean(5);
                                System.out.println("Opcion no disponible");
                                clean(3);
                            }
                        }
                    }
                    break;
                case 2:

                    while (!salir_sub) {
                        elector = menuMod();
                        cleanDot(3);
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
                    }
                    break;
                case 3:
                    while (!salir_sub) {
                        elector = menuIngreso();
                        clean(3);
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
                    }
                    break;
                default:
                    clean(3);
                    System.out.println("Opcion no disponible");
                    break;
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------- //


}
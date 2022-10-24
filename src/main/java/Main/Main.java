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
                            case 1 -> gestorFechas(1);
                            case 2 -> pedidosPendientes();
                            case 3 -> gestorFechas(0);
                            case 4 -> carta().forEach(k->{
                                System.out.println(k.cartaView());
                            });
                            case 5 -> gestorConsultaAlumno();
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
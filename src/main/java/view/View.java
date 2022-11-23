package view;

import java.util.Scanner;

/**
 * Clase que muestra la interfaz grafica al usuario para el uso del Programa
 */
public class View {

    protected static Boolean primera_vez = true;

    /**
     * Muestra el menu inicial de acciones del programa por consola. En este podrá elegir si desea:
     * <ul>
     *     <li>0. Salir del programa</li>
     *     <li>1. Consultar Datos</li>
     *     <li>2. Modificar Datos</li>
     *     <li>3. Ingresar Datos</li>
     * </ul>
     *
     * @return Opcion seleccionada
     */
    public static Integer menuInicio() {
        Scanner sc = new Scanner(System.in);
        clean(2);
        if (primera_vez) {
            System.out.println("<<                                                        >>");
            System.out.println("BIENVENIDO AL PROGRAMA DE GESTION DE LOS DESAYUNOS DEL CESUR");
            primera_vez = false;
            System.out.println("<<                                                        >>");
            clean(2);
        } else {
            System.out.println("<<                                                        >>");
            clean(2);
        }
        System.out.println("Indique que desea hacer:");
        clean(1);
        System.out.println("0. Salir del programa");
        System.out.println("1. Consultar Datos");
        System.out.println("2. Modificar Datos");
        System.out.println("3. Ingresar Datos");
        System.out.println("4. Resumen Estadistico");
        clean(1);
        return sc.nextInt();

    }

    /**
     * Muestra el menu de las opciones de consulta a la base de datos. Estas serian:
     * <ul>
     *     <li>0. Salir al menu principal</li>
     *     <li>1. Listar comandas pendientes de un dia</li>
     *     <li>2. Mostrar todas las comandas pendientes</li>
     *     <li>3. Mostrar todas las comandas, pendientes y recogidas, dado un tramo</li>
     *     <li>4. Mostrar Carta</li>
     *     <li>5. Ver pedidos de un Alumno</li>
     * </ul>
     *
     * @return Opcion seleccionada
     */
    public static Integer menuConsulta() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Menu de Consulta");
        System.out.println("<<           >>");
        cleanDot(2);
        System.out.println("0. Salir al menu principal");
        System.out.println("1. Listar comandas pendientes de un dia");
        System.out.println("2. Mostrar todas las comandas pendientes");
        System.out.println("3. Mostrar todas las comandas, pendientes y recogidas, dado un tramo");
        System.out.println("4. Mostrar Carta");
        System.out.println("5. Mostrar los pedidos de un Alumno");
        clean(1);
        return sc.nextInt();

    }

    /**
     * Muestra el menu de las opciones de modificacion a la base de datos. Estas serian:
     * <ul>
     *     <li>0. Salir al menu principal</li>
     *     <li>1. Eliminar pedido</li>
     *     <li>2. Marcar pedido como recogido</li>
     *     <li>3. Modificar Pedido</li>
     *     <li>4. Modificar producto de la carta</li>
     * </ul>
     *
     * @return Opcion seleccionada
     */
    public static Integer menuMod() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Menu de Modificacion");
        System.out.println("<<                >>");
        cleanDot(2);
        System.out.println("0. Salir al menu principal");
        System.out.println("1. Eliminar Pedido");
        System.out.println("2. Marcar pedido como Recogido");
        clean(1);
        return sc.nextInt();
    }

    /**
     * Muestra el menu de las opciones de insercion a la base de datos. Estas serian:
     * <ul>
     *     <li>0. Salir al menu principal</li>
     *     <li>1. Hacer pedido (Previamente muestra la carta)</li>
     *     <li>2. Insertar Nuevo Producto</li>
     *     <li>3. Rellenar Stock de un Producto existente</li>
     * </ul>
     *
     * @return Opcion seleccionada
     */
    public static Integer menuIngreso() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Menu de Ingreso");
        System.out.println("<<           >>");
        cleanDot(2);
        System.out.println("0. Salir al menu principal");
        System.out.println("1. Hacer Pedido");
        System.out.println("2. Insertar Nuevo Producto");
        clean(1);
        return sc.nextInt();
    }

    /**
     * Limpa la pantalla y muestra mensaje de despedida del programa
     */
    public static void salir() {
        cleanDot(100);
        System.out.println("Gracias por Usarme");
    }

    /**
     * Realiza tantos saltos de linea como se desee
     *
     * @param max Numero de saltos de linea
     */
    public static void clean(Integer max) {
        for (Integer i = 0; i < max; i++) {
            System.out.println("");
        }
    }

    /**
     * Realiza tantos saltos de linea como se desee, pero añadiendo un '.' en cada nueva linea
     *
     * @param max Numero de saltos de linea
     */
    public static void cleanDot(Integer max) {
        for (Integer i = 0; i < max; i++) {
            System.out.println(".");
        }
    }

    /**
     * Lee un Integer introducido por teclado
     *
     * @return El valor recogido
     */
    public static Integer leerInt() {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    /**
     * Lee un String introducido por teclado
     *
     * @return El valor recogido
     */
    public static String leerString() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * Lee un Double introducido por teclado
     *
     * @return El valor recogido
     */
    public static float leerFloat() {
        Scanner sc = new Scanner(System.in);
        float value = 0.0F;
        value = sc.nextFloat();
        return value;

    }
}

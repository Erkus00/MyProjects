package main;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.text.Normalizer;
import java.util.*;

public class Main {
    protected static final String initial_path = "src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "input" + File.separatorChar;
    protected static String input = "";
    protected static String input_converted = "";

    static {
        Scanner sc = new Scanner(System.in);

        System.out.println(
                "Introduzca el nombre del archivo. Recuerde, que debe estar situado dentro del Proyecto en: \n" +
                        "\n" +
                        "         " + initial_path +
                        "\n" +
                        "\ncon el contenido que desea procesar. No indicar la extension, pero debe ser (.txt) para que funcione" +
                        "\n" +
                        "\n!!!WARNING: Sensible a las Mayusculas y Minusculas"
        );
        System.out.println();
        input = sc.nextLine();
        input_converted = input + ".txt";

        clean(5);
    }

    protected static final String output = "src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "output" + File.separatorChar + input + "_histograma.cvs";


    protected static final String path = (initial_path + input_converted);
    protected static final String[] header = {"PALABRA", "VECES REPETIDAS"};

    public static void main(String[] args) throws Exception {
        var listado = new LinkedHashMap<String, Integer>();
        listado = leeryNormalizarTexto();
        uploadToCSV(listado);

    }

    static LinkedHashMap<String, Integer> leeryNormalizarTexto() {
        LinkedHashMap<String, Integer> listaPalabras = null;

        try (var br = new BufferedReader(new FileReader(Main.path));) {

            ArrayList<String> all_text = new ArrayList<String>();
            ArrayList<String> all_text_mod = new ArrayList<String>();
            String value = "";
            listaPalabras = new LinkedHashMap<>();
            StringBuilder final_text = new StringBuilder();
            while (br.ready()) {
                value = br.readLine();
                if (value != null) {
                    final_text.append(value).append("\n");
                    all_text.addAll(List.of(value.split(" ")));
                }
            }
            clean(1);
            System.out.println("*************");
            System.out.println("El texto es: ");
            System.out.println("*************");
            System.out.println("-----------------------------------------------------------------------------------------------------");
            System.out.println(final_text);
            System.out.println("-----------------------------------------------------------------------------------------------------");
            clean(10);

            String normalized_element = "";
            for (String element : all_text) {
                normalized_element = normalizar(element);
                normalized_element = washing(normalized_element);
                if (normalized_element.length() >= 3 && permitido(normalized_element)) {
                    all_text_mod.add(normalized_element);
                }
            }

            // Orden alfabetico de los elementos
            Collections.sort(all_text_mod);

            // Permite guardar las palabras ordenadas alfabeticamente

            for (String k : all_text_mod) {
                listaPalabras.put(k, 0);
            }

            // Creamos un HasSet a partir de las palabras que nos aparecen en la Lista Normalizada y guardada como nosotros deseamos
            Set<String> diccionario_elementos = new HashSet<>(all_text_mod);

            for (String s : diccionario_elementos) {
                // (Calculo de el numero de veces que aparece en el listado, elemento s)
                int veces = Collections.frequency(all_text_mod, s);
                // Guardamos la informacion en el HasMap() ordenado -> En esta variable se encuentran los datos que exportaremos a un archivo CSV
                listaPalabras.put(s, veces);
            }
            System.out.println("---------");
            System.out.println("Listado: ");
            System.out.println();
            listaPalabras.forEach((k, v) -> {
                System.out.println(k + " -> " + v);
            });
            System.out.println("---------");
            clean(14);


            System.out.println("<<----------------------------------------------------->>");
            System.out.println("    El archivo ha sido guardado en: " +
                    "\n\n" +
                    "               " + output +
                    "\n");

            System.out.println("    Numero de Palabras diferentes: " + listaPalabras.size());
            System.out.println("<<----------------------------------------------------->>");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listaPalabras;
    }

    static public String normalizar(String text) {

        String normalized_element = Normalizer.normalize(text, Normalizer.Form.NFKD);
        normalized_element = normalized_element.replaceAll("\\p{InCombiningDiacriticalMarks}", "").toLowerCase();
        return normalized_element;
    }

    static public String washing(String text) {
        String word = text;
        word = word.replaceAll("\\p{Punct}", "");
        word = word.replaceAll("[?¡«»]", "");
        word = word.replaceAll("[—+/*]", "");
        word = word.replaceAll("[0-9]", "");
        return word;
    }

    static public Boolean permitido(String text) {
        String[] non_util_char = {"con", "por", "los", "las", "que", "del"};
        boolean permitido = true;
        for (String car : non_util_char) {
            if (text.equals(car)) {
                permitido = false;
                break;
            }
        }
        return permitido;
    }

    static public void uploadToCSV(LinkedHashMap<String, Integer> element) throws Exception {
        try (var out = new FileWriter(output); var printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(header))) {
            element.forEach((k, v) -> {
                try {
                    printer.printRecord(k, v);
                } catch (IOException e) {
                    throw new RuntimeException(e);

                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void clean(Integer max) {
        for (int i = 0; i < max; i++) {
            System.out.println(".");

        }

    }
}
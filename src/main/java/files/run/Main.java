package files.run;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.text.Normalizer;
import java.util.*;

import static jdk.internal.net.http.Http1Exchange.State.HEADERS;

public class Main {
    protected static final String easy_path = ("src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "input" + File.separatorChar + "input.txt");
    protected static final String[] header = {"WORD", "CONTADOR"};

    public static void main(String[] args) {
        leeryNormalizarTexto(easy_path);

    }

    static void leeryNormalizarTexto(String origen) {
        try (var br = new BufferedReader(new FileReader(origen));) {

            ArrayList<String> all_text = new ArrayList<String>();
            ArrayList<String> all_text_mod = new ArrayList<String>();
            String value = "";

            while (br.ready()) {
                value = br.readLine();
                if (value != null) {

                    all_text.addAll(List.of(value.split(" ")));
                }
            }

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
            LinkedHashMap<String, Integer> listaPalabras = new LinkedHashMap<>();
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

            System.out.println(listaPalabras);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static public String normalizar(String text) {

        String normalized_element = Normalizer.normalize(text, Normalizer.Form.NFKD);
        normalized_element = normalized_element.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").toLowerCase();
        return normalized_element;
    }

    static public String washing(String text) {
        String word = text;
        word = word.replaceAll("\\p{Punct}", "");
        word = word.replaceAll("[? ¡]", "");
        word = word.replaceAll("[0-9]", "");
        return word;
    }

    static public Boolean permitido(String text) {
        String[] non_util_char = {"con", "por", "los", "las", "que", "del"};
        Boolean permitido = true;
        for (String carac : non_util_char) {
            if (text.equals(carac)) {
                permitido = false;
            }
        }
        return permitido;
    }

    static public void uploadToCSV() throws Exception {
        try (var out = new FileWriter("example.csv"); var printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(header))) {

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
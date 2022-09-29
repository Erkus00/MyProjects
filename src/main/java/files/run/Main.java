package files.run;

import java.io.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class Main {
    protected static final String easy_path = ("src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "input" + File.separatorChar + "input_easy.txt");

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

            all_text.clear();
            all_text.add("camión");

            for (String element : all_text) {
                element = normalizar(element);
                System.out.println(element);

                all_text_mod.add(element);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static public String normalizar(String text) {
        if (!Normalizer.isNormalized(text, Normalizer.Form.NFKC)) {
            return Normalizer.normalize(text, Normalizer.Form.NFKD).toLowerCase();
        }
        return text.toLowerCase();
    }
}
package Controller;
import Model.Fecha;
import java.util.ArrayList;
import static View.View.*;

public class ControllerFecha {
    public static Fecha leerFecha() {
        ArrayList<String> meses = new ArrayList<>();
        System.out.println("A continuacion puede ver los meses del año y los dias que tienen");
        meses.add("Enero");
        meses.add("Febrero");
        meses.add("Marzo");
        meses.add("Abril");
        meses.add("Mayo");
        meses.add("Junio");
        meses.add("Julio");
        meses.add("Agosto");
        meses.add("Septiembre");
        meses.add("Octubre");
        meses.add("Noviembre");
        meses.add("Diciembre");
        final Integer[] cont = {1};
        meses.forEach(k -> {
            System.out.print(cont[0] + ". ");
            System.out.println(k);
            cont[0]++;
        });
        cleanDot(2);
        System.out.println("Indiqueme el dia que desea consultar");
        System.out.println("Tenga en cuenta que si ingresa de manera incorrecta los parámetros. Ej: '30-02'");
        Integer dia = leerInt();
        System.out.println("Indique el mes");
        Integer mes_int = leerInt();
        String mes = meses.get(mes_int - 1);

        return new Fecha(dia, mes);
    }
}

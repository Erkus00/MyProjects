package model;

import java.sql.Date;

/**
 * Objeto de control y traductora para el uso adecuado de los elementos tipo DATE en un formato apto para DATE.SQL -> [yyyy-[m]m-[m]d]
 */
public class Fecha {

    // Dia del mes
    private Integer dia = null;

    // Mes en formato Numerico
    private final Integer mes;

    // Constructor de la Fecha
    public Fecha(Integer dia, String mes) {
        switch (mes) {
            case "Enero" -> {
                if (dia > 0 && dia < 31) {
                    this.dia = dia;
                }
                this.mes = 1;
            }
            case "Febrero" -> {
                if (dia > 0 && dia < 28) {
                    this.dia = dia;
                }
                this.mes = 2;
            }
            case "Marzo" -> {
                if (dia > 0 && dia < 31) {
                    this.dia = dia;
                }
                this.mes = 3;
            }
            case "Abril" -> {
                if (dia > 0 && dia < 30) {
                    this.dia = dia;
                }
                this.mes = 4;
            }
            case "Mayo" -> {
                if (dia > 0 && dia < 31) {
                    this.dia = dia;
                }
                this.mes = 5;
            }
            case "Junio" -> {
                if (dia > 0 && dia < 30) {
                    this.dia = dia;
                }
                this.mes = 6;
            }
            case "Julio" -> {
                if (dia > 0 && dia < 31) {
                    this.dia = dia;
                }
                this.mes = 7;
            }
            case "Agosto" -> {
                if (dia > 0 && dia < 31) {
                    this.dia = dia;
                }
                this.mes = 8;
            }
            case "Septiembre" -> {
                if (dia > 0 && dia < 30) {
                    this.dia = dia;
                }
                this.mes = 9;
            }
            case "Octubre" -> {
                if (dia > 0 && dia < 31) {
                    this.dia = dia;
                }
                this.mes = 10;
            }
            case "Noviembre" -> {
                if (dia > 0 && dia < 30) {
                    this.dia = dia;
                }
                this.mes = 11;
            }
            case "Diciembre" -> {
                if (dia > 0 && dia < 31) {
                    this.dia = dia;
                }
                this.mes = 12;
            }
            default -> {
                this.dia = null;
                this.mes = null;
            }
        }
    }

    /**
     * Convierte el dia y el mes en un String con formato -> [year-mes-dia]
     *
     * @return String con el formato que el conversor es capaz de entender
     */
    private String fecha() {
        String fecha_string = "";
        String dia_string = "";
        String mes_string = "";
        dia_string = "-" + dia;
        mes_string = "-" + mes;
        // Año -> Predeterminado en 2022
        Integer year = 2022;
        fecha_string = year + mes_string + dia_string;
        System.out.println(fecha_string);
        return fecha_string;
    }

    /**
     * Convierte el String de la funcion fecha() a un objeto tipo java.sql.DATE
     *
     * @return Fecha deseada en formato apto para ser leido por SQL
     */
    private Date transformacionFecha() {
        String fecha_string = fecha();
        return java.sql.Date.valueOf(fecha_string);
    }

    /**
     * Guarda y devuelve la variable sql_Date
     *
     * @return La fecha en formato apto para SQL
     */
    public Date getSql_date() {
        // Fecha traducida en formato apto para MySQL
        return transformacionFecha();
    }
}

package Model;

import java.sql.Date;

public class Fecha {
    private Integer dia = null;
    private final Integer mes;
    private Integer year = 2022;
    private Date sql_date;

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

    private String fecha() {
        String fecha_string = "";
        String dia_string = "";
        String mes_string = "";
        dia_string = "-" + dia;
        mes_string = "-" + mes;
        fecha_string = year + mes_string + dia_string;
        System.out.println(fecha_string);
        return fecha_string;
    }

    private Date transformacionFecha() {
        String fecha_string = fecha();
        return Date.valueOf(fecha_string);
    }

    public Date getSql_date() {
        sql_date = transformacionFecha();
        return sql_date;
    }
}

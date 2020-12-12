package com.jacksonbarreto;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Movimento implements Serializable {
    private GregorianCalendar data;
    private char tipo;


    public Movimento(char tipo){
        this.data = new GregorianCalendar();
        this.tipo = tipo;
    }

    public Movimento(char tipo, int dia, int mes, int ano, int hora, int minuto){
        this.data = new GregorianCalendar(ano,mes-1,dia,hora,minuto);
        this.tipo = tipo;
    }


    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public String toString(){
        int dia = this.data.get(GregorianCalendar.DAY_OF_MONTH);
        int mes = data.get(GregorianCalendar.MONTH);
        mes++;
        int ano = data.get(GregorianCalendar.YEAR);
        int hora = data.get(GregorianCalendar.HOUR_OF_DAY);
        int minutos = data.get(GregorianCalendar.MINUTE);
        return String.format("%c\t%02d/%02d/%d\t%d:%02d",this.tipo,dia,mes,ano,hora,minutos);
    }

    public GregorianCalendar getData(){
        return this.data;
    }

    public void setData(GregorianCalendar data) {
        this.data = data;
    }

    public char getTipo(){
        return this.tipo;
    }

}

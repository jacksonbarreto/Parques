package com.jacksonbarreto;

import java.util.GregorianCalendar;

public class Movimento {
    private GregorianCalendar data;
    private char tipo;


    public Movimento(char tipo){
        this.data = new GregorianCalendar();
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

    public char getTipo(){
        return this.tipo;
    }

}

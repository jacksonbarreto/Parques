package com.jacksonbarreto;

import com.jacksonbarreto.classes.ParqueEstacionamento;

import java.util.GregorianCalendar;

public class Main {

    public static void main(String[] args) {
        GregorianCalendar data1 = new GregorianCalendar(2020, 10, 5);
        GregorianCalendar data2 = new GregorianCalendar(2020,11,17);

        ParqueEstacionamento parque = new ParqueEstacionamento("Av. do Atlântico, 98 - Viana do Castelo", 3, 2);
        parque.adicionarCliente("Júlio César","22-AB-17");
        parque.adicionarCliente("Carlos Eduardo","44-DD-18");
        parque.adicionarCliente("Marco Aurélio","BC-19-42");
        parque.entrarExatamente("44-DD-18",10,12,2020,10,0);
        parque.entrar("BC-19-42");
        parque.sairExatamente("44-DD-18",10,12,2020,13,0);
        parque.entrar("22-AB-17");
        parque.sair("BC-19-42");


        System.out.println(parque.imprimirMovimento(data1,data2));
        parque.gravarFicheiroSerial("paque1.txt");
        ParqueEstacionamento parque2 = new ParqueEstacionamento("paque1.txt");
        System.out.println(parque2.imprimirMovimento(data1,data2));
        System.out.println(parque.obterFaturacao(data1,data2));
    }
}

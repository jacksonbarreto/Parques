package com.jacksonbarreto;

import java.util.GregorianCalendar;

public class Main {

    public static void main(String[] args) {
        GregorianCalendar data1 = new GregorianCalendar(2020, 11, 5);
        GregorianCalendar data2 = new GregorianCalendar(2020,11,8);

        ParqueEstacionamento parque = new ParqueEstacionamento("Av. do Atlântico, 98 - Viana do Castelo", 3, 2);
        parque.adicionarCliente("Júlio César","22-AB-17");
        parque.adicionarCliente("Carlos Eduardo","44-DD-18");
        parque.adicionarCliente("Marco Aurélio","BC-19-42");
        parque.entrar("44-DD-18");
        parque.entrar("BC-19-42");
        parque.sair("44-DD-18");
        parque.entrar("22-AB-17");
        parque.sair("BC-19-42");


        System.out.println(parque.imprimirMovimento(data1,data2));
    }
}

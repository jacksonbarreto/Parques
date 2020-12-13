package com.jacksonbarreto;


import com.jacksonbarreto.classes.ParqueEstacionamento;

import java.time.LocalDateTime;
import java.time.Month;


public class Main {

    public static void main(String[] args) {

        LocalDateTime data1 = LocalDateTime.of(2020, Month.DECEMBER, 5, 0, 0);
        LocalDateTime data2 = LocalDateTime.of(2020, Month.DECEMBER, 17, 0, 0);


        ParqueEstacionamento parque = new ParqueEstacionamento("Av. do Atlântico, 98 - Viana do Castelo", 3, 2);
        parque.adicionarCliente("Júlio César", "22-AB-17");
        parque.adicionarCliente("Carlos Eduardo", "44-DD-18");
        parque.adicionarCliente("Marco Aurélio", "BC-19-42");
        parque.entrarExatamente("44-DD-18", LocalDateTime.of(2020, Month.DECEMBER, 10, 10, 0));
        parque.entrar("BC-19-42");
        parque.sairExatamente("44-DD-18", LocalDateTime.of(2020, Month.DECEMBER, 10, 13, 0));
        parque.entrar("22-AB-17");
        parque.sair("BC-19-42");


        System.out.println(parque.imprimirMovimento(data1, data2));
        parque.gravarFicheiroSerial("paque1.txt");
        ParqueEstacionamento parque2 = new ParqueEstacionamento("paque1.txt");
        System.out.println(parque2.imprimirMovimento(data1, data2));
        System.out.println("Faturação: " + parque.obterFaturacao(data1, data2));
        System.out.println("====================================================");
        System.out.println(parque);


    }
}

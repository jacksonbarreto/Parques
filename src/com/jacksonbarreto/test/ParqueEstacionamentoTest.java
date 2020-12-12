package com.jacksonbarreto.test;
import com.jacksonbarreto.classes.CartaoCliente;
import com.jacksonbarreto.classes.ParqueEstacionamento;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParqueEstacionamentoTest {

    @Test
    void moradaTest(){
        ParqueEstacionamento parque = new ParqueEstacionamento("Rua Tomadia da Costa",300,100);
        assertEquals("Rua Tomadia da Costa",parque.getMorada());
        assertThrows(IllegalArgumentException.class, ()-> new ParqueEstacionamento("",300,100));
    }

    @Test
    void lotacaoEmaxClientesTest(){
        ParqueEstacionamento parque = new ParqueEstacionamento("Av. do Atlântico, 98 - Viana do Castelo", 300, 100);
        assertEquals(300,parque.getMaxClientes());
        assertEquals(100,parque.getLotacao());

        parque = new ParqueEstacionamento("Av. do Atlântico, 98 - Viana do Castelo", 100, 100);
        assertEquals(100,parque.getMaxClientes());
        assertEquals(100,parque.getLotacao());

        assertThrows(IllegalArgumentException.class,()-> new ParqueEstacionamento("Rua do Cabrito,12", 100,300));
        assertThrows(IllegalArgumentException.class,()-> new ParqueEstacionamento("Rua do Cabrito,12", 0,0));
        assertThrows(IllegalArgumentException.class,()-> new ParqueEstacionamento("Rua do Cabrito,12", 300,-12));
    }

    @Test
    void adicionaClienteTest(){
        ParqueEstacionamento parque = new ParqueEstacionamento("Av. do Atlântico, 98 - Viana do Castelo", 2, 2);

        parque.adicionarCliente("Carlos Eduardo","44-DD-18");

        assertThrows(IllegalArgumentException.class, ()-> parque.adicionarCliente("José Carlos","44-DD-18"));
        assertThrows(IllegalArgumentException.class, ()-> parque.adicionarCliente("José Carlos","44D18"));
        assertThrows(IllegalArgumentException.class, ()-> parque.adicionarCliente("José Carlos","44DD18"));
        parque.adicionarCliente("Carlos Eduardo","44-JJ-18");
        assertEquals(2,parque.getNumClientesAtivos());
        assertThrows(IllegalArgumentException.class, ()-> parque.adicionarCliente("José Carlos","44DD18"));
        assertThrows(IllegalArgumentException.class, ()-> parque.adicionarCliente("José Carlos","44DD18"));
    }

    @Test
    void removeClienteTest(){
        ParqueEstacionamento parque = new ParqueEstacionamento("Av. do Atlântico, 98 - Viana do Castelo", 300, 100);
        parque.adicionarCliente("Carlos Eduardo","44-DD-18");
        assertEquals(1,parque.getNumClientesAtivos());
        parque.removerCliente("44-DD-18");
        assertEquals(0,parque.getNumClientesAtivos());
        parque.adicionarCliente("Júlio César","22-AB-17");
        assertEquals(1,parque.getNumClientesAtivos());
        parque.adicionarCliente("Carlos Eduardo","44-DD-18");
        assertEquals(2,parque.getNumClientesAtivos());
    }

    @Test
    void entrarClienteTest(){
        ParqueEstacionamento parque = new ParqueEstacionamento("Av. do Atlântico, 98 - Viana do Castelo", 3, 2);
        assertThrows(IllegalArgumentException.class, ()-> parque.entrar("44-DD-18"));
        parque.adicionarCliente("Carlos Eduardo","44-DD-18");
        parque.adicionarCliente("Júlio César","22-AB-17");
        parque.adicionarCliente("Marco Aurélio","BC-19-42");
        parque.entrar("44-DD-18");

        assertEquals(1,parque.getOcupacao());
        assertThrows(IllegalArgumentException.class, ()-> parque.entrar("44-DD-18"));
        parque.entrar("22-AB-17");
        assertThrows(IllegalArgumentException.class, ()-> parque.entrar("BC-19-42"));
        assertEquals(2,parque.getOcupacao());
    }

    @Test
    void sairClienteTest(){
        ParqueEstacionamento parque = new ParqueEstacionamento("Av. do Atlântico, 98 - Viana do Castelo", 3, 2);
        assertThrows(IllegalArgumentException.class, ()-> parque.sair("44-DD-18"));
        parque.adicionarCliente("Carlos Eduardo","44-DD-18");
        assertThrows(IllegalArgumentException.class, ()-> parque.sair("44-DD-18"));
        parque.adicionarCliente("Júlio César","22-AB-17");
        parque.adicionarCliente("Marco Aurélio","BC-19-42");
        assertThrows(IllegalArgumentException.class, ()-> parque.sair("44-DD-18"));
        parque.entrar("22-AB-17");
        parque.entrar("44-DD-18");
        assertEquals(2,parque.getOcupacao());
        parque.sair("22-AB-17");
        assertEquals(1,parque.getOcupacao());
    }

    @Test
    void listarOcupantesTest(){
        ParqueEstacionamento parque = new ParqueEstacionamento("Av. do Atlântico, 98 - Viana do Castelo", 3, 2);
        parque.adicionarCliente("Júlio César","22-AB-17");
        parque.adicionarCliente("Carlos Eduardo","44-DD-18");
        parque.adicionarCliente("Marco Aurélio","BC-19-42");
        parque.entrar("22-AB-17");
        ArrayList<CartaoCliente> ocupantes = parque.listarOcupantes();
        assertEquals(1,ocupantes.size());
        assertEquals("22-AB-17", ocupantes.get(0).getMatricula());
        parque.entrar("44-DD-18");
        ocupantes = parque.listarOcupantes();
        assertEquals(2,ocupantes.size());
        assertEquals("44-DD-18", ocupantes.get(1).getMatricula());
    }

    @Test
    void listarClientesTest(){
        ParqueEstacionamento parque = new ParqueEstacionamento("Av. do Atlântico, 98 - Viana do Castelo", 3, 2);
        parque.adicionarCliente("Júlio César","22-AB-17");
        parque.adicionarCliente("Carlos Eduardo","44-DD-18");
        ArrayList<CartaoCliente> clientes = parque.listarClientes();
        assertEquals(2,clientes.size());
        parque.adicionarCliente("Marco Aurélio","BC-19-42");
        clientes = parque.listarClientes();
        assertEquals(3,clientes.size());

    }

    @Test
    void imprimirMovimentosEntreDatasTest(){
        ParqueEstacionamento parque = new ParqueEstacionamento("Av. do Atlântico, 98 - Viana do Castelo", 3, 2);
        parque.adicionarCliente("Júlio César","22-AB-17");
        parque.adicionarCliente("Carlos Eduardo","44-DD-18");
        parque.adicionarCliente("Marco Aurélio","BC-19-42");
        parque.entrar("44-DD-18");
        parque.entrar("BC-19-42");
        parque.sair("44-DD-18");
        parque.entrar("22-AB-17");
        parque.sair("BC-19-42");

    }
    @Test
    void obterFaturacaoTest(){
        ParqueEstacionamento parque = new ParqueEstacionamento("Av. do Atlântico, 98 - Viana do Castelo", 3, 2);
        parque.adicionarCliente("Júlio César","22-AB-17");
        parque.adicionarCliente("Carlos Eduardo","44-DD-18");
        parque.adicionarCliente("Marco Aurélio","BC-19-42");
        parque.entrarExatamente("44-DD-18",10,12,2020,10,0);
        parque.entrar("BC-19-42");
        parque.sairExatamente("44-DD-18",10,12,2020,12,0);
        parque.entrar("22-AB-17");
        parque.sair("BC-19-42");

        GregorianCalendar data1 = new GregorianCalendar(2020, 11, 5);
        GregorianCalendar data2 = new GregorianCalendar(2020,11,17);
        assertEquals(3.0, parque.obterFaturacao(data1, data2));
    }
}

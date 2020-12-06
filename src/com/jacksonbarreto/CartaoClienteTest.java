package com.jacksonbarreto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CartaoClienteTest {

    @Test
    void nomeClienteTest(){
        CartaoCliente cartao = new CartaoCliente(" luís FiLIPE. ", "21-42-CC");
        assertEquals("Luís Filipe", cartao.getNome());
        cartao = new CartaoCliente("+ lPd* - ?ppp   =","21-42-CC");
        assertEquals("Lpd - Ppp", cartao.getNome());
        //verifica se os conectivos continuam em minúscula.
        cartao = new CartaoCliente("pero vAz ++ de   CaMinHa ","21-42-CC");
        assertEquals("Pero Vaz de Caminha", cartao.getNome());
        assertThrows(IllegalArgumentException.class, ()-> new CartaoCliente("", "21-42-CC"));
        assertThrows(IllegalArgumentException.class, ()-> new CartaoCliente("Ma", "21-42-CC"));
        assertThrows(IllegalArgumentException.class, ()-> new CartaoCliente(" ju  ", "21-42-CC"));
        assertThrows(IllegalArgumentException.class, ()-> new CartaoCliente("j u", "21-42-CC"));
    }
    @Test
    void matriculaClienteTest(){
        CartaoCliente cartao = new CartaoCliente(" luís FiLIPE. ", "2142cF");
        assertEquals("21-42-CF",cartao.getMatricula());
        cartao = new CartaoCliente(" luís FiLIPE. ", "18-JP-18");
        assertEquals("18-JP-18",cartao.getMatricula());
        cartao = new CartaoCliente(" luís FiLIPE. ", "25-90-mk");
        assertEquals("25-90-MK",cartao.getMatricula());
        assertThrows(IllegalArgumentException.class, ()-> new CartaoCliente(" luís FiLIPE. ", "18-JPi-18"));
        assertThrows(IllegalArgumentException.class, ()-> new CartaoCliente(" luís FiLIPE. ", "18JPi18"));
        assertThrows(IllegalArgumentException.class, ()-> new CartaoCliente(" luís FiLIPE. ", ""));
    }


}

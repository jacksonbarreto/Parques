package com.jacksonbarreto.classes;

public class CartaoClienteAtrelado extends CartaoCliente{
    private final Integer pesoBruto;
    private final String matriculaAtrelado;
    public static final Double PRECO_HORA;
    private static final Integer PESO_BRUTO_MAXIMO;
    private static final Integer PESO_BRUTO_COM_MATRICULA;

    static {
        PRECO_HORA = 2.0;
        PESO_BRUTO_MAXIMO = 1000;
        PESO_BRUTO_COM_MATRICULA = 300;
    }

    public CartaoClienteAtrelado(String nome, String matricula, Integer pesoBruto, String matriculaAtrelado) {
        super(nome, matricula);
        if(pesoBruto > PESO_BRUTO_MAXIMO)
            throw new IllegalArgumentException("Peso Bruto não pode ser superior a 1000Kg.");
        if (pesoBruto < PESO_BRUTO_COM_MATRICULA)
            throw new IllegalArgumentException("Somente atrelados com peso bruto igual ou superior a "
                                                + PESO_BRUTO_COM_MATRICULA + "Kg devem registrar matricula de atrelado");
        this.pesoBruto = pesoBruto;
        this.matriculaAtrelado = matriculaAtrelado;
    }

    public CartaoClienteAtrelado(String nome, String matricula, Integer pesoBruto) {
        super(nome, matricula);
        if(pesoBruto > PESO_BRUTO_MAXIMO)
            throw new IllegalArgumentException("Peso Bruto não pode ser superior a 1000Kg.");
        this.pesoBruto = pesoBruto;
        this.matriculaAtrelado = null;
    }
}

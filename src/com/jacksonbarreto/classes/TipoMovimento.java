package com.jacksonbarreto.classes;

public enum TipoMovimento {
    ENTRADA('E', 1, "Entrada"), SAIDA('S', 2, "Saída");

    private char tipoChar;
    private Integer tipoInt;
    private String tipoString;

    TipoMovimento(char tipoChar, int tipoInt, String tipoString) {
        this.tipoChar = tipoChar;
        this.tipoInt = tipoInt;
        this.tipoString = tipoString;
    }

    public char getTipoChar() {
        return tipoChar;
    }

    public Integer getTipoInt() {
        return tipoInt;
    }
}

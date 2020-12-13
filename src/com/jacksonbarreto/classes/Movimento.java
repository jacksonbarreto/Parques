package com.jacksonbarreto.classes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Movimento implements Serializable {
    private final LocalDateTime data;
    private final char tipo;


    public Movimento(char tipo) {
        this.data = LocalDateTime.now();
        this.tipo = tipo;
    }

    public Movimento(char tipo, LocalDateTime data) {
        this.data = data;
        this.tipo = tipo;
    }

    public Movimento(Movimento movimento) {
        this.tipo = movimento.getTipo();
        this.data = movimento.getData();
    }

    public String toString() {
        return String.format("%c\t%s", this.tipo, this.data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy\tHH:mm")));
    }

    public LocalDateTime getData() {
        return this.data;
    }


    public char getTipo() {
        return this.tipo;
    }

}

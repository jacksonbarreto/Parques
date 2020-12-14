package com.jacksonbarreto.classes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Movimento implements Serializable {
    private final LocalDateTime data;
    private final TipoMovimento tipo;

    /**
     * Construtor de movimento
     *
     * @param tipo Tipo de movimentação
     */
    public Movimento(TipoMovimento tipo) {
        this.data = LocalDateTime.now();
        this.tipo = tipo;
    }

    /**
     * Construtor de movimento em uma data específica.
     *
     * @param tipo Tipo de movimentação
     * @param data Data e hora da movimentação
     */
    public Movimento(TipoMovimento tipo, LocalDateTime data) {
        this.data = data;
        this.tipo = tipo;
    }

    /**
     * Construtor de movimento a partir de um outro movimento.
     *
     * @param movimento Movimento a ser copiado
     */
    public Movimento(Movimento movimento) {
        this.tipo = movimento.getTipo();
        this.data = movimento.getData();
    }

    /**
     * Método para obter a representação textual de um movimento na formatação: <TipoMovimento>  <DataHOra>
     *
     * @return A representação textual de um movimento
     */
    public String toString() {
        return String.format("%c\t%s", this.tipo.getTipoChar(), this.data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy\tHH:mm")));
    }

    /**
     * Método para obter a data e hora do movimento.
     *
     * @return Data e hora do movimento.
     */
    public LocalDateTime getData() {
        return this.data;
    }

    /**
     * Método para obter o tipo da movimentação.
     *
     * @return O tipo do movimento (Entrada ou Saída).
     */
    public TipoMovimento getTipo() {
        return this.tipo;
    }

}

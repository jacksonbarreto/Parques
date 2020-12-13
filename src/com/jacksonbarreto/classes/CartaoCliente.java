package com.jacksonbarreto.classes;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class CartaoCliente implements Serializable, Iterable<CartaoCliente> {
    private boolean ativo;
    private boolean estacionado;
    private String nome;
    private String matricula;
    private ArrayList<Movimento> movimentos;

    public CartaoCliente(String nome, String matricula) {
        if (nomeIsInvalid(nome))
            throw new IllegalArgumentException("Nome Inválido.");
        this.nome = Helpers.normalizeNome(Helpers.sanitizeString(nome));
        if (Helpers.matriculaIsInvalid(matricula))
            throw new IllegalArgumentException("Matrícula inválida.");

        this.matricula = Helpers.normalizeMatricula(matricula);
        this.movimentos = new ArrayList<Movimento>();
        this.ativo = true;
        this.estacionado = false;
    }


    public String toString() {
        StringBuilder cliente = new StringBuilder();
        cliente.append("\n-------------------------------------------");
        cliente.append(String.format("\nNome: %s \tMatricula: %s", this.nome, this.matricula));
        cliente.append(String.format("\nStatus: %s  Estacionado: %s", this.ativo, this.estacionado));
        cliente.append("\nMovimentos:\n");
        for (Movimento m : this.movimentos) {
            cliente.append(m.toString()).append("\n");
        }
        cliente.append("-------------------------------------------").append("\n");
        return cliente.toString();
    }

    public ArrayList<Movimento> getMovimentosInDatesArray(LocalDateTime data1, LocalDateTime data2) {
        ArrayList<Movimento> movimentosInData = new ArrayList<>();
        if (!this.movimentos.isEmpty()) {
            for (Movimento m : movimentos) {

                if (m.getData().isAfter(data1) && m.getData().isBefore(data2)) {
                    movimentosInData.add(m);
                }
            }
        }
        return movimentosInData;
    }

    public String getMovimentoInDates(LocalDateTime data1, LocalDateTime data2) {
        StringBuilder movimentoList = new StringBuilder();
        if (!this.movimentos.isEmpty()) {
            movimentoList.append(String.format("\nMatricula: %s\n", this.matricula));
            for (Movimento m : movimentos) {
                if (m.getData().isAfter(data1) && m.getData().isBefore(data2)) {
                    movimentoList.append(m.toString()).append("\n");
                }
            }
        } else {
            movimentoList.append("Sem Movimentos.");
        }
        return movimentoList.toString();
    }

    private boolean nomeIsInvalid(String nome) {
        if (nome.isEmpty() || nome.replaceAll("^ +| +$|( )+", "").length() < 3)
            return true;
        return false;
    }

    public void addMovimento(char tipo) {
        this.movimentos.add(new Movimento(tipo));
    }

    public void addMovimento(char tipo, LocalDateTime data) {
        this.movimentos.add(new Movimento(tipo, data));
    }


    public boolean isEstacionado() {
        return estacionado;
    }

    public void setEstacionado(boolean estacionado) {
        this.estacionado = estacionado;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @NotNull
    @Override
    public Iterator<CartaoCliente> iterator() {
        return null;
    }
}

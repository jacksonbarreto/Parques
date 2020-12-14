package com.jacksonbarreto.classes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CartaoCliente implements Serializable {
    private boolean ativo;
    private boolean estacionado;
    private String nome;
    private final String matricula;
    private ArrayList<Movimento> movimentos;

    /**
     * Construtor de um cliente.
     *
     * @param nome      Nome do cliente.
     * @param matricula Matrícula do veículo do cliente.
     */
    public CartaoCliente(String nome, String matricula) {
        if (nomeIsInvalid(nome))
            throw new IllegalArgumentException("Nome Inválido.");
        this.nome = Helpers.normalizeNome(Helpers.sanitizeString(nome));
        if (Helpers.matriculaIsInvalid(matricula))
            throw new IllegalArgumentException("Matrícula inválida.");

        this.matricula = Helpers.normalizeMatricula(matricula);
        this.movimentos = new ArrayList<>();
        this.ativo = true;
        this.estacionado = false;
    }

    /**
     * Método para obter a representação textual do cliente, inclusive todos os seus movimentos.
     *
     * @return A representação textual do cliente.
     */
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

    /**
     * Método retorna um ArrayList com todos os movimentos realizados pelo cliente em um intervalo de datas fornecidas como parâmetros.
     *
     * @param dataInicial Inicio do Intervalo a ser consultado (Sensível a minutos e segundos).
     * @param dataFinal   Final do Intervalo a ser consultado (Sensível a minutos e segundos).
     * @return Movimentos ocorridos no período ou um ArrayList vazio.
     */
    public ArrayList<Movimento> getMovimentosNoIntervaloArray(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        ArrayList<Movimento> movimentosNoPeriodo = new ArrayList<>();

        for (Movimento movimento : this.movimentos) {
            if (movimento.getData().isAfter(dataInicial) && movimento.getData().isBefore(dataFinal)) {
                movimentosNoPeriodo.add(movimento);
            }
        }
        return movimentosNoPeriodo;
    }

    /**
     * Método retorna uma String com todos os movimentos realizados pelo cliente em um intervalo de datas fornecidas como parâmetros.
     *
     * @param dataInicial Inicio do Intervalo a ser consultado (Sensível a minutos e segundos).
     * @param dataFinal   Final do Intervalo a ser consultado (Sensível a minutos e segundos).
     * @return A representação textual de todos os movimentos ocorridos no período.
     */
    public String getMovimentosNoIntervaloString(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        StringBuilder movimentoList = new StringBuilder();
        if (this.movimentos.isEmpty()) {
            movimentoList.append("Sem Movimentos.");
        } else {
            movimentoList.append(String.format("\nMatricula: %s\n", this.matricula));
            for (Movimento movimento : movimentos) {
                if (movimento.getData().isAfter(dataInicial) && movimento.getData().isBefore(dataFinal)) {
                    movimentoList.append(movimento.toString()).append("\n");
                }
            }
        }
        return movimentoList.toString();
    }

    private boolean nomeIsInvalid(String nome) {
        return nome.isEmpty() || nome.replaceAll("^ +| +$|( )+", "").length() < 3;
    }

    /**
     * Adiciona um novo movimento na lista de movimentos do cliente.
     *
     * @param tipoMovimento Tipo de movimento realizado pelo cliente.
     */
    public void adicionaMovimento(TipoMovimento tipoMovimento) {
        this.movimentos.add(new Movimento(tipoMovimento));
    }

    /**
     * Adiciona um novo movimento em uma data/hora específica na lista de movimentos do cliente.
     *
     * @param tipoMovimento Tipo de movimento realizado pelo cliente.
     * @param data          Data e Hora em que o movimento ocorreu.
     */
    public void adicionaMovimento(TipoMovimento tipoMovimento, LocalDateTime data) {
        this.movimentos.add(new Movimento(tipoMovimento, data));
    }

    /**
     * Este método informa se o cliente está estacionado.
     *
     * @return true, caso o cliente esteja estacionado.
     */
    public boolean isEstacionado() {
        return estacionado;
    }

    /**
     * Define o estado do cliente como estacionado ou não-estacionado.
     *
     * @param estacionado True = estacionado || False = não-estacionado.
     */
    public void setEstacionado(boolean estacionado) {
        this.estacionado = estacionado;
    }

    /**
     * Informa se o cliente está ativo ou não.
     *
     * @return true caso o cliente esteja ativo ou false caso esteja inativo.
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * Define o status do cliente como ativo ou inativo.
     *
     * @param ativo true = ativo || false = inativo
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * Método para obter o nome do cliente.
     *
     * @return o nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método para definir o novo nome do cliente.
     *
     * @param novoNome Novo nome a ser atribuído ao cliente.
     */
    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    /**
     * Método para obter a matrícula do cliente.
     *
     * @return a matrícula do cliente.
     */
    public String getMatricula() {
        return matricula;
    }

}

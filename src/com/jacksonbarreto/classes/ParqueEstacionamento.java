package com.jacksonbarreto.classes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class ParqueEstacionamento implements Serializable {
    private CartaoCliente[] clientes;
    private int numClientes;
    private int numClientesAtivos;
    private int numClientesEstacionados;
    private String morada;
    private int maxClientes;
    private int lotacao;
    final static private double PRECO_HORA = 1.5;

    public ParqueEstacionamento(String morada, int maxClientes, int lotacao) {

        if (lotacaoEmaxClientesIsInvalid(maxClientes, lotacao))
            throw new IllegalArgumentException("Valores de lotação e máximo de clientes devem ser positivos e lotação deve ser menor ou igual ao máximo de clientes.");
        if (morada.isEmpty())
            throw new IllegalArgumentException("A morada não pode ser vazia.");
        this.morada = morada;
        this.maxClientes = maxClientes;
        this.lotacao = lotacao;
        this.numClientes = 0;
        this.numClientesAtivos = 0;
        this.numClientesEstacionados = 0;
        this.clientes = new CartaoCliente[maxClientes];
    }

    public ParqueEstacionamento(String caminho) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            ParqueEstacionamento parque = (ParqueEstacionamento) ois.readObject();
            this.clientes = parque.clientes;
            this.lotacao = parque.lotacao;
            this.maxClientes = parque.maxClientes;
            this.numClientes = parque.numClientes;
            this.numClientesAtivos = parque.numClientesAtivos;
            this.morada = parque.morada;
            this.numClientesEstacionados = parque.numClientesEstacionados;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public ParqueEstacionamento(ParqueEstacionamento parque) {
        this.clientes = parque.clientes;
        this.lotacao = parque.lotacao;
        this.maxClientes = parque.maxClientes;
        this.numClientes = parque.numClientes;
        this.numClientesAtivos = parque.numClientesAtivos;
        this.morada = parque.morada;
        this.numClientesEstacionados = parque.numClientesEstacionados;
    }

    private boolean lotacaoEmaxClientesIsInvalid(int maxClientes, int lotacao) {
        if (maxClientes <= 0 || lotacao <= 0)
            return true;
        return lotacao > maxClientes;
    }

    public void adicionarCliente(String nome, String matricula) {
        if (this.numClientes == this.maxClientes)
            throw new IllegalArgumentException("A capacidade de Clientes foi atingida.");
        if (Helpers.matriculaIsInvalid(matricula))
            throw new IllegalArgumentException("Matrícula inválida.");
        matricula = Helpers.normalizeMatricula(matricula);
        if (matriculaExists(matricula)) {
            int indexMatricula = indexOfMatricula(matricula);
            if (clientes[indexMatricula].isAtivo())
                throw new IllegalArgumentException("Esta matrícula já se encontra ATIVA no Parque.");
            else {
                clientes[indexMatricula].setAtivo(true);
                this.numClientesAtivos++;
            }
        } else {
            try {
                CartaoCliente cliente = new CartaoCliente(nome, matricula);
                this.clientes[numClientes] = cliente;
                this.numClientes++;
                this.numClientesAtivos++;
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    public void removerCliente(String matricula) {
        if (Helpers.matriculaIsInvalid(matricula))
            throw new IllegalArgumentException("Matrícula inválida.");
        matricula = Helpers.normalizeMatricula(matricula);
        if (matriculaExists(matricula)) {
            int indexMatricula = indexOfMatricula(matricula);
            clientes[indexMatricula].setAtivo(false);
            this.numClientesAtivos--;
        } else {
            throw new IllegalArgumentException("Cliente Inexistente");
        }
    }

    public void entrar(String matricula) {
        if (Helpers.matriculaIsInvalid(matricula))
            throw new IllegalArgumentException("Matrícula inválida.");
        matricula = Helpers.normalizeMatricula(matricula);

        if (this.lotacao == this.numClientesEstacionados)
            throw new IllegalArgumentException("Parque Lotado.");

        if (indexOfMatricula(matricula) == -1)
            throw new IllegalArgumentException("Cliente não cadastrado.");
        if (clientes[indexOfMatricula(matricula)].isEstacionado())
            throw new IllegalArgumentException("Cliente já estacionado.");

        clientes[indexOfMatricula(matricula)].setEstacionado(true);
        clientes[indexOfMatricula(matricula)].adicionaMovimento(TipoMovimento.ENTRADA);
        this.numClientesEstacionados++;
    }

    public void sair(String matricula) {
        if (Helpers.matriculaIsInvalid(matricula))
            throw new IllegalArgumentException("Matrícula inválida.");
        matricula = Helpers.normalizeMatricula(matricula);

        if (this.numClientesEstacionados == 0)
            throw new IllegalArgumentException("O parque está vazio.");

        if (indexOfMatricula(matricula) == -1)
            throw new IllegalArgumentException("Cliente não cadastrado.");

        if (!clientes[indexOfMatricula(matricula)].isEstacionado())
            throw new IllegalArgumentException("O cliente não está estacionado.");

        clientes[indexOfMatricula(matricula)].setEstacionado(false);
        clientes[indexOfMatricula(matricula)].adicionaMovimento(TipoMovimento.SAIDA);
        this.numClientesEstacionados--;
    }

    public void entrarExatamente(String matricula, LocalDateTime data) {
        if (Helpers.matriculaIsInvalid(matricula))
            throw new IllegalArgumentException("Matrícula inválida.");
        matricula = Helpers.normalizeMatricula(matricula);

        if (this.lotacao == this.numClientesEstacionados)
            throw new IllegalArgumentException("Parque Lotado.");

        if (indexOfMatricula(matricula) == -1)
            throw new IllegalArgumentException("Cliente não cadastrado.");
        if (clientes[indexOfMatricula(matricula)].isEstacionado())
            throw new IllegalArgumentException("Cliente já estacionado.");

        clientes[indexOfMatricula(matricula)].setEstacionado(true);
        clientes[indexOfMatricula(matricula)].adicionaMovimento(TipoMovimento.ENTRADA, data);
        this.numClientesEstacionados++;
    }

    public void sairExatamente(String matricula, LocalDateTime data) {
        if (Helpers.matriculaIsInvalid(matricula))
            throw new IllegalArgumentException("Matrícula inválida.");
        matricula = Helpers.normalizeMatricula(matricula);

        if (this.numClientesEstacionados == 0)
            throw new IllegalArgumentException("O parque está vazio.");

        if (indexOfMatricula(matricula) == -1)
            throw new IllegalArgumentException("Cliente não cadastrado.");

        if (!clientes[indexOfMatricula(matricula)].isEstacionado())
            throw new IllegalArgumentException("O cliente não está estacionado.");

        clientes[indexOfMatricula(matricula)].setEstacionado(false);
        clientes[indexOfMatricula(matricula)].adicionaMovimento(TipoMovimento.SAIDA, data);
        this.numClientesEstacionados--;
    }

    public ArrayList<CartaoCliente> listarOcupantes() {
        ArrayList<CartaoCliente> ocupantes = new ArrayList<>();

        if (this.numClientesEstacionados == 0)
            return ocupantes;

        for (int i = 0; i < this.numClientes; i++) {
            if (this.clientes[i].isEstacionado()) {
                ocupantes.add(this.clientes[i]);
                //ocupantes.add((CartaoCliente) clientes[i].clone());
            }
        }

        return ocupantes;
    }

    public ArrayList<CartaoCliente> listarClientes() {
        ArrayList<CartaoCliente> clientes = new ArrayList<>();

        if (this.numClientes == 0)
            return clientes;

        for (int i = 0; i < this.numClientes; i++) {
            if (this.clientes[i].isAtivo()) {
                clientes.add(this.clientes[i]);
            }
        }
        return clientes;
    }

    public String imprimirMovimento(LocalDateTime data1, LocalDateTime data2) {
        StringBuilder movimentos = new StringBuilder();
        movimentos.append("\n**********\tMovimentos do Parque\t**********\n");
        for (int i = 0; i < this.numClientes; i++) {
            movimentos.append(this.clientes[i].getMovimentosNoIntervaloString(data1, data2));
        }

        return movimentos.toString();
    }

    /*
        public void gravarFicheiroCsv(String caminho){
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
                StatefulBeanToCsv<ParqueEstacionamento> bwCsv = new StatefulBeanToCsvBuilder(bw).build();
                bwCsv.bw(this);
                bw.flush();

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    */
    public void gravarFicheiroSerial(String caminho) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {

            oos.writeObject(this);


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public double obterFaturacao(LocalDateTime data1, LocalDateTime data2) {
        ArrayList<Movimento> movimento;
        Movimento m1, m2;
        double faturacao = 0;
        Duration period;
        long horas;


        for (int i = 0; i < this.numClientes; i++) {
            if (this.clientes[i].isAtivo()) {
                movimento = clientes[i].getMovimentosNoIntervaloArray(data1, data2);
                if (!movimento.isEmpty()) {
                    for (int j = 0; j < movimento.size() - 1; j++) {
                        m1 = movimento.get(j);
                        m2 = movimento.get(j + 1);
                        if (m1.getTipo() == TipoMovimento.ENTRADA && m2.getTipo() == TipoMovimento.SAIDA) {

                            period = Duration.between(m1.getData(), m2.getData());
                            horas = period.toHours();
                            faturacao += horas * PRECO_HORA;
                        }
                    }
                }
            }
        }
        return faturacao;
    }


    private int indexOfMatricula(String matricula) {
        if (this.numClientes == 0)
            return -1;
        for (int i = 0; i < this.numClientes; i++) {
            if (this.clientes[i].getMatricula().equals(matricula))
                return i;
        }
        return -1;
    }

    private boolean matriculaExists(String matricula) {
        return indexOfMatricula(matricula) >= 0;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getMaxClientes() {
        return maxClientes;
    }

    public int getLotacao() {
        return lotacao;
    }

    public int getOcupacao() {
        return numClientesEstacionados;
    }

    public int getNumClientesAtivos() {
        return numClientesAtivos;
    }

    @Override
    public String toString() {
        return "ParqueEstacionamento{" +
                "clientes=" + Arrays.toString(clientes) +
                ", numClientes=" + numClientes +
                ", numClientesAtivos=" + numClientesAtivos +
                ", numClientesEstacionados=" + numClientesEstacionados +
                ", morada='" + morada + '\'' +
                ", maxClientes=" + maxClientes +
                ", lotacao=" + lotacao +
                ", PRECO_HORA=" + PRECO_HORA +
                '}';
    }
}

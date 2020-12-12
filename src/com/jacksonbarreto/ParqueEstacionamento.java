package com.jacksonbarreto;

//import com.opencsv.*;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ParqueEstacionamento implements Serializable {
    private CartaoCliente[] clientes;
    private int numClientes;
    private int numClientesAtivos;
    private int numClientesEstacionados;
    private String morada;
     private int maxClientes;
     private int lotacao;
     final private double PRECO_HORA = 1.5;

    public ParqueEstacionamento(String morada, int maxClientes, int lotacao){

        if (lotacaoEmaxClientesIsInvalid(maxClientes,lotacao))
            throw new IllegalArgumentException("Valores de lotação e máximo de clientes devem ser positivos e lotação deve ser menor ou igual ao máximo de clientes.");
        if (morada.isEmpty())
            throw new IllegalArgumentException("A morada não pode ser vazia.");
        this.morada = morada;
        this.maxClientes = maxClientes;
        this.lotacao = lotacao;
        this.numClientes = 0;
        this.numClientesAtivos=0;
        this.numClientesEstacionados=0;
        this.clientes = new CartaoCliente[maxClientes];
    }
    public ParqueEstacionamento(String caminho){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            ParqueEstacionamento parque = (ParqueEstacionamento) ois.readObject();
            this.clientes = parque.clientes;
            this.lotacao = parque.lotacao;
            this.maxClientes = parque.maxClientes;
            this.numClientes = parque.numClientes;
            this.numClientesAtivos = parque.numClientesAtivos;
            this.morada = parque.morada;
            this.numClientesEstacionados = parque.numClientesEstacionados;
        }catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public ParqueEstacionamento(ParqueEstacionamento parque){
        this.clientes = parque.clientes;
        this.lotacao = parque.lotacao;
        this.maxClientes = parque.maxClientes;
        this.numClientes = parque.numClientes;
        this.numClientesAtivos = parque.numClientesAtivos;
        this.morada = parque.morada;
        this.numClientesEstacionados = parque.numClientesEstacionados;
    }

    private boolean lotacaoEmaxClientesIsInvalid(int maxClientes, int lotacao){
        if(maxClientes <=0 || lotacao <= 0)
            return true;
        if(lotacao > maxClientes)
            return true;
        return false;
    }

    public void adicionarCliente(String nome, String matricula){
        if (this.numClientes == this.maxClientes)
            throw new IllegalArgumentException("A capacidade de Clientes foi atingida.");
        if(Helpers.matriculaIsInvalid(matricula))
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
                CartaoCliente cliente = new CartaoCliente(nome,matricula);
                this.clientes[numClientes] = cliente;
                this.numClientes++;
                this.numClientesAtivos++;
            } catch (IllegalArgumentException e){
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    public void removerCliente(String matricula){
        if(Helpers.matriculaIsInvalid(matricula))
            throw new IllegalArgumentException("Matrícula inválida.");
        matricula = Helpers.normalizeMatricula(matricula);
        if (matriculaExists(matricula)) {
            int indexMatricula = indexOfMatricula(matricula);
            clientes[indexMatricula].setAtivo(false);
            this.numClientesAtivos--;
        }
        else {
            throw new IllegalArgumentException("Cliente Inexistente");
        }
    }

    public void entrar(String matricula){
        if(Helpers.matriculaIsInvalid(matricula))
            throw new IllegalArgumentException("Matrícula inválida.");
        matricula = Helpers.normalizeMatricula(matricula);

        if(this.lotacao == this.numClientesEstacionados)
            throw new IllegalArgumentException("Parque Lotado.");

        if(indexOfMatricula(matricula) == -1)
            throw new IllegalArgumentException("Cliente não cadastrado.");
        if(clientes[indexOfMatricula(matricula)].isEstacionado())
            throw new IllegalArgumentException("Cliente já estacionado.");

        clientes[indexOfMatricula(matricula)].setEstacionado(true);
        clientes[indexOfMatricula(matricula)].addMovimento('E');
        this.numClientesEstacionados++;
    }

    public void sair(String matricula){
        if(Helpers.matriculaIsInvalid(matricula))
            throw new IllegalArgumentException("Matrícula inválida.");
        matricula = Helpers.normalizeMatricula(matricula);

        if(this.numClientesEstacionados == 0)
            throw new IllegalArgumentException("O parque está vazio.");

        if(indexOfMatricula(matricula) == -1)
            throw new IllegalArgumentException("Cliente não cadastrado.");

        if(!clientes[indexOfMatricula(matricula)].isEstacionado())
            throw new IllegalArgumentException("O cliente não está estacionado.");

        clientes[indexOfMatricula(matricula)].setEstacionado(false);
        clientes[indexOfMatricula(matricula)].addMovimento('S');
        this.numClientesEstacionados--;
    }
    public void entrarExatamente(String matricula,int dia, int mes, int ano, int hora, int minuto){
        if(Helpers.matriculaIsInvalid(matricula))
            throw new IllegalArgumentException("Matrícula inválida.");
        matricula = Helpers.normalizeMatricula(matricula);

        if(this.lotacao == this.numClientesEstacionados)
            throw new IllegalArgumentException("Parque Lotado.");

        if(indexOfMatricula(matricula) == -1)
            throw new IllegalArgumentException("Cliente não cadastrado.");
        if(clientes[indexOfMatricula(matricula)].isEstacionado())
            throw new IllegalArgumentException("Cliente já estacionado.");

        clientes[indexOfMatricula(matricula)].setEstacionado(true);
        clientes[indexOfMatricula(matricula)].addMovimento('E',ano,mes,dia,hora,minuto);
        this.numClientesEstacionados++;
    }
    public void sairExatamente(String matricula,int dia, int mes, int ano, int hora, int minuto){
        if(Helpers.matriculaIsInvalid(matricula))
            throw new IllegalArgumentException("Matrícula inválida.");
        matricula = Helpers.normalizeMatricula(matricula);

        if(this.numClientesEstacionados == 0)
            throw new IllegalArgumentException("O parque está vazio.");

        if(indexOfMatricula(matricula) == -1)
            throw new IllegalArgumentException("Cliente não cadastrado.");

        if(!clientes[indexOfMatricula(matricula)].isEstacionado())
            throw new IllegalArgumentException("O cliente não está estacionado.");

        clientes[indexOfMatricula(matricula)].setEstacionado(false);
        clientes[indexOfMatricula(matricula)].addMovimento('S',ano,mes,dia,hora,minuto);
        this.numClientesEstacionados--;
    }

    public ArrayList<CartaoCliente> listarOcupantes(){
        ArrayList<CartaoCliente> ocupantes = new ArrayList<CartaoCliente>();

        if(this.numClientesEstacionados == 0)
            return ocupantes;

        for(int i=0; i< this.numClientes; i++){
            if(this.clientes[i].isEstacionado()) {
                    ocupantes.add(this.clientes[i]);
                    //ocupantes.add((CartaoCliente) clientes[i].clone());
            }
        }

        return ocupantes;
    }

    public ArrayList<CartaoCliente> listarClientes(){
        ArrayList<CartaoCliente> clientes = new ArrayList<CartaoCliente>();

        if (this.numClientes == 0)
            return clientes;

        for(int i=0; i< this.numClientes; i++){
            if(this.clientes[i].isAtivo()) {
                clientes.add(this.clientes[i]);
                //clientes.add((CartaoCliente) this.clientes[i].clone());
            }
        }

         /*
        for(CartaoCliente c : this.clientes){
            if (c.isAtivo())
                clientes.add(c);
        }
        */

        return clientes;
    }

    public String imprimirMovimento(GregorianCalendar data1, GregorianCalendar data2){
        StringBuilder movimentos = new StringBuilder();
        movimentos.append("\n**********\tMovimentos do Parque\t**********\n");
        for(int i=0; i< this.numClientes; i++){
                movimentos.append(this.clientes[i].getMovimentoInDates(data1,data2));
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
    public void gravarFicheiroSerial(String caminho){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {

            oos.writeObject(this);


        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public double obterFaturacao(GregorianCalendar data1, GregorianCalendar data2){
        ArrayList<Movimento> movimento;
        Movimento m1,m2;
        double faturacao=0;
        Duration period;
        long horas;
        LocalDateTime dataMov1, dataMov2;
        LocalDateTime start = LocalDateTime.of(
                data1.get(Calendar.YEAR),
                data1.get(Calendar.MONTH)+1,
                data1.get(Calendar.DAY_OF_MONTH),
                data1.get(Calendar.HOUR_OF_DAY),
                data1.get(Calendar.MINUTE)
        );

        LocalDateTime end = LocalDateTime.of(
                data2.get(Calendar.YEAR),
                data2.get(Calendar.MONTH)+1,
                data2.get(Calendar.DAY_OF_MONTH),
                data2.get(Calendar.HOUR_OF_DAY),
                data2.get(Calendar.MINUTE)
        );

        for(int i=0; i< this.numClientes; i++){
            if(this.clientes[i].isAtivo()) {
                movimento = clientes[i].getMovimentosInDatesArray(data1,data2);
                if (!movimento.isEmpty()) {
                    for (int j = 0; j < movimento.size() - 1; j++) {
                        m1 = movimento.get(j);
                        m2 = movimento.get(j + 1);
                        if (m1.getTipo() == 'E' && m2.getTipo() == 'S') {
                            dataMov1 = LocalDateTime.of(
                                    m1.getData().get(Calendar.YEAR),
                                 m1.getData().get(Calendar.MONTH)+1,
                                    m1.getData().get(Calendar.DAY_OF_MONTH),
                                    m1.getData().get(Calendar.HOUR_OF_DAY),
                                    m1.getData().get(Calendar.MINUTE)
                            );
                            dataMov2 = LocalDateTime.of(
                                    m2.getData().get(Calendar.YEAR),
                                    m2.getData().get(Calendar.MONTH)+1,
                                    m2.getData().get(Calendar.DAY_OF_MONTH),
                                    m2.getData().get(Calendar.HOUR_OF_DAY),
                                    m2.getData().get(Calendar.MINUTE)
                            );
                            period = Duration.between(dataMov1, dataMov2);
                            horas = period.toHours();
                            faturacao += horas * PRECO_HORA;
                        }
                    }
                }
            }
        }
        return faturacao;
    }


    private int indexOfMatricula(String matricula){
        if (this.numClientes == 0)
            return -1;
        for (int i=0; i < this.numClientes; i++){
            if (this.clientes[i].getMatricula().equals(matricula))
                return i;
        }
        return -1;
    }

    private boolean matriculaExists(String matricula){
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


}

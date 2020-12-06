package com.jacksonbarreto;

import com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ParqueEstacionamento {
    private CartaoCliente[] clientes;
    private int numClientes;
    private int numClientesAtivos;
    private int numClientesEstacionados;
    private String morada;
    final private int maxClientes;
    final private int lotacao;

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

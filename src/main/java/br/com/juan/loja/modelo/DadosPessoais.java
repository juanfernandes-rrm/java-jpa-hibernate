package br.com.juan.loja.modelo;

import javax.persistence.Embeddable;

@Embeddable
public class DadosPessoais {
    private String nome;
    private String CPF;

    public DadosPessoais(String nome, String CPF) {
        this.nome = nome;
        this.CPF = CPF;
    }

    public DadosPessoais() {

    }

    public String getNome() {
        return nome;
    }

    public String getCPF() {
        return CPF;
    }
}

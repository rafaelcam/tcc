
package com.agrotime.entidades;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author João
 */
@XmlRootElement
public class Pessoa {
 
    private int id;
    private String nome;
    private String email;
    private Date nascimento;
    private Date dataCorrente;
    private int idade;  
 
    /**
     *
     */
    public Pessoa() {
        
    }
 
    public Pessoa(int id, String nome, String email, Date nascimento, Date dataCorrente, int idade) {
        super();
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.nascimento = nascimento;
        this.dataCorrente = dataCorrente;
        this.idade = idade;
    }
 
    public String getNome() {
        return nome;
    }
 
    public void setNome(String nome) {
        this.nome = nome;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public Date getNascimento() {
        return nascimento;
    }
 
    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }
 
    public Date getDataCorrente() {
        return dataCorrente;
    }
 
    public void setDataCorrente(Date dataCorrente) {
        this.dataCorrente = dataCorrente;
    }
 
    public int getIdade() {
        return idade;
    }
 
    public void setIdade(int idade) {
        this.idade = idade;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
}
package br.com.andresgois.FeignApplication.domain.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    private UUID id;
    private String nome;
    private String email;

    public Usuario(){}
    public Usuario(UUID id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
}

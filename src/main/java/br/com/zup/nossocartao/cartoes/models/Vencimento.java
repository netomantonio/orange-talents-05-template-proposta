package br.com.zup.nossocartao.cartoes.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Vencimento {

    @Id
    private String id;

    @NotNull
    private Integer dia;

    @NotNull
    private LocalDateTime dataCriacao;

    @Deprecated
    public Vencimento() {
    }
    public Vencimento(String id, Integer dia, LocalDateTime dataCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataCriacao = dataCriacao;
    }

    public Integer getDia() {
        return dia;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getId() {
        return id;
    }
}

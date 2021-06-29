package br.com.zup.nossocartao.cartoes.models;

import br.com.zup.nossocartao.cartoes.enums.TipoCarteiras;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @Enumerated(EnumType.STRING)

    private TipoCarteiras nome;

    @ManyToOne
    private Cartao cartao;

    private String idRetorno;


    @Deprecated
    public Carteira() {
    }

    public Carteira(String email, TipoCarteiras carteiraNome, Cartao cartaoRequest, String idRetorno) {

        this.email = email;
        this.nome = carteiraNome;
        this.cartao = cartaoRequest;
        this.idRetorno = idRetorno;
    }

    public Long getId() {
        return this.id;
    }
}

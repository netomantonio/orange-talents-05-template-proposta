package br.com.zup.nossocartao.biometrias.models;

import br.com.zup.nossocartao.cartoes.models.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Biometria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(columnDefinition = "text")
    private String imgBiometria;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Biometria() {
    }

    public Biometria(String imgBiometria, Cartao cartao) {
        this.imgBiometria = imgBiometria;
        this.cartao = cartao;
    }

}

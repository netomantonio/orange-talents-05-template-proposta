package br.com.zup.nossocartao.cartoes.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime instanteBloqueio = LocalDateTime.now();

    private String sistema;
    private boolean ativo;
    private String ipRequest;
    private String agentRequest;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(String sistema, boolean ativo, String ipRequest, String agentRequest, Cartao cartao) {
        this.sistema = sistema;
        this.ativo = ativo;
        this.ipRequest = ipRequest;
        this.agentRequest = agentRequest;
        this.cartao = cartao;
    }
}

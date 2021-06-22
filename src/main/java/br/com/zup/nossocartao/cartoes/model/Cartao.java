package br.com.zup.nossocartao.cartoes.model;

import br.com.zup.nossocartao.cartoes.compartilhado.StatusCartao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Cartao {

    @Id
    private String id;

    @NotNull
    private LocalDateTime emitidoEm;

    @NotNull
    private String titular;


    @Enumerated(EnumType.STRING)
    private StatusCartao cartaoBloqueado;


    private BigDecimal limite;

    @ManyToOne(cascade = CascadeType.ALL)
    private Vencimento vencimento;


    @Deprecated
    public Cartao() {}
    public Cartao(String id, LocalDateTime emitidoEm, String titular, BigDecimal limite, Vencimento vencimento) {
        this.cartaoBloqueado = StatusCartao.DESBLOQUEADO;
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.vencimento = vencimento;
    }
}

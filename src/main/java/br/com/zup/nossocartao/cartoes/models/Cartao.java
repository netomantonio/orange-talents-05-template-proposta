package br.com.zup.nossocartao.cartoes.models;

import br.com.zup.nossocartao.biometrias.models.Biometria;
import br.com.zup.nossocartao.cartoes.utils.StatusCartao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<Biometria> biometrias;

    @ManyToOne(cascade = CascadeType.ALL)
    private Vencimento vencimento;

    @Deprecated
    public Cartao() {
    }

    public Cartao(String id, LocalDateTime emitidoEm, String titular, BigDecimal limite, Vencimento vencimento) {
        this.cartaoBloqueado = StatusCartao.DESBLOQUEADO;
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.vencimento = vencimento;
    }


    public Cartao associaBiometria(List<Biometria> biometrias) {
        this.biometrias.addAll(biometrias);
        return this;
    }

    public String getId() {
        return this.id;
    }

    public StatusCartao getCartaoBloqueado() {
        return this.cartaoBloqueado;
    }

    public void bloquear() {
        this.cartaoBloqueado = StatusCartao.BLOQUEADO;
    }
}

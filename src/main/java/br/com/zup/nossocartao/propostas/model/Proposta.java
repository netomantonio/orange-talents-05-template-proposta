package br.com.zup.nossocartao.propostas.model;

import br.com.zup.nossocartao.cartoes.model.Cartao;
import br.com.zup.nossocartao.propostas.compartilhado.StatusProposta;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotNull
    @Positive
    private BigDecimal salario;
    @NotBlank
    @Column(unique = true)
    private String documento;

    @Enumerated(EnumType.STRING)
    private StatusProposta status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Cartao cartao;

    @Deprecated
    public Proposta(){}
    public Proposta(@Email @NotBlank String email,
                    @NotBlank String nome,
                    @NotBlank String endereco,
                    @NotNull @Positive BigDecimal salario,
                    @NotBlank String documento) {
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.documento = documento;
    }

    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getDocumento() {
        return this.documento;
    }

    public void setStatus(StatusProposta status) {
        this.status = status;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }
}

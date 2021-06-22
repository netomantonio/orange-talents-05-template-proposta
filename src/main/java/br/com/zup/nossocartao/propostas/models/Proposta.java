package br.com.zup.nossocartao.propostas.models;

import br.com.zup.nossocartao.cartoes.models.Cartao;
import br.com.zup.nossocartao.propostas.utils.StatusProposta;

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

    public StatusProposta getStatus() {
        return this.status;
    }

    public Boolean getTemCartao() {
        return (this.cartao != null);
    }

    public String getEmail() {
        return this.email;
    }
}

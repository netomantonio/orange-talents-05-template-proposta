package br.com.zup.nossocartao.propostas.requests;

import br.com.zup.nossocartao.annotations.CPForCNPJ;
import br.com.zup.nossocartao.propostas.models.Proposta;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    @CPForCNPJ(message = "ta errado fiao")
    @NotBlank
//    @Unique(domainClass = Proposta.class, fieldName = "documento", message = "já existe proposta para esse documento")
    private String documento;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    public NovaPropostaRequest(@NotBlank String documento,
                               @NotBlank @Email String email,
                               @NotBlank String nome,
                               @NotBlank String endereco,
                               @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta toModel() {
        return new Proposta(email, nome, endereco, salario, documento);
    }

    public String getDocumento() {
        return this.documento;
    }

    public String getEmail() {
        return this.email;
    }
}

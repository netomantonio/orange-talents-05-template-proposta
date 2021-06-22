package br.com.zup.nossocartao.cartoes.requests;

import br.com.zup.nossocartao.cartoes.model.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartaoRequest {


    @NotNull
    private String id;

    @NotNull
    @FutureOrPresent
    private LocalDateTime emitidoEm;

    @NotBlank
    private String titular;

    @NotNull
    @Positive
    private BigDecimal limite;


    @NotNull
    private Long idProposta;

    @NotEmpty
    private VencimentoRequest vencimento;
    @Deprecated
    public CartaoRequest() {
    }


    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public Long getIdProposta() {
        return idProposta;
    }

    @JsonCreator
    public CartaoRequest(String id, LocalDateTime emitidoEm, String titular,
                         BigDecimal limite, VencimentoRequest vencimento,
                         Long idProposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.vencimento = vencimento;
        this.idProposta = idProposta;
    }

    public Cartao toModel(){
        return new Cartao(this.id,this.emitidoEm,this.titular,this.limite,this.vencimento.toModel());

    }
}
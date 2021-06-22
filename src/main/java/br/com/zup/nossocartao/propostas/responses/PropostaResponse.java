package br.com.zup.nossocartao.propostas.responses;

import br.com.zup.nossocartao.propostas.models.Proposta;
import br.com.zup.nossocartao.propostas.utils.StatusProposta;

public class PropostaResponse {


    private final Long id;
    private final Boolean cartao;
    private final String solicitante;
    private final StatusProposta status;
    private final String email;

    public PropostaResponse(Proposta proposta) {
        this.id = proposta.getId();
        this.solicitante = proposta.getNome();
        this.status = proposta.getStatus();
        this.cartao = proposta.getTemCartao();
        this.email = proposta.getEmail();
    }

    public Long getId() {
        return id;
    }

    public Boolean getCartao() {
        return cartao;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public StatusProposta getStatus() {
        return status;
    }
}

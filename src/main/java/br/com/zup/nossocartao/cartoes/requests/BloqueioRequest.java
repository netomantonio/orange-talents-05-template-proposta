package br.com.zup.nossocartao.cartoes.requests;

import br.com.zup.nossocartao.cartoes.models.Bloqueio;
import br.com.zup.nossocartao.cartoes.models.Cartao;

public class BloqueioRequest {
    private String sistema;
    private boolean ativo;
    private String ipRequest;
    private String agentRequest;

    public BloqueioRequest(String sistema, boolean ativo, String ipRequest, String agentRequest) {

        this.sistema = sistema;
        this.ativo = ativo;
        this.ipRequest = ipRequest;
        this.agentRequest = agentRequest;
    }

    public Bloqueio toModel(Cartao cartao) {
        return new Bloqueio(this.sistema, this.ativo, this.ipRequest, this.agentRequest, cartao);
    }
}

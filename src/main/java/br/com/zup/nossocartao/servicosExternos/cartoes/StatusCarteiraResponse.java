package br.com.zup.nossocartao.servicosExternos.cartoes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StatusCarteiraResponse {

    @JsonProperty
    private StatusCarteira resultado;

    @JsonProperty
    private String id;

    @JsonCreator
    public StatusCarteiraResponse(StatusCarteira resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public StatusCarteira getResultado() {
        return this.resultado;
    }

    public String getId() {
        return this.id;
    }
}

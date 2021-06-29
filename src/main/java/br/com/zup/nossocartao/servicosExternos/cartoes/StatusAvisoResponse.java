package br.com.zup.nossocartao.servicosExternos.cartoes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StatusAvisoResponse {

    @JsonProperty
    private StatusAviso resultado;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public StatusAvisoResponse(StatusAviso resultado) {
        this.resultado = resultado;
    }


    public StatusAviso getResultado() {
        return resultado;
    }
}

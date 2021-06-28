package br.com.zup.nossocartao.servicosExternos.cartoes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SistemaRequest {

    @JsonProperty
    private String sistemaResponsavel;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SistemaRequest(String sistema) {
        this.sistemaResponsavel = sistema;
    }

}

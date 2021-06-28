package br.com.zup.nossocartao.cartoes.responses;

import br.com.zup.nossocartao.cartoes.enums.StatusBloqueio;
import com.fasterxml.jackson.annotation.JsonCreator;

public class BloqueioResponse {

    private StatusBloqueio resultado;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BloqueioResponse(StatusBloqueio resultado) {
        this.resultado = resultado;
    }

    public StatusBloqueio getResultado() {
        return this.resultado;
    }
}

package br.com.zup.nossocartao.cartoes.requests;

import br.com.zup.nossocartao.cartoes.enums.TipoCarteiras;
import br.com.zup.nossocartao.cartoes.models.Cartao;
import br.com.zup.nossocartao.cartoes.models.Carteira;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CarteiraDigitalRequest {

    @Email
    @NotEmpty
    @JsonProperty
    private String email;

    @NotNull
    @JsonProperty
    private TipoCarteiras carteira;

    public CarteiraDigitalRequest(String email, TipoCarteiras carteira) {
        this.email = email;
        this.carteira = carteira;
    }


    public Carteira toModel(Cartao cartaoRequest, String idRetorno) {
        return new Carteira(this.email, this.carteira, cartaoRequest, idRetorno);
    }

    public String getCarteira() {
        return this.carteira.name();
    }
}

package br.com.zup.nossocartao.cartoes.requests;

import br.com.zup.nossocartao.cartoes.models.AvisoViagem;
import br.com.zup.nossocartao.cartoes.models.Cartao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    @JsonProperty
    private String destino;

    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty
    private LocalDate validoAte;

    public AvisoViagemRequest(String destino, LocalDate dataFimViagem) {

        this.destino = destino;
        this.validoAte = dataFimViagem;
    }

    public AvisoViagem toModel(Cartao cartao, String ip, String userAgent) {
        return new AvisoViagem(cartao, this.destino, this.validoAte, ip, userAgent);
    }
}

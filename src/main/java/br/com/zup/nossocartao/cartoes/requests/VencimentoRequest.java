package br.com.zup.nossocartao.cartoes.requests;


import br.com.zup.nossocartao.cartoes.model.Vencimento;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.LocalDateTime;

public class VencimentoRequest {

    private String id;

    private Integer dia;

    private LocalDateTime dataDeCriacao;

    public VencimentoRequest() {
    }

    public String getId() {
        return id;
    }

    public Integer getDia() {
        return dia;
    }


    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

    @JsonCreator
    public VencimentoRequest(String id, Integer dia, LocalDateTime dataCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataCriacao;
    }

    public Vencimento toModel(){
        return new Vencimento(id,dia,dataDeCriacao);
    }
}

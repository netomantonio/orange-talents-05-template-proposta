package br.com.zup.nossocartao.proposta.request;

import br.com.zup.nossocartao.proposta.model.Proposta;

public class ConsultaDadosRequest {

    private Long idProposta;

    private String nome;

    private String documento;

    public Long getIdProposta() {
        return idProposta;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public ConsultaDadosRequest(Proposta proposta)   {

        this.idProposta = proposta.getId();
        this.nome = proposta.getNome();
        this.documento = proposta.getDocumento();
    }
}

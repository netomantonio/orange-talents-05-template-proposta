package br.com.zup.nossocartao.servicosExternos.analiseFinanceira;

import br.com.zup.nossocartao.propostas.utils.StatusProposta;

public class ConsultaDadosPropostaResponse {
    private Long idProposta;

    private String nome;

    private String documento;

    private PropostaRetorno resultadoSolicitacao;

    public ConsultaDadosPropostaResponse(Long idProposta, String nome,
                                         String documento, PropostaRetorno resultadoSolicitacao) {
        this.idProposta = idProposta;
        this.nome = nome;
        this.documento = documento;
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public PropostaRetorno getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public StatusProposta retornStatus() {
        return this.resultadoSolicitacao.retorno();
    }
}

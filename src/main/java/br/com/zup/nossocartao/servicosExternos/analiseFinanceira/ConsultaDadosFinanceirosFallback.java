package br.com.zup.nossocartao.servicosExternos.analiseFinanceira;

import br.com.zup.nossocartao.propostas.requests.ConsultaDadosRequest;
import org.springframework.stereotype.Component;

@Component
public class ConsultaDadosFinanceirosFallback implements ConsultaDados {

    @Override
    public ConsultaDadosPropostaResponse consultaDados(ConsultaDadosRequest request) {
        return new ConsultaDadosPropostaResponse(request.getIdProposta(), request.getDocumento(), request.getNome(), PropostaRetorno.COM_RESTRICAO);
    }
}

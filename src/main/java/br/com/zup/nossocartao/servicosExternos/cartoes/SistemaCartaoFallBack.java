package br.com.zup.nossocartao.servicosExternos.cartoes;

import br.com.zup.nossocartao.cartoes.enums.StatusBloqueio;
import br.com.zup.nossocartao.cartoes.requests.AvisoViagemRequest;
import br.com.zup.nossocartao.cartoes.requests.CartaoRequest;
import br.com.zup.nossocartao.cartoes.requests.CarteiraDigitalRequest;
import br.com.zup.nossocartao.cartoes.responses.BloqueioResponse;
import org.springframework.stereotype.Component;

@Component
public class SistemaCartaoFallBack implements SistemaCartao {

    @Override
    public CartaoRequest gerarCartao(String idProposta) {
        return null;
    }

    @Override
    public BloqueioResponse bloquearCartao(String idCartao, SistemaRequest sistemaResponsavel) {
        return new BloqueioResponse(StatusBloqueio.FALHA);
    }

    @Override
    public StatusAvisoResponse adicionarAviso(String idCartao, AvisoViagemRequest avisoViagemRequest) {
        return new StatusAvisoResponse(StatusAviso.FALHA);
    }

    @Override
    public StatusCarteiraResponse adicionaCarteira(String idCartao, CarteiraDigitalRequest carteiraRequest) {
        return new StatusCarteiraResponse(StatusCarteira.FALHA, null);
    }
}

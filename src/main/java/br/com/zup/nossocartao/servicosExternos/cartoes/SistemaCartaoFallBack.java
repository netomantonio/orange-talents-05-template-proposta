package br.com.zup.nossocartao.servicosExternos.cartoes;

import br.com.zup.nossocartao.cartoes.enums.StatusBloqueio;
import br.com.zup.nossocartao.cartoes.requests.CartaoRequest;
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
}

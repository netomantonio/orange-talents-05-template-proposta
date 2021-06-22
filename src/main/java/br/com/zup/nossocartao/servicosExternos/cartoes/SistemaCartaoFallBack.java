package br.com.zup.nossocartao.servicosExternos.cartoes;

import br.com.zup.nossocartao.cartoes.requests.CartaoRequest;
import org.springframework.stereotype.Component;

@Component
public class SistemaCartaoFallBack implements SistemaCartao {

    @Override
    public CartaoRequest gerarCartao(String idProposta) {
        return null;
    }
}

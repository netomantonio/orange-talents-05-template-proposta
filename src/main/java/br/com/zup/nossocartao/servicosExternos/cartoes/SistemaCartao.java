package br.com.zup.nossocartao.servicosExternos.cartoes;

import br.com.zup.nossocartao.cartoes.requests.CartaoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name= "cartao", url = "${cartao.url}", fallback = SistemaCartaoFallBack.class)
public interface SistemaCartao {

    @GetMapping
    CartaoRequest gerarCartao(@RequestParam String idProposta);
}

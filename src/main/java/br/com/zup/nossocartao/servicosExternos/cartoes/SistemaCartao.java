package br.com.zup.nossocartao.servicosExternos.cartoes;

import br.com.zup.nossocartao.cartoes.requests.CartaoRequest;
import br.com.zup.nossocartao.cartoes.responses.BloqueioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cartao", url = "${cartao.url}", fallback = SistemaCartaoFallBack.class)
public interface SistemaCartao {

    @GetMapping
    CartaoRequest gerarCartao(@RequestParam String idProposta);

    @RequestMapping(method = RequestMethod.POST, path = "/{idCartao}/bloqueios", consumes = "application/json")
    BloqueioResponse bloquearCartao(@PathVariable("idCartao") String idCartao, @RequestBody SistemaRequest sistemaResponsavel);
}
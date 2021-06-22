package br.com.zup.nossocartao.servicosExternos.analiseFinanceira;

import br.com.zup.nossocartao.propostas.requests.ConsultaDadosRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name= "consultaDados", url = "${analise.proposta}", fallback = ConsultaDadosFinanceirosFallback.class)
public interface ConsultaDados{

    @PostMapping("${analise.proposta.recurso}")
    ConsultaDadosPropostaResponse consultaDados(@RequestBody ConsultaDadosRequest propostaConsultaDadosRequest);
}

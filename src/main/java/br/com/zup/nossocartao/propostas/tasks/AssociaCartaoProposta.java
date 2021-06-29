package br.com.zup.nossocartao.propostas.tasks;

import br.com.zup.nossocartao.cartoes.models.Cartao;
import br.com.zup.nossocartao.cartoes.requests.CartaoRequest;
import br.com.zup.nossocartao.propostas.models.Proposta;
import br.com.zup.nossocartao.propostas.repositories.PropostaRepository;
import br.com.zup.nossocartao.servicosExternos.cartoes.SistemaCartao;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssociaCartaoProposta {

    @Autowired
    PropostaRepository propostaRepository;

    @Autowired
    SistemaCartao sistemaCartao;

    @Autowired
    Tracer tracer;

    @Scheduled(cron = "5/10 * * * * * ")
    public void associaCartaoaPropostasElegiveis() {

        List<Proposta> propostasElegiveisSemCartao = propostaRepository.findElegiveis();
        for (Proposta proposta : propostasElegiveisSemCartao) {
            Span activeSpan = tracer.activeSpan();
            activeSpan.setTag("associa-proposta.email", proposta.getEmail());
            try {
                CartaoRequest cartaoRequest = sistemaCartao.gerarCartao(proposta.getId().toString());
                Cartao cartao = cartaoRequest.toModel();
                if (cartao == null) throw new Exception("Sistema de associação de cartões indisponivel");
                proposta.setCartao(cartao);
                propostaRepository.save(proposta);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}

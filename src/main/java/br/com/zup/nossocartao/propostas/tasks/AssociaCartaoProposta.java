package br.com.zup.nossocartao.propostas.tasks;

import br.com.zup.nossocartao.cartoes.model.Cartao;
import br.com.zup.nossocartao.cartoes.repository.CartaoRepository;
import br.com.zup.nossocartao.cartoes.requests.CartaoRequest;
import br.com.zup.nossocartao.propostas.model.Proposta;
import br.com.zup.nossocartao.propostas.repository.PropostaRepository;
import br.com.zup.nossocartao.servicosExternos.cartoes.SistemaCartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssociaCartaoProposta {

    @Autowired
    PropostaRepository propostaRepository;

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    SistemaCartao sistemaCartao;


    @Scheduled(cron = "5/10 * * * * * ")
    public void associaCartaoaPropostasElegiveis() {

        List<Proposta> propostasElegiveisSemCartao = propostaRepository.findElegiveis();
        for (Proposta proposta : propostasElegiveisSemCartao) {
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

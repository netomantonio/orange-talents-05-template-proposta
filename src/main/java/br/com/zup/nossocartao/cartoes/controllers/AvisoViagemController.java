package br.com.zup.nossocartao.cartoes.controllers;

import br.com.zup.nossocartao.cartoes.models.AvisoViagem;
import br.com.zup.nossocartao.cartoes.models.Cartao;
import br.com.zup.nossocartao.cartoes.repositories.CartaoRepository;
import br.com.zup.nossocartao.cartoes.requests.AvisoViagemRequest;
import br.com.zup.nossocartao.cartoes.utils.StatusCartao;
import br.com.zup.nossocartao.errors.ErrorsResponse;
import br.com.zup.nossocartao.servicosExternos.cartoes.SistemaCartao;
import br.com.zup.nossocartao.servicosExternos.cartoes.StatusAviso;
import br.com.zup.nossocartao.servicosExternos.cartoes.StatusAvisoResponse;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
public class AvisoViagemController {

    @Autowired
    CartaoRepository cartaoRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    SistemaCartao sistemaCartao;

    @Autowired
    Tracer tracer;

    @Autowired
    private MeterRegistry metrics;

    @Transactional
    @PostMapping("/api/cartoes/avisos/viagens")
    public ResponseEntity<?> avisar(@PathParam("idCartao") String idCartao,
                                    @RequestBody @Valid AvisoViagemRequest avisoViagemRequest,
                                    HttpServletRequest request) {
        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);
        if (possivelCartao.isEmpty()) return ResponseEntity.notFound().build();
        Cartao cartaoRequest = possivelCartao.get();
        Counter contadorAvisoViagem = metrics.counter("aviso-viagem-created", "cartao-id-hash", cartaoRequest.toString());
        contadorAvisoViagem.increment();
        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("cartao.id", cartaoRequest.getId());

        if (cartaoRequest.getCartaoBloqueado().equals(StatusCartao.BLOQUEADO)) {
            return ResponseEntity.badRequest().body(
                    new ErrorsResponse("cartão", "Cartão está bloqueado")
            );
        }
        StatusAvisoResponse statusAviso = sistemaCartao.adicionarAviso(idCartao, avisoViagemRequest);

        if (statusAviso.getResultado().equals(StatusAviso.FALHA)) {

            return ResponseEntity.badRequest().body(new ErrorsResponse("Avisos", "falha na tentativa do aviso de viagem"));

        }

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        AvisoViagem avisoViagem = avisoViagemRequest.toModel(cartaoRequest, ip, userAgent);

        em.persist(avisoViagem);

        return ResponseEntity.ok().build();
    }
}

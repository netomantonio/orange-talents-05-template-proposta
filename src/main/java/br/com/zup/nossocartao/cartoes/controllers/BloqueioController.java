package br.com.zup.nossocartao.cartoes.controllers;

import br.com.zup.nossocartao.cartoes.enums.StatusBloqueio;
import br.com.zup.nossocartao.cartoes.models.Bloqueio;
import br.com.zup.nossocartao.cartoes.models.Cartao;
import br.com.zup.nossocartao.cartoes.repositories.BloqueioRepository;
import br.com.zup.nossocartao.cartoes.repositories.CartaoRepository;
import br.com.zup.nossocartao.cartoes.requests.BloqueioRequest;
import br.com.zup.nossocartao.cartoes.responses.BloqueioResponse;
import br.com.zup.nossocartao.cartoes.utils.StatusCartao;
import br.com.zup.nossocartao.errors.ErrorsResponse;
import br.com.zup.nossocartao.servicosExternos.cartoes.SistemaCartao;
import br.com.zup.nossocartao.servicosExternos.cartoes.SistemaRequest;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
public class BloqueioController {

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    BloqueioRepository bloqueioRepository;

    @Autowired
    SistemaCartao sistemaCartao;

    @Autowired
    Tracer tracer;

    @PostMapping("/api/cartoes/bloqueio")
    public ResponseEntity<?> bloquear(@PathParam("idCartao") String idCartao, HttpServletRequest request) {
        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);
        if (possivelCartao.isEmpty()) return ResponseEntity.notFound().build();
        Cartao cartaoRequest = possivelCartao.get();

        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("cartao.id", cartaoRequest.getId());

        if (cartaoRequest.getCartaoBloqueado().equals(StatusCartao.BLOQUEADO)) {
            return ResponseEntity.unprocessableEntity().body(
                    new ErrorsResponse("cartão", "Cartão já está bloqueado")
            );
        }
        BloqueioResponse statusBloqueio = sistemaCartao.bloquearCartao(idCartao, new SistemaRequest("api-propostas"));
        if (statusBloqueio.getResultado().equals(StatusBloqueio.FALHA)) {
            return ResponseEntity.badRequest().body(
                    new ErrorsResponse("cartao", "Falha na tentativa de bloqueio")
            );
        }

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        BloqueioRequest bloqueioRequest = new BloqueioRequest("propostas", false, ip, userAgent);
        Bloqueio bloqueio = bloqueioRequest.toModel(cartaoRequest);

        cartaoRequest.bloquear();
        cartaoRepository.save(cartaoRequest);

        bloqueioRepository.save(bloqueio);

        return ResponseEntity.ok().build();
    }
}

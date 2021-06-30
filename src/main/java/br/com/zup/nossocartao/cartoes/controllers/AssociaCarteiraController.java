package br.com.zup.nossocartao.cartoes.controllers;

import br.com.zup.nossocartao.cartoes.models.Cartao;
import br.com.zup.nossocartao.cartoes.models.Carteira;
import br.com.zup.nossocartao.cartoes.repositories.CartaoRepository;
import br.com.zup.nossocartao.cartoes.repositories.CarteiraRepository;
import br.com.zup.nossocartao.cartoes.requests.CarteiraDigitalRequest;
import br.com.zup.nossocartao.cartoes.utils.StatusCartao;
import br.com.zup.nossocartao.errors.ErrorsResponse;
import br.com.zup.nossocartao.servicosExternos.cartoes.SistemaCartao;
import br.com.zup.nossocartao.servicosExternos.cartoes.StatusCarteira;
import br.com.zup.nossocartao.servicosExternos.cartoes.StatusCarteiraResponse;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.Optional;

@RestController
public class AssociaCarteiraController {

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    SistemaCartao sistemaCartao;

    @Autowired
    CarteiraRepository carteiraRepository;

    @Autowired
    Tracer tracer;

    @Autowired
    private MeterRegistry metrics;

    @PostMapping("/api/cartoes/carteiras")
    public ResponseEntity<?> associar(@PathParam("idCartao") String idCartao,
                                      @Valid @RequestBody CarteiraDigitalRequest carteiraDigitalRequest,
                                      UriComponentsBuilder builder) {
        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);
        if (possivelCartao.isEmpty()) return ResponseEntity.notFound().build();
        Cartao cartaoRequest = possivelCartao.get();
        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("cartao.id", cartaoRequest.getId());
        Counter contadorAssociaCarteira = metrics.counter("associa-carteira-created", "cartao-id-hash", cartaoRequest.toString());
        contadorAssociaCarteira.increment();
        if (cartaoRequest.getCartaoBloqueado().equals(StatusCartao.BLOQUEADO)) {
            return ResponseEntity.badRequest().body(
                    new ErrorsResponse("cartão", "Cartão está bloqueado")
            );
        }
        var returno = verificaCarteiraAssociada(carteiraDigitalRequest, cartaoRequest);
        if (returno) {
            return ResponseEntity.unprocessableEntity().body(new ErrorsResponse("Carteira ", "A carteira já está associada"));
        }

        StatusCarteiraResponse statusCarteira = sistemaCartao.adicionaCarteira(idCartao, carteiraDigitalRequest);
        if (statusCarteira.getResultado().equals(StatusCarteira.FALHA)) {
            return ResponseEntity.badRequest().body(new ErrorsResponse("Carteira ", "Falha na tentativa de associar carteira"));
        }

        Carteira carteira = carteiraDigitalRequest.toModel(cartaoRequest, statusCarteira.getId());

        carteiraRepository.save(carteira);

        URI uri = builder.path("cartao/{idCartao}/carteiras/{idCarteira}").buildAndExpand(cartaoRequest.getId(), carteira.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    private boolean verificaCarteiraAssociada(CarteiraDigitalRequest carteiraDigitalRequest, Cartao cartao) {
        Optional<Carteira> carteira = carteiraRepository.findByNomeAndCartaoId(carteiraDigitalRequest.getCarteira(), cartao.getId());
        return carteira.isPresent();
    }
}

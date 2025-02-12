package br.com.zup.nossocartao.biometrias.controllers;

import br.com.zup.nossocartao.biometrias.requests.BiometriaRequest;
import br.com.zup.nossocartao.cartoes.models.Cartao;
import br.com.zup.nossocartao.cartoes.repositories.CartaoRepository;
import br.com.zup.nossocartao.errors.ErrorsResponse;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class AdicionaBiometriaController {

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    Tracer tracer;

    @Autowired
    private MeterRegistry metrics;

    @PostMapping("/api/cartoes/{id}/biometrias")
    public ResponseEntity<?> adicionar(@RequestBody @Valid BiometriaRequest biometriaRequest, @PathVariable String id, UriComponentsBuilder uriBuilder) {

        Optional<Cartao> cartao = cartaoRepository.findById(id);

        if (cartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("cartao.id", cartao.get().getId());
        List<ErrorsResponse> errorsResponses = biometriaRequest.verificaBase64();

        if (!errorsResponses.isEmpty()) {
            return ResponseEntity.badRequest().body(errorsResponses);
        }
        Counter contadorAssociaBiometria = metrics.counter("associa-biometria-created", "cartao-id-hash", cartao.get().toString());
        contadorAssociaBiometria.increment();

        Cartao cartaoComBiometria = cartao.get().associaBiometria(biometriaRequest.toModel(cartao.get()));

        cartaoRepository.save(cartaoComBiometria);
        URI uri = uriBuilder.path("/api/cartoes/{id}/biometrias/").buildAndExpand(cartao.get().getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
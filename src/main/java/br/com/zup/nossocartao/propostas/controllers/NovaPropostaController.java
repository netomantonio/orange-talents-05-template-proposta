package br.com.zup.nossocartao.propostas.controllers;

import br.com.zup.nossocartao.propostas.models.Proposta;
import br.com.zup.nossocartao.propostas.requests.ConsultaDadosRequest;
import br.com.zup.nossocartao.propostas.requests.NovaPropostaRequest;
import br.com.zup.nossocartao.servicosExternos.analiseFinanceira.ConsultaDados;
import br.com.zup.nossocartao.servicosExternos.analiseFinanceira.ConsultaDadosPropostaResponse;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class NovaPropostaController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private ConsultaDados consultaDados;

    @Autowired
    Tracer tracer;

    @Autowired
    private MeterRegistry metrics;

    @PostMapping("/api/propostas")
    @Transactional
    public ResponseEntity<?> novaProposta(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder builder) {
        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("user.email", request.getEmail());
        Query query = manager.createQuery(
                "select 1 from Proposta where documento=:value"
        );
        query.setParameter("value", request.getDocumento());
        if (query.getResultList().size() != 0) return ResponseEntity.status(422).build();

        Proposta novaProposta = request.toModel();
        manager.persist(novaProposta);
        Counter contadorNovaProposta = metrics.counter("nova-proposta-created");
        contadorNovaProposta.increment();
        ConsultaDadosPropostaResponse consultaDadosPropostaResponse = consultaDados.consultaDados(new ConsultaDadosRequest(novaProposta));
        novaProposta.setStatus(consultaDadosPropostaResponse.retornStatus());

        URI endConsulta = builder.path("/api/propostas/{id}").build(novaProposta.getId());
        return ResponseEntity.created(endConsulta).build();
    }
}

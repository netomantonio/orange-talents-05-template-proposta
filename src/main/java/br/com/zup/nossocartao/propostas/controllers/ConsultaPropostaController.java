package br.com.zup.nossocartao.propostas.controllers;

import br.com.zup.nossocartao.propostas.models.Proposta;
import br.com.zup.nossocartao.propostas.repositories.PropostaRepository;
import br.com.zup.nossocartao.propostas.responses.PropostaResponse;
import feign.Param;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsultaPropostaController {

    @Autowired
    PropostaRepository propostaRepository;

    @Autowired
    Tracer tracer;

    @GetMapping("/api/propostas")
    public ResponseEntity<?> consulta(@Param Long propostaId) {
        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("proposta.id", propostaId);
        Proposta proposta = propostaRepository.findById(propostaId).orElseThrow();
        PropostaResponse propostaResponse = new PropostaResponse(proposta);

        return ResponseEntity.ok().body(propostaResponse);
    }
}

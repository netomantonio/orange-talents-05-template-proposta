package br.com.zup.nossocartao.novaProposta.controller;

import br.com.zup.nossocartao.novaProposta.model.Proposta;
import br.com.zup.nossocartao.novaProposta.request.NovaPropostaRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class NovaPropostaController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/api/propostas")
    @Transactional
    public ResponseEntity<?> novaProposta(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder builder){
        Proposta novaProposta = request.toModel();
        manager.persist(novaProposta);

        URI endConsulta = builder.path("/api/propostas/{id}").build(novaProposta.getId());
        return ResponseEntity.created(endConsulta).build();
    }
}
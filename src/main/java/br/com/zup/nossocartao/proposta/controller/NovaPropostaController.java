package br.com.zup.nossocartao.proposta.controller;

import br.com.zup.nossocartao.proposta.model.Proposta;
import br.com.zup.nossocartao.proposta.request.ConsultaDadosRequest;
import br.com.zup.nossocartao.proposta.request.NovaPropostaRequest;
import br.com.zup.nossocartao.servicosExternos.analiseFinanceira.ConsultaDados;
import br.com.zup.nossocartao.servicosExternos.analiseFinanceira.ConsultaDadosPropostaResponse;
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

    @PostMapping("/api/propostas")
    @Transactional
    public ResponseEntity<?> novaProposta(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder builder){
        Query query = manager.createQuery(
                "select 1 from Proposta where documento=:value"
        );
        query.setParameter("value", request.getDocumento());
        if(query.getResultList().size() != 0) return ResponseEntity.status(422).build();

        Proposta novaProposta = request.toModel();
        manager.persist(novaProposta);

        ConsultaDadosPropostaResponse consultaDadosPropostaResponse = consultaDados.consultaDados(new ConsultaDadosRequest(novaProposta));
        novaProposta.setStatus(consultaDadosPropostaResponse.retornStatus());

        URI endConsulta = builder.path("/api/propostas/{id}").build(novaProposta.getId());
        return ResponseEntity.created(endConsulta).build();
    }
}
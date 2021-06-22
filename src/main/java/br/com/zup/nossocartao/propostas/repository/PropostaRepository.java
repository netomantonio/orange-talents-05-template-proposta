package br.com.zup.nossocartao.propostas.repository;

import br.com.zup.nossocartao.propostas.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    @Query(value = "select * from proposta where status = 'ELEGIVEL' and cartao_id is null", nativeQuery = true)
    List<Proposta> findElegiveis();
}

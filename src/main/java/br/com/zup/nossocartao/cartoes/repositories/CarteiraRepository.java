package br.com.zup.nossocartao.cartoes.repositories;

import br.com.zup.nossocartao.cartoes.models.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
    @Query(value = "select * from carteira where nome = :carteira and cartao_id = :id", nativeQuery = true)
    Optional<Carteira> findByNomeAndCartaoId(String carteira, String id);
}

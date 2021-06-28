package br.com.zup.nossocartao.cartoes.repositories;

import br.com.zup.nossocartao.cartoes.models.Bloqueio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloqueioRepository extends JpaRepository<Bloqueio, Long> {
}

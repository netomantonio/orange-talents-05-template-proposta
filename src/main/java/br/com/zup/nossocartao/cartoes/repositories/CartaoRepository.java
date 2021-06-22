package br.com.zup.nossocartao.cartoes.repositories;

import br.com.zup.nossocartao.cartoes.models.Cartao;
import org.springframework.data.repository.CrudRepository;

public interface CartaoRepository extends CrudRepository<Cartao, String> {
}

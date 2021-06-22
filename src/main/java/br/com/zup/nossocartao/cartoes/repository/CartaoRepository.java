package br.com.zup.nossocartao.cartoes.repository;

import br.com.zup.nossocartao.cartoes.model.Cartao;
import org.springframework.data.repository.CrudRepository;

public interface CartaoRepository extends CrudRepository<Cartao, String> {
}

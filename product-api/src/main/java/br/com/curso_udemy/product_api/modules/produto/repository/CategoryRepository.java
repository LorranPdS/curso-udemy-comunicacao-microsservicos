package br.com.curso_udemy.product_api.modules.produto.repository;

import br.com.curso_udemy.product_api.modules.produto.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/*
Não foi necessário colocarmos uma anotação @Repository nessa nossa interface porque
nós já estamos estendendo uma classe "JpaRepository" e em algum lugar dela já
está sendo usado o @Repository, então não irá precisar dessa annotation.

CASO você queira fazer algum tipo de chamada via JDBC dentro dessa interface, daí sim
você precisaria colocar o @Repository nela para identificar essa interface como
um componente do Spring
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}

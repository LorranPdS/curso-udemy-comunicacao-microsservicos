package br.com.curso_udemy.product_api.modules.category.repository;

import br.com.curso_udemy.product_api.modules.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
Não foi necessário colocarmos uma anotação @Repository nessa nossa interface porque
nós já estamos estendendo uma classe "JpaRepository" e em algum lugar dela já
está sendo usado o @Repository, então não irá precisar dessa annotation.

CASO você queira fazer algum tipo de chamada via JDBC dentro dessa interface, daí sim
você precisaria colocar o @Repository nela para identificar essa interface como
um componente do Spring
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /*
        Observações:
        1. abaixo, o JPA irá identificar que existe um campo "description" na nossa tabela e irá
            criar um mapeamento para nós com uma query gerada pelo Spring Data e assim não
            precisaremos implementar essa query na mão
        2. estando com a assinatura 'List<Category> findByDescriptionIgnoreCaseContaining(String description)',
            garantimos que o JPA irá mapear o script de maneira que ignora maiúscula e minúscula e coloca
            um alias entre a description no SELECT. Por exemplo:
            - SELECT * FROM Category where description like '%conteudo%';
     */
    List<Category> findByDescriptionIgnoreCaseContaining(String description);
}

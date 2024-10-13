package br.com.curso_udemy.product_api.modules.produto.repository;

import br.com.curso_udemy.product_api.modules.produto.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}

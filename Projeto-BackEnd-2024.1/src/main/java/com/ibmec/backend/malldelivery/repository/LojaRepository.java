package com.ibmec.backend.malldelivery.repository;

import com.ibmec.backend.malldelivery.model.Loja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Integer> {
    Optional<Loja> findByCnpj(String cnpj);
}

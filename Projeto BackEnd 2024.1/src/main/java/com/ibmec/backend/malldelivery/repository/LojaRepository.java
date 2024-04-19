package com.ibmec.backend.malldelivery.repository;

import com.ibmec.backend.malldelivery.model.Loja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Integer> {
}

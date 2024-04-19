package com.ibmec.backend.malldelivery.repository;

import com.ibmec.backend.malldelivery.model.DadoBancario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DadoBancarioRepository extends JpaRepository<DadoBancario, Integer> {
}

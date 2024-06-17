package com.ibmec.backend.malldelivery.repository;

import com.ibmec.backend.malldelivery.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}

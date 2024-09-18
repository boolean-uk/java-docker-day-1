package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
}

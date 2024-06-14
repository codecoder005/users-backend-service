package com.company.repository;

import com.company.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    Optional<CustomerEntity> findByAadharNo(@Param("aadharNo") String aadharNo);

    Optional<CustomerEntity> findByPan(@Param("pan") String pan);
}

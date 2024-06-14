package com.company.repository;

import com.company.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, UUID> {
    @Query(value = """
    SELECT account FROM BankAccountEntity account
    WHERE account.customer.uuid = :customerUUID
    """)
    Optional<BankAccountEntity> findByCustomerUUID(@Param("customerUUID") final UUID uuid);

    @Query(value = """
    DELETE FROM BankAccountEntity account
    WHERE account.customer.uuid = :customerUUID
    """)
    @Modifying
    void deleteBankAccountByCustomerUUID(@Param("customerUUID") final UUID uuid);
}

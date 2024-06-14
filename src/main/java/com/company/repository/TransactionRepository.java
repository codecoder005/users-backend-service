package com.company.repository;

import com.company.entity.TransactionEntity;
import com.company.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    Optional<List<TransactionEntity>> findBySender(@Param("sender") UUID senderUUID);

    Optional<List<TransactionEntity>> findByReceiver(@Param("receiver") UUID receiverUUID);

    Optional<List<TransactionEntity>> findByTypeEquals(@Param("type")TransactionType type);
}

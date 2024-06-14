package com.company.repository;

import com.company.node.TeamNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.UUID;

public interface TeamRepository extends Neo4jRepository<TeamNode, UUID> {

}

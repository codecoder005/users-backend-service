package com.company.node;

import com.company.common.AppConstants;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Node(labels = "PLAYER")
public class PlayerNode {
    @Id
    @GeneratedValue(generatorClass = GeneratedValue.UUIDGenerator.class)
    private UUID playerId;

    private String name;
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private List<PlayerRole> roles;

    private Float height;
    private Float weight;
    private String country;
    private String state;

    @Relationship(AppConstants.GraphDB.Relationship.PLAYED_FOR)
    private List<TeamNode> playedFor;

    @Relationship(AppConstants.GraphDB.Relationship.CAPTAINED)
    private List<TeamNode> captainedFor;

    @Relationship(AppConstants.GraphDB.Relationship.CAPTAINS)
    private TeamNode captainsFor;

    @Relationship(AppConstants.GraphDB.Relationship.PLAYS_FOR)
    private TeamNode playsFor;
}

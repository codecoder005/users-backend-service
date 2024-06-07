package com.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "customers")
public class CustomerEntity extends BaseEntity{
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String aadharNo;

    @Column(unique = true)
    private String pan;
    private String email;
    private String phoneNo;

    @Enumerated(EnumType.STRING)
    private KYCStatus kycStatus;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private BankAccountEntity bankAccount;
}

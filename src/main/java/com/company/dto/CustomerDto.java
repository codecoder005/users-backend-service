package com.company.dto;

import com.company.entity.KYCStatus;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto implements Serializable {
    private UUID uuid;
    private String name;
    private String aadharNo;
    private String pan;
    private String email;
    private String phoneNo;

    private KYCStatus kycStatus;
}

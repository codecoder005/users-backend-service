package com.company.model;

import com.company.model.response.AuthenticationResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationMutationOperations {
    private AuthenticationResponse login;
    private Boolean register;
}

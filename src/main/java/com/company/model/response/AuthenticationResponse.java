package com.company.model.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse implements Serializable {
    private UUID uid;
    private String name;
    private String email;
    private List<String> roles;
}

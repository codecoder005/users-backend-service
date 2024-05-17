package com.company.docx;

import com.company.common.AppConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

import static com.company.common.AppConstants.Names.SECURITY_REQUIREMENT_BEARER_AUTH;

@OpenAPIDefinition(
        info = @Info(
                title = "Users Backend System",
                version = "1.0.0",
                description = """
                This backend system provides essential user management functionalities. It includes endpoints for user registration and authentication.
                The registration endpoint allows users to create new accounts by providing their email, name, and password.
                The authentication endpoint validates user credentials and provides access to the system, generating an authentication token upon successful login.
                """,
                termsOfService = "terms of service",
                contact = @Contact(
                        name = "Ramakrishna Janapureddy",
                        email = "developer.raakhi005@gmail.com"
                ),
                license = @License(
                        name = "Apache License 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        servers = {
                @Server(
                        description = "Local",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Development",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Stage",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Production",
                        url = "http://localhost:8080"
                )
        },
        security = {
                @SecurityRequirement(name = SECURITY_REQUIREMENT_BEARER_AUTH)
        }
)
@SecurityScheme(
        name = SECURITY_REQUIREMENT_BEARER_AUTH,
        description = "Bearer Authentication using JWT",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIDocxConfig {

}

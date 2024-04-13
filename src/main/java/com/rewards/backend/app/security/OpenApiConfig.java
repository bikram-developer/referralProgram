package com.rewards.backend.app.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
info = @Info(contact = @Contact(name = "Bikram Mitra Open API documentations"),
description = "Swagger Documentation",
termsOfService = "Terms of Service"),
security = {
		@SecurityRequirement(name = "bearerAuth")
		}
)
@SecurityScheme
(name = "bearerAuth", description = "JWT Auth description", 
scheme = "bearer", type = SecuritySchemeType.HTTP, 
bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {

}

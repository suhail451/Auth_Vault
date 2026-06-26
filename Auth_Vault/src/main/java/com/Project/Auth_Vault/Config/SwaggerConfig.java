package com.Project.Auth_Vault.Config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

@Bean
    public OpenAPI openAPI(){
    return new OpenAPI()
            .info(new Info()
                    .title("Auth Vault API")
                    .description("Production ready Authentication service")
                    .version("1.0.0")
                    .contact(new Contact()
                            .name("Suhail")
                            .email("arainsuhail451@gmal.com")));

}


}

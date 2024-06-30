package by.powerssolutions.vadarod.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig {

    @Value("${nbrb.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI setOpenApi() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Developer environment");
        Contact contact = new Contact();
        contact.setEmail("A.Nortin@gmail.com");
        contact.setName("Nortin");
        contact.setUrl("https://github.com/NortinPowers");
        License licence = new License()
                .name("MIT Licence")
                .url("https://choosealicence.com/licence/mit/");
        Info info = new Info()
                .title("NBRB API")
                .version("1.0")
                .contact(contact)
                .description("API for receiving and displaying exchange rates from the NBRB website")
                .license(licence);
        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}

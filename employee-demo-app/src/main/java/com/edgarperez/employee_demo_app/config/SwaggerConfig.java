package com.edgarperez.employee_demo_app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.web.servlet.function.RouterFunction;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Demo App")
                        .description("This is a demo application for Employee CRUD operations")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Edgar Perez")
                                .email("edgar.perezmeza@tcs.com")
                                .url("https://github.com/edgarfrancisco2022/employee-demo-app"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0"))

                );
    }
}

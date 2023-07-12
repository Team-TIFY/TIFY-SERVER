package tify.server.api.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import java.util.List;
import javax.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    //
    @Bean
    public OpenAPI openAPI(ServletContext servletContext) {
        String contextPath = servletContext.getContextPath();
        io.swagger.v3.oas.models.servers.Server server =
                new io.swagger.v3.oas.models.servers.Server().url(contextPath);
        return new OpenAPI().servers(List.of(server)).components(authSetting()).info(swaggerInfo());
    }

    private io.swagger.v3.oas.models.info.Info swaggerInfo() {
        License license = new License();
        license.setUrl("https://github.com/Team-TIFY/TIFY-SERVER");
        license.setName("TIFY");

        return new io.swagger.v3.oas.models.info.Info()
                .version("v0.0.1")
                .title("\"TIFY 서버 API문서\"")
                .description("TIFY 서버의 API 문서 입니다.")
                .license(license);
    }

    private Components authSetting() {
        return new Components()
                .addSecuritySchemes(
                        "access-token",
                        new SecurityScheme()
                                .type(Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(In.HEADER)
                                .name("Authorization"));
    }
}

package de.jkrech.mediamanager.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.time.LocalDate
import java.util.*

@EnableSwagger2
@PropertySource("classpath:swagger.properties")
@Configuration
internal class SwaggerConfiguration {
  fun apiInfo(): ApiInfo {
    return ApiInfoBuilder()
      .title("Media Manager")
      .description("Media Manager Backend")
      .license("Copyright: me")
      .termsOfServiceUrl("")
      .version("1.0.0")
      .contact(Contact("", "", "admin@example.de"))
      .build()
  }

  @Bean
  fun api(): Docket {
    return Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage("de.jkrech.mediamanager.ports.http"))
      .build()
      .directModelSubstitute(LocalDate::class.java, Date::class.java)
      .apiInfo(apiInfo())
  }
}
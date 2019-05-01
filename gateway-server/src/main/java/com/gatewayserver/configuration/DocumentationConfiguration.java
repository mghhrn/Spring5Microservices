package com.gatewayserver.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Used to configure the different Swagger documentation of the existing microservices
 */
@EnableSwagger2
@Configuration
@Primary
public class DocumentationConfiguration implements SwaggerResourcesProvider {

    @Value("${springfox.documentation.swagger.v2.path}")
    private String documentationPath;

    @Autowired
    private RouteLocator routeLocator;


    @Override
    public List<SwaggerResource> get() {
        return Optional.ofNullable(routeLocator)
                       .map(rl -> routeLocator.getRoutes().stream()
                                                          .map(r -> swaggerResource(r.getId(),
                                                                  r.getFullPath().replace("/**", documentationPath),"1.0"))
                                                          .collect(Collectors.toList())
                        )
                       .orElse(new ArrayList<>());
    }


    /**
     * Carry out the returned {@link SwaggerResource} with the given information.
     *
     * @param name
     *    Identifier of the resource
     * @param url
     *    Location of the resource
     * @param version
     *    Current version of the resource
     *
     * @return {@link SwaggerResource}
     */
    private SwaggerResource swaggerResource(String name, String url, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setUrl(url);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}

package com.centralvet.core.config;

import com.centralvet.core.controllers.ClinicController;
import com.centralvet.core.controllers.CustomerController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(ClinicController.class);
        register(CustomerController.class);
    }
}
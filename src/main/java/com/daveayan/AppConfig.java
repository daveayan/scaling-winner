package com.daveayan;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
    AppConfig() {
        System.out.println ("\n\n\n\n\n\n\n AppConfig AppConfig \n\n\n\n\n\n");
    }
}

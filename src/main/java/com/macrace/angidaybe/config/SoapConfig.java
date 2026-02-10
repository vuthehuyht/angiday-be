package com.macrace.angidaybe.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class SoapConfig {
    @Value("${soap.endpoint}")
    private String endpoint;

    @Value("${soap.user}")
    private String user;

    @Value("${soap.password}")
    private String password;

    @Value("${soap.cpCode}")
    private String cpCode;

    @Value("${soap.brandName}")
    private String brandName;
}

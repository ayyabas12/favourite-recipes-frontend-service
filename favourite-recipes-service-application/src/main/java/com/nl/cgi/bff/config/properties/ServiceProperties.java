package com.nl.cgi.bff.config.properties;

import lombok.Data;

@Data
public class ServiceProperties {
    private String baseUrl;
    private int memorySizeInMb;
    private int connectionTimeOut;
    private int readTimeOut;
    private int writeTimeOut;
    private int responseTimeOut;
}

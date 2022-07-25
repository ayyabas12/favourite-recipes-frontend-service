package com.nl.cgi.bff.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nl.cgi.bff.config.properties.PersistenceServiceProperties;
import com.nl.cgi.bff.config.properties.ServiceProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import reactor.netty.http.client.HttpClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@EnableConfigurationProperties({
        PersistenceServiceProperties.class})
public class AppConfig implements WebMvcConfigurer {


    @Bean("persistenceWebClient")
     @RefreshScope
    public WebClient getpersistenceWebClient(WebClient.Builder webClientBuilder, PersistenceServiceProperties serviceProperties) {
        return getWebClient(webClientBuilder, serviceProperties);
    }


    private WebClient getWebClient(WebClient.Builder webClientBuilder, ServiceProperties serviceProperties) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, serviceProperties.getConnectionTimeOut())
                .responseTimeout(Duration.ofMillis(serviceProperties.getResponseTimeOut()))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(serviceProperties.getReadTimeOut(), TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(serviceProperties.getWriteTimeOut(), TimeUnit.MILLISECONDS)));

        return webClientBuilder
                .baseUrl(serviceProperties.getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(serviceProperties.getMemorySizeInMb() * 1024 * 1024))
                        .build())
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}

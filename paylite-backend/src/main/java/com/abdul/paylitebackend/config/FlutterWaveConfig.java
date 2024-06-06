package com.abdul.paylitebackend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class FlutterWaveConfig {

    @Value("${flutterWave.api.key}")
    private String apiKey;

    @Value("${flutterWave.api.url}")
    private String apiUrl;
}

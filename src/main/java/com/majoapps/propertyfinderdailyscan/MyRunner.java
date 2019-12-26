package com.majoapps.propertyfinderdailyscan;

import com.majoapps.propertyfinderdailyscan.scheduled.DailyPropertyScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class MyRunner implements CommandLineRunner {
    
    @Autowired
    DailyPropertyScan dailyPropertyScan;

    @Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
    }

    @Override
    public void run(String... args) {
        try {
            dailyPropertyScan.getListingsNSW();
        } catch (Exception e) {
            log.error("get Listing exception {} ", e);
        }
    }
}
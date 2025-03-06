package com.esprit.ms.pidevbackend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class cloudianry {
    private String cloudName = "ddwg3ybng";
    private String apiKey = "852842743863565";
    private String apiSecret = "jHbvZgsFP6HvSsJPdnywW1nmSVg";

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                   "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

}

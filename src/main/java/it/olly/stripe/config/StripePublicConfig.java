package it.olly.stripe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripePublicConfig {
    
    @Value("${stripe.publishable.key}")
    private String publishableKey;
    
    @Value("${stripe.price.basic}")
    private String basicPriceId;
    
    @Value("${stripe.price.premium}")
    private String premiumPriceId;

    public String getPublishableKey() {
        return publishableKey;
    }

    public String getBasicPriceId() {
        return basicPriceId;
    }

    public String getPremiumPriceId() {
        return premiumPriceId;
    }
}
package it.olly.stripe.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.olly.stripe.config.StripePublicConfig;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    private StripePublicConfig stripePublicConfig;

    @GetMapping("/stripe")
    public Map<String, Object> getStripeConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("publishableKey", stripePublicConfig.getPublishableKey());
        config.put("prices", Map.of("basic", stripePublicConfig.getBasicPriceId(), "premium",
                                    stripePublicConfig.getPremiumPriceId()));
        return config;
    }
}
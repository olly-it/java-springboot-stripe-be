package it.olly.stripe.controller;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;

import it.olly.stripe.service.PaymentService;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createSubscription(@RequestParam String email,
            @RequestParam String priceId, @RequestParam String paymentMethodId) {
        try {
            Map<String, Object> result = paymentService.createSubscriptionWithPaymentMethod(email, priceId,
                                                                                            paymentMethodId);
            return ResponseEntity.ok(result);
        } catch (StripeException e) {
            logger.error("Error creating subscription", e);
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
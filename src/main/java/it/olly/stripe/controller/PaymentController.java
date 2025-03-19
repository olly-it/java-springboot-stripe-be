package it.olly.stripe.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import it.olly.stripe.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestParam Long amount) {
        logger.info("Received payment intent request for amount: {}", amount);
        try {
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(amount, "usd");
            logger.info("Successfully created PaymentIntent with id: {}", paymentIntent.getId());
            return ResponseEntity.ok(paymentIntent.getClientSecret());
        } catch (StripeException e) {
            logger.error("Failed to create PaymentIntent for amount: {}. Error: {}", amount, e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
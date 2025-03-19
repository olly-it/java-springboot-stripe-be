package it.olly.stripe.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.SubscriptionCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    @PostMapping("/create")
    public ResponseEntity<String> createSubscription(@RequestParam String email, @RequestParam String priceId) {
        try {
            // Create customer
            CustomerCreateParams customerParams = CustomerCreateParams.builder()
                .setEmail(email)
                .build();
            Customer customer = Customer.create(customerParams);
            logger.info("Created customer: {}", customer.getId());

            // Create subscription
            SubscriptionCreateParams subParams = SubscriptionCreateParams.builder()
                .setCustomer(customer.getId())
                .addItem(SubscriptionCreateParams.Item.builder().setPrice(priceId).build())
                .build();
            Subscription subscription = Subscription.create(subParams);
            logger.info("Created subscription: {}", subscription.getId());

            return ResponseEntity.ok(subscription.getId());
        } catch (StripeException e) {
            logger.error("Error creating subscription: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
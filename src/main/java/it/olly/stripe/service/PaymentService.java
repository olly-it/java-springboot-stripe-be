package it.olly.stripe.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Subscription;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.SubscriptionCreateParams;

@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    public PaymentIntent createPaymentIntent(Long amount, String currency) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                .setAutomaticPaymentMethods(PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                        .setEnabled(true)
                        .build())
                .build();

        return PaymentIntent.create(params);
    }

    public Map<String, Object> createSubscriptionWithPaymentMethod(String email, String priceId, String paymentMethodId)
            throws StripeException {

        // Create or retrieve customer
        CustomerCreateParams customerParams = CustomerCreateParams.builder()
                .setEmail(email)
                .setPaymentMethod(paymentMethodId)
                .setInvoiceSettings(CustomerCreateParams.InvoiceSettings.builder()
                        .setDefaultPaymentMethod(paymentMethodId)
                        .build())
                .build();

        Customer customer = Customer.create(customerParams);
        logger.info("Created/Retrieved customer: {}", customer.getId());

        // Create subscription
        // Update subscription creation parameters
        SubscriptionCreateParams subParams = SubscriptionCreateParams.builder()
                .setCustomer(customer.getId())
                .addItem(SubscriptionCreateParams.Item.builder()
                        .setPrice(priceId)
                        .build())
                .setPaymentBehavior(SubscriptionCreateParams.PaymentBehavior.ERROR_IF_INCOMPLETE) // Changed from
                                                                                                  // DEFAULT_INCOMPLETE
                .setPaymentSettings(SubscriptionCreateParams.PaymentSettings.builder()
                        .setPaymentMethodTypes(Arrays
                                .asList(SubscriptionCreateParams.PaymentSettings.PaymentMethodType.CARD))
                        .setSaveDefaultPaymentMethod(SubscriptionCreateParams.PaymentSettings.SaveDefaultPaymentMethod.ON_SUBSCRIPTION)
                        .build())
                .setOffSession(true) // Added to process payment immediately
                .build();

        Subscription subscription = Subscription.create(subParams);
        logger.info("Created subscription: {}", subscription.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("subscriptionId", subscription.getId());

        // Check if additional authentication is needed
        if (subscription.getLatestInvoiceObject() != null) {
            PaymentIntent paymentIntent = subscription.getLatestInvoiceObject()
                    .getPaymentIntentObject();
            if (paymentIntent != null && "requires_action".equals(paymentIntent.getStatus())) {
                response.put("requiresAction", true);
                response.put("clientSecret", paymentIntent.getClientSecret());
            }
        }

        return response;
    }
}
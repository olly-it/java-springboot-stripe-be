# Java Springboot MVC stripe BE testing app (with a lightweight FE)

## Config
stripe.api.key=your_stripe_secret_key_here (from stripe's home, "for developers")
stripe.webhook.secret=your_webhook_signing_secret_here
stripe.publishable.key=your_stripe_publishable_key_here (from stripe's home, "for developers")
stripe.price.basic=your_product_price_id_1
stripe.price.premium=your_product_price_id_2

## Webhooks
To use webhooks:

- Go to Stripe Dashboard → Developers → Webhooks
- Add an endpoint (your server URL + /api/webhook/stripe)
- Get the signing secret and add it to your properties file

## subscriptions
Create products and prices in your Stripe dashboard:

- Go to Stripe Dashboard → Products
- Create products with recurring prices
- Update the price IDs in your properties file

## Testing
[https://docs.stripe.com/testing](https://docs.stripe.com/testing)

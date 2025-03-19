# Java Springboot MVC stripe BE testing app (with a lightweight FE)

## Config
- BE: put "your_stripe_secret_key_here" in application.properties
- FE: put "your_publishable_key_here" in index.html

## Webhooks
To use webhooks:

- Go to Stripe Dashboard → Developers → Webhooks
- Add an endpoint (your server URL + /api/webhook/stripe)
- Get the signing secret and add it to your properties file
- BE: put "your_webhook_signing_secret_here" in application.properties

## subscriptions
Create products and prices in your Stripe dashboard:

- Go to Stripe Dashboard → Products
- Create products with recurring prices
- Update the price IDs in the HTML select options
- FE: put "stripe.product1.id" in index.html
- FE: put "stripe.product2.id" in index.html

## Testing
[https://docs.stripe.com/testing](https://docs.stripe.com/testing)

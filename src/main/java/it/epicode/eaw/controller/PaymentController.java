package it.epicode.eaw.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@RestController
@CrossOrigin(origins = "*", maxAge = 6000000)

public class PaymentController {
	private static Gson gson = new Gson();
	static class CreatePayment {
		@SerializedName("items")
		Object[] items;

		public Object[] getItems() {
			return items;
		}
	}

	static class CreatePaymentResponse {
		private String clientSecret;

		public CreatePaymentResponse(String clientSecret) {
			this.clientSecret = clientSecret;
		}
	}

	static int calculateOrderAmount(Object[] items) {
		// Replace this constant with a calculation of the order's amount
		// Calculate the order total on the server to prevent
		// people from directly manipulating the amount on the client
		return 1400;
	}

	@Value("${STRIPE_SECRET_KEY}")
	private String secretKey;

	@PostMapping("/create-payment-intent")
//	@RequestBody CreatePayment CreatePayment
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String createPaymentIntent() throws StripeException {
		
		Stripe.apiKey = secretKey;
//		 CreatePayment postBody = gson.fromJson(CreatePayment);
		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder().setAmount(10 * 100L).setCurrency("eur")
				.setAutomaticPaymentMethods(
						PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build())
				.build();

		// Create a PaymentIntent with the order amount and currency
		PaymentIntent paymentIntent = PaymentIntent.create(params);

		      CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret());
//		return new CreatePaymentResponse(paymentIntent.getClientSecret());
//		      Gson gson = new Gson();
		      return gson.toJson(paymentResponse);

	}
	@ExceptionHandler
	public String handleError(StripeException e) {
		return e.getMessage();
	}
	

}

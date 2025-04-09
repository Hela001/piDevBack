package com.esprit.ms.pidevbackend.Controllers;
import com.esprit.ms.pidevbackend.Services.IFactureService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.esprit.ms.pidevbackend.Entities.Paiement;
import com.esprit.ms.pidevbackend.Services.IPaiemetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Api/paiement")

public class PaiementController {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${frontend.url}")
    private String frontendUrl;

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestBody Map<String, Object> request) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        Long factureId = Long.parseLong(request.get("factureId").toString());
        // Convertir explicitement en Long
        Long amount = Long.parseLong(request.get("amount").toString());
        String currency = (String) request.get("currency");

        SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(request.get("successUrl").toString())
                .setCancelUrl(request.get("cancelUrl").toString())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(currency)
                                                .setUnitAmount(amount) // Maintenant c'est un Long
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Facture #" + factureId)
                                                                .build())
                                                .build())
                                .build());

        Session session = Session.create(paramsBuilder.build());

        Map<String, String> response = new HashMap<>();
        response.put("id", session.getId());
        return response;
    }

    @PostMapping("/create-payment-intent")
    public Map<String, String> createPaymentIntent(@RequestBody Map<String, Object> request) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        com.stripe.model.PaymentIntent intent = com.stripe.model.PaymentIntent.create(
                new com.stripe.param.PaymentIntentCreateParams.Builder()
                        .setAmount(Long.parseLong(request.get("amount").toString()))
                        .setCurrency(request.get("currency").toString())
                        .build());

        Map<String, String> response = new HashMap<>();
        response.put("clientSecret", intent.getClientSecret());
        return response;
    }
}

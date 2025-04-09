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

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestBody Map<String, Object> request) throws StripeException {
        // Configurez votre clé secrète Stripe
        Stripe.apiKey = "sk_test_51Qy2JjKpCQHkABgKoV8ZvThcDiiD6s0baLb0lpiF8pOLMqnKK9GfNYdZa3VnecfP0dfPU8gsVOmhTTzUT8Rwv2zU00BJvOpEW1";

        Long factureId = Long.parseLong(request.get("factureId").toString());
        Long amount = Long.parseLong(request.get("amount").toString());
        String currency = (String) request.get("currency");
        String successUrl = (String) request.get("successUrl");
        String cancelUrl = (String) request.get("cancelUrl");

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(currency)
                                                .setUnitAmount(amount)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Facture #" + factureId)
                                                                .build())
                                                .build())
                                .build())
                .build();

        Session session = Session.create(params);

        Map<String, String> response = new HashMap<>();
        response.put("id", session.getId());
        return response;
    }
}
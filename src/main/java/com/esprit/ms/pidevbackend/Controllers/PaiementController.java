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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Api/paiement")
public class PaiementController {
    private static final Logger logger = LoggerFactory.getLogger(PaiementController.class);
    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(
            @RequestBody Map<String, Object> request) throws StripeException {
        if (!request.containsKey("factureId") || !request.containsKey("amount") ||
                !request.containsKey("currency") || !request.containsKey("successUrl") ||
                !request.containsKey("cancelUrl")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing required parameters."));
        }
        logger.info("✅ Reached createCheckoutSession endpoint.");
        try {
            Long factureId = Long.parseLong(request.get("factureId").toString());
            Long amount = Long.parseLong(request.get("amount").toString());

            String currency = (String) request.get("currency");
            String successUrl = (String) request.get("successUrl");
            String cancelUrl = (String) request.get("cancelUrl");

            System.out.println("Creating Checkout Session with: ");
            System.out.println("FactureId: " + factureId);
            System.out.println("Amount: " + amount);
            System.out.println("Currency: " + currency);
            System.out.println("Success URL: " + successUrl);
            System.out.println("Cancel URL: " + cancelUrl);

            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD) // 👈 Ajout de cette ligne
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(successUrl)
                    .setCancelUrl(cancelUrl)
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(currency)
                                                    .setUnitAmount(amount) // en centimes
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Paiement Facture ID: " + factureId)
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();



            Session session = Session.create(params);

            Map<String, String> response = new HashMap<>();
            response.put("id", session.getId());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
package com.esprit.ms.pidevbackend.Controllers;
import com.esprit.ms.pidevbackend.Entities.FactureStatus;
import com.esprit.ms.pidevbackend.Entities.methodePaiement;
import com.esprit.ms.pidevbackend.Repositories.PaiementRepo;
import com.esprit.ms.pidevbackend.Services.IFactureService;
import com.esprit.ms.pidevbackend.Services.PaiemetService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Api/paiement")
public class PaiementController {
    private static final Logger logger = LoggerFactory.getLogger(PaiementController.class);
    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @Autowired
    private PaiemetService paiementService;

    @Autowired
    private IFactureService factureService;
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
                    .putMetadata("factureId", factureId.toString())
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
//    @PostMapping("/webhook")
//    public ResponseEntity<String> handleWebhook(
//            @RequestBody String payload,
//            @RequestHeader("Stripe-Signature") String sigHeader) {
//
//        try {
//            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
//
//            if ("checkout.session.completed".equals(event.getType())) {
//                Session session = (Session) event.getDataObjectDeserializer().getObject().get();
//                handleCompletedSession(session); // Ajoutez cette ligne
//                logger.info("Paiement réussi : {}", session.getId());
//            }
//
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            logger.error("Erreur lors du traitement du webhook", e);
//            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
//        }
//    }
//
//    private void handleCompletedSession(Session session) {
//        try {
//            logger.info("💰 Processing completed session: {}", session.getId());
//
//            // Vérifiez que le paiement n'a pas déjà été enregistré
//            if (paiementService.existsBySessionId(session.getId())) {
//                logger.warn("⚠️ Paiement déjà enregistré pour la session: {}", session.getId());
//                return;
//            }
//
//            // Créer et sauvegarder le paiement
//            Paiement paiement = new Paiement();
//            paiement.setMontant(session.getAmountTotal() / 100f);
//            paiement.setDatePaiement(new Date());
//            paiement.setPayment(methodePaiement.CARTE);
//            paiement.setSessionId(session.getId()); // Stockez l'ID de session Stripe
//
//            // Associer à la facture si disponible dans les metadata
//            String factureIdStr = session.getMetadata().get("factureId");
//            if (factureIdStr != null) {
//                try {
//                    Long factureId = Long.parseLong(factureIdStr);
//                    paiement = paiementService.addPaiement(paiement, factureId);
//                    factureService.updateFactureStatus(factureId, FactureStatus.Paid);
//                    logger.info("✅ Paiement enregistré pour la facture ID: {}", factureId);
//                } catch (Exception e) {
//                    logger.error("❌ Erreur lors de l'association à la facture", e);
//                }
//            } else {
//                paiementService.modifyPaiement(paiement);
//                logger.warn("⚠️ Aucun ID de facture trouvé dans les metadata");
//            }
//        } catch (Exception e) {
//            logger.error("❌ Erreur critique lors du traitement de la session", e);
//        }
//    }
@PostMapping("/webhook")
public ResponseEntity<String> handleWebhook(
        @RequestBody String payload,
        @RequestHeader("Stripe-Signature") String sigHeader) {
    logger.info("🔔 Webhook reçu de Stripe");

    try {
        Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        logger.info("Type d'événement: {}", event.getType());

        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().get();
            handleCompletedSession(session);
        }

        return ResponseEntity.ok().build();
    } catch (Exception e) {
        logger.error("Erreur webhook: {}", e.getMessage());
        return ResponseEntity.status(400).body(e.getMessage());
    }
}

    private void handleCompletedSession(Session session) {
        try {
            logger.info("Traitement du paiement pour la session: {}", session.getId());

            // Vérifier si le paiement existe déjà
            if (paiementService.existsBySessionId(session.getId())) {
                logger.warn("Paiement déjà traité pour la session: {}", session.getId());
                return;
            }

            // Créer le nouveau paiement
            Paiement paiement = new Paiement();
            paiement.setMontant(session.getAmountTotal() / 100f);
            paiement.setDatePaiement(new Date());
            paiement.setPayment(methodePaiement.CARTE);
            paiement.setSessionId(session.getId());

            // Récupérer l'ID de la facture depuis les métadonnées
            String factureIdStr = session.getMetadata().get("factureId");
            if (factureIdStr != null) {
                Long factureId = Long.parseLong(factureIdStr);
                logger.info("Association du paiement à la facture ID: {}", factureId);

                // Sauvegarder le paiement et mettre à jour la facture
                paiement = paiementService.addPaiement(paiement, factureId);
                factureService.updateFactureStatus(factureId, FactureStatus.Paid);

                logger.info("✅ Paiement enregistré avec succès pour la facture: {}", factureId);
            }
        } catch (Exception e) {
            logger.error("Erreur lors du traitement du paiement: {}", e.getMessage());
            throw e;
        }
    }

}
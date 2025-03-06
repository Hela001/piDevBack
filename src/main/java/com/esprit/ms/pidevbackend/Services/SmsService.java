package com.esprit.ms.pidevbackend.Services;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

    @Value("${twilio.account-sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth-token}")
    private String AUTH_TOKEN;

    @Value("${twilio.phone-number}")
    private String FROM_PHONE_NUMBER;


    @PostConstruct
    public void initTwilio() {
        System.out.println("Twilio ACCOUNT_SID: " + ACCOUNT_SID);
        System.out.println("Twilio AUTH_TOKEN: " + AUTH_TOKEN);
        System.out.println("Twilio FROM_PHONE_NUMBER: " + FROM_PHONE_NUMBER);
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }


    public void sendSms(String toPhoneNumber, String messageBody) {
        try {
            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(FROM_PHONE_NUMBER),
                    messageBody
            ).create();
            System.out.println("Message envoyé avec SID: " + message.getSid());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'envoi du SMS: " + e.getMessage());
        }
    }
}

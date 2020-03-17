package com.intellisense.coronavirustracker.service.serviceImpl;

import com.intellisense.coronavirustracker.configuration.TwilioConfiguration;
import com.intellisense.coronavirustracker.model.SmsReport;
import com.intellisense.coronavirustracker.service.TwiloSmsSender;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwiloSmsSenderImplementation implements TwiloSmsSender {


    private static final Logger LOGGER = LoggerFactory.getLogger(TwiloSmsSenderImplementation.class);
    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwiloSmsSenderImplementation(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public void sendSms(SmsReport smsRequest) {
        if (isPhoneNumberValid(smsRequest.getPhoneNumber())){
            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());

            String address = smsRequest.getAddress();
            String message = smsRequest.getMessage() + "\n Address: " + address;
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            LOGGER.info("Send sms {}"+smsRequest);
        }else {
            throw new IllegalArgumentException(
                    "Phone Number ["+smsRequest.getPhoneNumber()+"] is not valid");
        }

    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // implement phone number validation
        return true;
    }
}

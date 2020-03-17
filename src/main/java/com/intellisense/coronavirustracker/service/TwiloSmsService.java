package com.intellisense.coronavirustracker.service;

import com.intellisense.coronavirustracker.model.SmsReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TwiloSmsService {

    private final TwiloSmsSender twiloSmsSender;

    @Autowired
    public TwiloSmsService(@Qualifier("twilio") TwiloSmsSender twiloSmsSender) {
        this.twiloSmsSender = twiloSmsSender;
    }

    public void sendSms(SmsReport smsRequest){
        twiloSmsSender.sendSms(smsRequest);
    }
}

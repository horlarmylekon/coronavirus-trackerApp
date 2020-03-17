package com.intellisense.coronavirustracker.service;

import com.intellisense.coronavirustracker.model.SmsReport;

public interface TwiloSmsSender {
    void sendSms(SmsReport smsRequest);
}

package com.intellisense.coronavirustracker.controller;


import com.intellisense.coronavirustracker.model.SmsReport;
import com.intellisense.coronavirustracker.service.TwiloSmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/report")
public class TwilioSmsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsController.class);
    private final TwiloSmsService twiloSmsService;

    @Autowired
    public TwilioSmsController(TwiloSmsService twiloSmsService) {
        this.twiloSmsService = twiloSmsService;
    }

    @GetMapping("/")
    public String showReportPage(){
        return "report";
    }

    @PostMapping("/sms")
    public String sendSms(@ModelAttribute("smsReport") SmsReport smsRequest, Model model) {

        if (smsRequest != null){
            LOGGER.info("Initiallizing Twilio SMS ...");
            LOGGER.info("User Phone Number: {}", smsRequest.getPhoneNumber());
            twiloSmsService.sendSms(smsRequest);

        }else {
            LOGGER.info("Invalid SMS Request");
        }
        return "redirect:";
    }
}

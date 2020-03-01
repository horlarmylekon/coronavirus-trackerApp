package com.intellisense.coronavirustracker.service;


import org.springframework.stereotype.Service;
import sun.net.www.http.HttpClient;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class CoronaVirusDataService {
    /*
    This service gives the data of coronavirus cases
     */

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/archived_data/archived_time_series/time_series_2019-ncov-Confirmed.csv";

    // make an http call to the url
    @PostConstruct
    public void fetchVirusData() throws IOException {

        URL url = new URL(VIRUS_DATA_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        //read response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null){
            content.append(inputLine);
        }
        in.close();

        connection.disconnect();

        System.out.println(content);
    }
}

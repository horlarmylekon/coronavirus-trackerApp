package com.intellisense.coronavirustracker.service;


import com.intellisense.coronavirustracker.model.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sun.net.www.http.HttpClient;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {
    /*
    This service gives the data of coronavirus cases
     */

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/archived_data/archived_time_series/time_series_2019-ncov-Confirmed.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    // make an http call to the url
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException {

        List<LocationStats> newStats = new ArrayList<>();

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
        //in.close();

        //connection.disconnect();

        System.out.println(content);
//
//        StringReader reader = new StringReader(content.toString());
//        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
//        for (CSVRecord record : records) {
//            LocationStats locationStats = new LocationStats();
//            locationStats.setState(record.get("Province/State"));
//            locationStats.setCountry(record.get("Country/Region"));
//            locationStats.setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)));
//
//            System.out.println(locationStats);
//            newStats.add(locationStats);
//        }
//        this.allStats = newStats;
    }

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    public void setAllStats(List<LocationStats> allStats) {
        this.allStats = allStats;
    }
}

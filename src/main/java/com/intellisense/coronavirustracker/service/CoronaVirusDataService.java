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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {
    /*
    This service gives the data of coronavirus cases
     */
    private static String VIRUS_DATA_URL_1 = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/archived_data/archived_time_series/time_series_2019-ncov-Confirmed.csv";

    private List<LocationStats> allStats = new ArrayList<>();
    private static HttpURLConnection connection;

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    public void setAllStats(List<LocationStats> allStats) {
        this.allStats = allStats;
    }

    // make an http call to the url
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusConfirmedData() throws IOException {

        List<LocationStats> newStats = new ArrayList<>();

        try {
            URL url = new URL(VIRUS_DATA_URL_1);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //read response
            StringBuilder content;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                content = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            //System.out.println(content.toString());

            StringReader reader = new StringReader(content.toString());

            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : records) {
                LocationStats locationStats = new LocationStats();
                locationStats.setState(record.get("Province/State"));
                locationStats.setCountry(record.get("Country/Region"));

                int latestCases = Integer.parseInt(record.get(record.size() - 1));
                int prevDayCases = Integer.parseInt(record.get(record.size() - 2));

                locationStats.setLatestTotalCases(latestCases);
                locationStats.setDiffFromPrevDay(latestCases - prevDayCases);

                //System.out.println(locationStats);
                newStats.add(locationStats);
            }
            this.allStats = newStats;

        }finally {
            connection.disconnect();
        }
    }

}

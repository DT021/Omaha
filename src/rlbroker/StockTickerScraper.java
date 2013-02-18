/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rlbroker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 *
 * @author Alexandr
 */
public class StockTickerScraper {

    String name;


    public StockTickerScraper(String quote) {
        name = quote;
    }
    
    public void historicalDataUpdater(int startYear, int startDate, int startMonth, int endDate, int endYear, int endMonth) {
        try {
            
            URL yahoofin = new URL(
                    "http://ichart.finance.yahoo.com/table.csv?s=" + getName() + "&a=" +
                    (startMonth-1) + "&b=" + startDate + "&c=" + startYear +
                    "&d=" + (endMonth-1) + "&e=" + endDate + "&f=" + endYear + "&g=d.csv&ignore=.csv");
            
            URLConnection yc = yahoofin.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            int i = 0;
            in.readLine();// skipping the first line, as it's just titles
            while ((inputLine = in.readLine()) != null) {
                String[] info = inputLine.split(",");
                
                i++;
            }
            in.close();
        } catch (Exception ex) {
            System.out.println("Error in historic fetch for " + getName());
        }
    }

    public String getName() {
        return name;
    }
}

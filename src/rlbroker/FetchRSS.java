package rlbroker;

import java.net.URL;
import java.util.Iterator;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FetchRSS {
    String name;
    public FetchRSS(String name) {
        this.name = name;
    }
    
    
    
    public enum Feelings {

        POSITIVE(0), NEGATIVE(1), NEUTRAL(2);
        private int index;

        private Feelings(int index) {
            this.index = index;
        }
    };
    double[] stateSpace = new double[10 * 10 * 3];
    int indexOfState = 0;
    int indexOfBestMove = 0;
    int negativeWords = 0;
    int positveWords = 0;
    Feelings action  = Feelings.POSITIVE;

    public JLabel rssFetch(String name) throws Exception {

        URL url = new URL("http://feeds.finance.yahoo.com/rss/2.0/headline?s=" + name + "&region=US&lang=en-US");
        XmlReader reader = null;
        String begin = "<html>";
        String end = "</html>";
        String[] feedTitles = new String[50];
        try {
            reader = new XmlReader(url);
            SyndFeed feed = new SyndFeedInput().build(reader);
            int it = 0;
            for (Iterator i = feed.getEntries().iterator(); i.hasNext();) {
                SyndEntry entry = (SyndEntry) i.next();
                if (entry.getTitle().charAt(0) != '[') {
                    feedTitles[it] = entry.getTitle();
                    it++;
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        String formatted = begin;
        for (int i = 0; i < feedTitles.length; i++) {
            if (i == 0) {
                formatted += feedTitles[i];
            } else if (!(feedTitles[i] == (null))) {
                if (!feedTitles[i - 1].startsWith(feedTitles[i])) {
                    formatted += "<br>" + feedTitles[i];
                }
            }
        }
        formatted += end;
        return new JLabel(formatted);
    }

    public void updateState() {
        try {

            URL url = new URL("http://feeds.finance.yahoo.com/rss/2.0/headline?s=" + name + "&region=US&lang=en-US");
            XmlReader reader = null;
            String begin = "<html>";
            String end = "</html>";
            String[] feedTitles = new String[50];
            try {
                reader = new XmlReader(url);
                SyndFeed feed = new SyndFeedInput().build(reader);
                int it = 0;
                for (Iterator i = feed.getEntries().iterator(); i.hasNext();) {
                    SyndEntry entry = (SyndEntry) i.next();
                    if (entry.getTitle().charAt(0) != '[') {
                        feedTitles[it] = entry.getTitle();
                        it++;
                    }
                }
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
            String formatted = begin;
            for (int i = 0; i < feedTitles.length; i++) {
                if (i == 0) {
                } else if (!(feedTitles[i] == (null))) {
                    if (!feedTitles[i - 1].startsWith(feedTitles[i])) {
                        feedTitles[i] = "";
                    }
                }
            }
            for (int i = 0; i < feedTitles.length; i++) {
                if (feedTitles[i].contains("high")
                        || feedTitles[i].contains("gain")
                        || feedTitles[i].contains("highest")
                        || feedTitles[i].contains("higher")
                        || feedTitles[i].contains("greatist")
                        || feedTitles[i].contains("increasing")
                        || feedTitles[i].contains("greater")
                        || feedTitles[i].contains("gain")
                        || feedTitles[i].contains("profit")
                        || feedTitles[i].contains("up")
                        || feedTitles[i].contains("profit")
                        || feedTitles[i].contains("positive")
                        || feedTitles[i].contains("rise")
                        || feedTitles[i].contains("strong")
                        || feedTitles[i].contains("advance")) {
                    positveWords++;
                }
            }

            for (int i = 0; i < feedTitles.length; i++) {
                if (feedTitles[i].contains("low")
                        || feedTitles[i].contains("loss")
                        || feedTitles[i].contains("lowest")
                        || feedTitles[i].contains("lower")
                        || feedTitles[i].contains("drop")
                        || feedTitles[i].contains("decresing")
                        || feedTitles[i].contains("down")
                        || feedTitles[i].contains("shortcoming")
                        || feedTitles[i].contains("negative")
                        || feedTitles[i].contains("dip")
                        || feedTitles[i].contains("plunge")
                        || feedTitles[i].contains("ease")
                        || feedTitles[i].contains("easing")
                        || feedTitles[i].contains("plunging")
                        || feedTitles[i].contains("plummet")
                        || feedTitles[i].contains("tumble")
                        || feedTitles[i].contains("suffer")
                        || feedTitles[i].contains("weak")) {
                    negativeWords++;
                }
            }
            
            indexOfState = 
                    discritize((positveWords/feedTitles.length)*10) +
                    10 * discritize((negativeWords/feedTitles.length)*10);
        } catch (Exception e) {
        }
    }
    
    public int discritize (double value ){
        if (value <= 0.1) {
            return 0;
        }else if(value > 0.1 && value <= 0.2){
            return 1;
        }else if(value > 0.2 && value <= 0.3){
            return 2;
        }else if(value > 0.3 && value <= 0.4){
            return 3;
        }else if(value > 0.4 && value <= 0.5){
            return 4;
        }else if(value > 0.5 && value <= 0.6){
            return 5;
        }else if(value > 0.6 && value <= 0.7){
            return 6;
        }else if(value > 0.7 && value <= 0.8){
            return 7;
        }else if(value > 0.8 && value <= 0.9){
            return 8;
        }else{
            return 9;
        }
    }
    
    public void findIndexOfBestMove(){
        double globalMax;
        
        for (int i = 0; i < 3; i++) {
            if (stateSpace[indexOfBestMove] <= stateSpace[indexOfState + (100*i)]) {
                indexOfBestMove = indexOfState+(100*i);
                if (i == 0) {
                    action = Feelings.POSITIVE;
                }else if(i ==1){
                    action = Feelings.NEGATIVE;
                }else if(i == 2){
                    action = Feelings.NEUTRAL;
                }
            }
            
        }
    }
    
    public void determineFeeling(){
        if (positveWords > negativeWords) {
            action = Feelings.POSITIVE;
        }else if(negativeWords < positveWords){
            action = Feelings.NEGATIVE;
        }else if(negativeWords == positveWords){
            action = Feelings.NEUTRAL;
        }
    }
}
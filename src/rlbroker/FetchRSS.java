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
                if (entry.getTitle().charAt(0)!= '[') {
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
            System.out.println(feedTitles[i]);
            if (i==0) {
                formatted += feedTitles[i];
            }else if (!(feedTitles[i]==(null))){
                if (!feedTitles[i-1].startsWith(feedTitles[i])) {
                     formatted+="<br>" + feedTitles[i];
                }
            }
        }
        formatted += end;
        return new JLabel(formatted);
    }
}
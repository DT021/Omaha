package rlbroker;

import java.net.URL;
import java.util.Iterator;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.util.ArrayList;
import javax.swing.JLabel;

public class FetchRSS {

    public JLabel rssFetch(String name) throws Exception {

        URL url = new URL("http://feeds.finance.yahoo.com/rss/2.0/headline?s=" + name + "&region=US&lang=en-US");
        XmlReader reader = null;
        String articles = "<html>";
        try {
            reader = new XmlReader(url);
            SyndFeed feed = new SyndFeedInput().build(reader);
            for (Iterator i = feed.getEntries().iterator(); i.hasNext();) {
                SyndEntry entry = (SyndEntry) i.next();
                articles += "<br>" + (entry.getTitle());
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        articles += "</html>";
        return new JLabel(articles);
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rlbroker;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class RLBroker {
    /* Alex's notes;
     *  possible expansion:
     *      - do the to-do in the AGENT
     *      - make a broker with certain picks in a percentage format for
     * generalization
     *      - make the ui such that everything looks nice.
     *      - move on to food and health project
     *  
     * 
     */

    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            QuoteAgent agent = new QuoteAgent(("AAPL"), true);
            agent.trainAgent();
            System.out.print(agent.getName() + "  " + agent.getEquity());
        }
//        quotes.add(new QuoteAgent("AKAM", isGraphing));
//        quotes.add(new QuoteAgent("ALXN", isGraphing));
//        quotes.add(new QuoteAgent("ALTR", isGraphing));
//        quotes.add(new QuoteAgent("AMZN", isGraphing));
//        quotes.add(new QuoteAgent("AMGN", isGraphing));
//        quotes.add(new QuoteAgent("ADI", isGraphing));
//        quotes.add(new QuoteAgent("AAPL", isGraphing));
//        quotes.add(new QuoteAgent("AMAT", isGraphing));
//        quotes.add(new QuoteAgent("ADSK", isGraphing));

//       LoadingGUI loadingGUI = new LoadingGUI();
//       loadingGUI.setVisible(true);

//        BrokerAgent brokerAgent = new BrokerAgent();
//         brokerAgent.act();

//        brokerAgent.trainAll();
//         JFrame j = new JFrame("trainer");
//         JLabel l = new JLabel("Finished Training;");
//         j.add(l);
//         j.setVisible(true);
//        BrokerGUI brokerGUI = new BrokerGUI(null);
//        brokerGUI.setVisible(true);

    }
}

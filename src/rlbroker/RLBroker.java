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
//       LoadingGUI loadingGUI = new LoadingGUI();
//       loadingGUI.setVisible(true);
        
         BrokerAgent brokerAgent = new BrokerAgent();
         brokerAgent.act();
        
//         brokerAgent.trainAll();
//         JFrame j = new JFrame("trainer");
//         JLabel l = new JLabel("Finished Training;");
//         j.add(l);
//         j.setVisible(true);
//        BrokerGUI brokerGUI = new BrokerGUI(null);
//        brokerGUI.setVisible(true);
      
    }     
}

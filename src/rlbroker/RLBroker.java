/*
 * Author: Alexandra Kearney (Kearney@Ualberta.ca)
 * Undergraduate year one.
 * Innovative learning week smart data hack 2013
 * 
 *  This is a project made for the Innovative Learning Week Smart Data Hack where
 * a reinforcement learning agent will monitor one of ten stocks on the nasdaq.
 * Of the ten agents, five are then selected as the 'Top picks' of the day. 
 */
package rlbroker;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class RLBroker {
    /* Alex's notes;
     *  * 
     * Possible expansion points:
     *  - clean code
     *  - expand the language parsing to make it more effective
     *  - add the volume and remove the open from the statespace
     *      - the open shouldn't be an issue, the moves should be based on patterns,
     *      not on the current opening ( Although that could be a factor )
     *  - what can the project be 
     *  
     * 
     */

    public static void main(String[] args) {

        BrokerAgent brokerAgent = new BrokerAgent();
        brokerAgent.act();
        
    }
}

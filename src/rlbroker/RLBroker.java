/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rlbroker;

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
       
          for (int i = 0; i < 10; i++) {
              QuoteAgent agent = new QuoteAgent("GOOG", false);
        }
          QuoteAgent agent = new QuoteAgent("GOOG", true);
    }     
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rlbroker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Alexandr
 */
public class BrokerAgent {

   
    private int indexOfBestMove;
    private int indexOfPreviousBestMove;
    private int state;;
    private double reward;
    public enum Actions {

        BUY(0), SELL(1), HOLD(2);
        private int index;

        private Actions(int index) {
            this.index = index;
        }
    };
    private double temporalDifference;
    private static double learningRate = 0.1;
    private static double discountRate = 0.99;
    private int timesteps = 0;
    private boolean isHolding;
    Grapher rewardGrapher;
    Grapher equityGrapher; 
    Grapher openGrapher;
    private double profit;
    private double equity;
    BufferedReader bufferedReader;
    FileWriter fileWriter;
    BufferedWriter bufferedWriter;
    JFrame frame;
    JPanel panel;
    JLabel label;
    double[] stateSpace;
    boolean isGraphing = false;
    QuoteAgent GOOG = new QuoteAgent("GOOG", isGraphing)
    
    
    public BrokerAgent ( ){
        stateSpace = new double[3*3*2];// b,s,h from both actors, then a 
        
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rlbroker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private int state;
    private double reward;
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
    
    BrokerGUI gui = new BrokerGUI();
    ArrayList<QuoteAgent> quotes = new ArrayList<QuoteAgent>();
    ArrayList<QuoteAgent> top5 = new ArrayList<QuoteAgent>();
    int action;
    int startStep;

    public BrokerAgent() {
        
        quotes.add(new QuoteAgent("MMM", isGraphing));
        quotes.add(new QuoteAgent("ATB.TO", isGraphing));
        quotes.add(new QuoteAgent("ABBV", isGraphing));
        quotes.add(new QuoteAgent("ANF", isGraphing));
        quotes.add(new QuoteAgent("ACE", isGraphing));
        quotes.add(new QuoteAgent("ACT", isGraphing));
        quotes.add(new QuoteAgent("ADBE", isGraphing));
        quotes.add(new QuoteAgent("ADT", isGraphing));
        quotes.add(new QuoteAgent("AMD", isGraphing));
        quotes.add(new QuoteAgent("AES", isGraphing));
        
//        quotes.add(new QuoteAgent("AET", isGraphing));
//        quotes.add(new QuoteAgent("AFL", isGraphing));
//        quotes.add(new QuoteAgent("A", isGraphing));
//        quotes.add(new QuoteAgent("GAS", isGraphing));
//        quotes.add(new QuoteAgent("APD", isGraphing));
//        quotes.add(new QuoteAgent("ARG", isGraphing));
//        quotes.add(new QuoteAgent("AKAM", isGraphing));
//        quotes.add(new QuoteAgent("AA", isGraphing));
//        quotes.add(new QuoteAgent("ALXN", isGraphing));
//        quotes.add(new QuoteAgent("ATI", isGraphing));
//        
//        quotes.add(new QuoteAgent("AGN", isGraphing));
//        quotes.add(new QuoteAgent("ALL", isGraphing));
//        quotes.add(new QuoteAgent("ALTR", isGraphing));
//        quotes.add(new QuoteAgent("MO", isGraphing));
//        quotes.add(new QuoteAgent("AMZN", isGraphing));
//        quotes.add(new QuoteAgent("AEE", isGraphing));
//        quotes.add(new QuoteAgent("AEP", isGraphing));
//        quotes.add(new QuoteAgent("AXP", isGraphing));
//        quotes.add(new QuoteAgent("AIG", isGraphing));
//        quotes.add(new QuoteAgent("AMT", isGraphing));
//        
//        quotes.add(new QuoteAgent("AMP", isGraphing));
//        quotes.add(new QuoteAgent("ABC", isGraphing));
//        quotes.add(new QuoteAgent("AMGN", isGraphing));
//        quotes.add(new QuoteAgent("APH", isGraphing));
//        quotes.add(new QuoteAgent("APC", isGraphing));
//        quotes.add(new QuoteAgent("ADI", isGraphing));
//        quotes.add(new QuoteAgent("AON", isGraphing));
//        quotes.add(new QuoteAgent("AIV", isGraphing));
//        quotes.add(new QuoteAgent("APOL", isGraphing));
//        quotes.add(new QuoteAgent("AAPL", isGraphing));
//        
//        quotes.add(new QuoteAgent("AMAT", isGraphing));
//        quotes.add(new QuoteAgent("ADM", isGraphing));
//        quotes.add(new QuoteAgent("AIZ", isGraphing));
//        quotes.add(new QuoteAgent("T", isGraphing));
//        quotes.add(new QuoteAgent("ADSK", isGraphing));
//        quotes.add(new QuoteAgent("ADP", isGraphing));
//        quotes.add(new QuoteAgent("AN", isGraphing));
//        quotes.add(new QuoteAgent("AZO", isGraphing));
//        quotes.add(new QuoteAgent("AVB", isGraphing));
//        quotes.add(new QuoteAgent("AVY", isGraphing));
//        
//        quotes.add(new QuoteAgent("AVP", isGraphing));
//        quotes.add(new QuoteAgent("BHI", isGraphing));
//        quotes.add(new QuoteAgent("BLL", isGraphing));
//        quotes.add(new QuoteAgent("BAC", isGraphing));
//        quotes.add(new QuoteAgent("BK", isGraphing));
//        quotes.add(new QuoteAgent("BCR", isGraphing));
//        quotes.add(new QuoteAgent("BAX", isGraphing));
//        quotes.add(new QuoteAgent("BBT", isGraphing));
//        quotes.add(new QuoteAgent("BEAM", isGraphing));
//        quotes.add(new QuoteAgent("BDX", isGraphing));

        stateSpace = new double[(int)Math.pow(3, 10)*10];// b,s,h from both actors, then a 
    }
    
    public void setStartingPoint(){
        int shortestIndex = 0;
        int shortestTime = 0;
        
        for (int i = 0; i < quotes.size(); i++) {
            if (quotes.get(shortestIndex).getHighInput().length < quotes.get(i).getHighInput().length) {
                shortestIndex = i;
                shortestTime = quotes.get(i).getHighInput().length;
            }
        }
        for (int i = 0; i < quotes.size(); i++) {
            if (i != shortestIndex) {
                quotes.get(i).startStep = quotes.get(i).getHighInput().length - shortestTime;
            } 
        }
    }
    
    public void act() {
        System.out.println("");
        for (int i = 0; i < quotes.size(); i++) {
            quotes.get(i).step();
        }
        updateIndexOfBestMove();
        timesteps++;
        gui.setVisible(true);
        while (timesteps < quotes.get(0).getHighInput().length) {            
            
            for (int i = 0; i < quotes.size(); i++) {
            quotes.get(i).step();
            }
            gui.updateValues(top5, equity);
            updateTD();
            updateStateVariables();
            writeStateSpace();
            updateState();
            updateIndexOfBestMove();
            timesteps ++;
        }
    }
    
    public void updateState() {
        int index = 0;
        
        for (int i = 0; i < quotes.size(); i++) {
            index += Math.pow(3, i) * quotes.get(i).getActionNumber();
        }
        state = index;
    }
    
    public void updateIndexOfBestMove() {
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i = 0; i < top5.size(); i++) {
            top5.remove(i);
            
        }
            for (int j = 0; j < 10; j++) {
                if (stateSpace[state+(int)(Math.pow(3, 10)*j)]>= stateSpace[i1]) {
                    indexOfBestMove = state+(int)(Math.pow(3, 10)*j);
                    i5 = i4;
                    i4 = i3;
                    i3 = i2;
                    i2 = i1;
                    i1 = j;
                }
                if (stateSpace[state+(int)(Math.pow(3, 10)*j)]>= stateSpace[i2]) {
                    i5 = i4;
                    i4 = i3;
                    i3 = i2;
                    i2 = j;
                }
                if (stateSpace[state+(int)(Math.pow(3, 10)*j)]>= stateSpace[i3]) {
                    i5 = i4;
                    i4 = i3;
                    i3 = j;
                }
                if (stateSpace[state+(int)(Math.pow(3, 10)*j)]>= stateSpace[i4]) {
                    i5 = i4;
                    i4 = j;
                }
                if (stateSpace[state+(int)(Math.pow(3, 10)*j)]>= stateSpace[i5]) {
                    i5 = j;
                }
               top5.add(quotes.get(i1));
               top5.add(quotes.get(i2));
               top5.add(quotes.get(i3));
               top5.add(quotes.get(i4));
               top5.add(quotes.get(i5));
            }
    }
    
    public void updateTD() {
        determineReward();
        
        setTemporalDifference(
                learningRate
                * (getReward()
                + discountRate
                * stateSpace[getIndexOfBestMove()] - stateSpace[getIndexOfPreviousBestMove()]));
        
        stateSpace[getIndexOfPreviousBestMove()] += learningRate * getTemporalDifference();        
        
    }
    
    public void determineReward() {
        reward = top5.get(0).getReward();
    }
    
    public void updateStateVariables() {
        
        indexOfPreviousBestMove = indexOfBestMove;
    }
    
    public void writeStateSpace() {
        try {
            fileWriter = new FileWriter("StatespaceBroker.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            
            for (int i = 0; i < stateSpace.length; i++) {
                if (i == stateSpace.length - 1) {
                    bufferedWriter.write(stateSpace[i] + "");
                } else {
                    bufferedWriter.write(stateSpace[i] + "");
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error: In creation of the statespace");
        }
    }

    /**
     * @return the indexOfBestMove
     */
    public int getIndexOfBestMove() {
        return indexOfBestMove;
    }

    /**
     * @return the indexOfPreviousBestMove
     */
    public int getIndexOfPreviousBestMove() {
        return indexOfPreviousBestMove;
    }

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * @return the reward
     */
    public double getReward() {
        return reward;
    }

    /**
     * @return the temporalDifference
     */
    public double getTemporalDifference() {
        return temporalDifference;
    }

    /**
     * @return the timesteps
     */
    public int getTimesteps() {
        return timesteps;
    }

    /**
     * @param timesteps the timesteps to set
     */
    public void setTimesteps(int timesteps) {
        this.timesteps = timesteps;
    }

    /**
     * @return the profit
     */
    public double getProfit() {
        return profit;
    }

    /**
     * @return the equity
     */
    public double getEquity() {
        return equity;
    }

    /**
     * @param temporalDifference the temporalDifference to set
     */
    public void setTemporalDifference(double temporalDifference) {
        this.temporalDifference = temporalDifference;
    }
}

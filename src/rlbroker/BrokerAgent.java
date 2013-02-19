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
    QuoteAgent goog = new QuoteAgent("GOOG", true);
    QuoteAgent aapl = new QuoteAgent("AAPL", true);
    int action;
    int startStep;
    public BrokerAgent ( ){
        stateSpace = new double[3*3*4];// b,s,h from both actors, then a 
        if (goog.stateSpace.length < aapl.stateSpace.length) {
            aapl.startStep = (aapl.stateSpace.length)-(goog.stateSpace.length);
        }else{
            goog.startStep = goog.stateSpace.length - goog.stateSpace.length;
        }
    }
    
    public void act (){
        System.out.println("");
        goog.step();
        aapl.step();
        timesteps ++;
        while (timesteps <= goog.stateSpace.length) {            
            goog.step();
            aapl.step();
        }
        updateTD();
        System.out.println(getEquity());
        updateStateVariables();
        writeStateSpace();
        updateState();
        updateIndexOfBestMove();
    }
    
    public void updateState (){
       int googIndex = goog.getActionNumber();
       int applIndex = aapl.getActionNumber();
       state = googIndex + 3*applIndex;
    }
    
    public void updateIndexOfBestMove (){
        if (getState()+(3*3)*0 > getState()+ (3*3)*1) {
            action = 0;// buy google
            indexOfBestMove = getState()+(3*3*0);
        }else{
            action = 1;// buy appl
            indexOfBestMove = getState()+(3*3*1);
        }
    }
    
    public void updateTD(){
        determineReward();
        
        setTemporalDifference(
                learningRate * 
                (getReward() +
                discountRate *
                stateSpace[getIndexOfBestMove()] - stateSpace[getIndexOfPreviousBestMove()]));
        
        stateSpace[getIndexOfPreviousBestMove()] += learningRate * getTemporalDifference(); 
        
    }
    
    public void determineReward(){
        if (action == 0) {
            reward = goog.getReward();
            equity += goog.getOpenInput()[timesteps+1]-goog.getOpenInput()[timesteps];
            
        }else{
            reward = aapl.getReward();
            equity += aapl.getOpenInput()[timesteps+1]-aapl.getOpenInput()[timesteps];
        }
    }
    
    public void updateStateVariables(){
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

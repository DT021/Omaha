/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rlbroker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
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
    
    ArrayList<QuoteAgent> quotes = new ArrayList<QuoteAgent>();
    ArrayList<QuoteAgent> top5 = new ArrayList<QuoteAgent>();
    BrokerGUI gui;
    int action;
    int startStep;

    public BrokerAgent() {
        
        quotes.add(new QuoteAgent("ATVI", isGraphing));
        quotes.add(new QuoteAgent("AKAM", isGraphing));
        quotes.add(new QuoteAgent("ALXN", isGraphing));
        quotes.add(new QuoteAgent("ALTR", isGraphing));
        quotes.add(new QuoteAgent("AMZN", isGraphing));
        quotes.add(new QuoteAgent("AMGN", isGraphing));
        quotes.add(new QuoteAgent("ADI", isGraphing));
        quotes.add(new QuoteAgent("AAPL", isGraphing));
        quotes.add(new QuoteAgent("AMAT", isGraphing));
        quotes.add(new QuoteAgent("ADSK", isGraphing));

        stateSpace = new double[(int) Math.pow(3, 10) * 10];// b,s,h from both actors, then a 
        gui = new BrokerGUI(quotes);
        gui.setIndexPanel();
    }

    public void readSpace() {
        /* Created By: Alex Kearney 
         * Reads statespace from previous execution
         */
        try {
            bufferedReader = new BufferedReader(
                    new FileReader("StatespaceBroker.txt"));
            String line;
            int index = 0;
            while ((line = bufferedReader.readLine()) != null) {
                stateSpace[index] = Double.parseDouble(line);
                index++;
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Error: In the reading of stateSpace for broker");
        }
    }

    public void setStartingPoint() {
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
            gui.refreshQuoteData(quotes);
            updateTD();
            updateStateVariables();
            writeStateSpace();
            updateState();
            updateIndexOfBestMove();
            timesteps++;         
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
        int i1 = -1;
        int i2 = -1;
        int i3 = -1;
        int i4 = -1;
        int i5 = -1;
        for (int i = 0; i < top5.size(); i++) {
            top5.remove(i);
        }
        for (int j = 0; j < 10; j++) {
            boolean notFound = true;
            if ((i1 == -1 || stateSpace[state + (int) (Math.pow(3, 10) * j)] > stateSpace[i1]) && notFound) {
                indexOfBestMove = state + (int) (Math.pow(3, 10) * j);
                i5 = i4;
                i4 = i3;
                i3 = i2;
                i2 = i1;
                i1 = j;
                notFound = false;
            }
            if ((i2 == -1 || stateSpace[state + (int) (Math.pow(3, 10) * j)] > stateSpace[i2]) && notFound) {
                i5 = i4;
                i4 = i3;
                i3 = i2;
                i2 = j;
                notFound = false;
            }
            if ((i3 == -1 || stateSpace[state + (int) (Math.pow(3, 10) * j)] > stateSpace[i3]) && notFound) {
                i5 = i4;
                i4 = i3;
                i3 = j;
                notFound = false;
            }
            if ((i4 == -1 || stateSpace[state + (int) (Math.pow(3, 10) * j)] > stateSpace[i4]) && notFound) {
                i5 = i4;
                i4 = j;
                notFound = false;
            }
            if ((i5 == -1 || stateSpace[state + (int) (Math.pow(3, 10) * j)] > stateSpace[i5]) && notFound) {
                i5 = j;
                notFound = false;
            }
        }
        top5.add(quotes.get(i1));
        top5.add(quotes.get(i2));
        top5.add(quotes.get(i3));
        top5.add(quotes.get(i4));
        top5.add(quotes.get(i5));
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

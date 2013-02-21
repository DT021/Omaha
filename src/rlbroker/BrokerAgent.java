/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rlbroker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import javax.sound.midi.SysexMessage;
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
    LoadingGUI loadingGUI = new LoadingGUI();

    public BrokerAgent() {
        loadingGUI.setVisible(true);
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
        for (int i = 0; i < quotes.size(); i++) {
            quotes.get(i).singleStep();
            System.out.println(quotes.get(i).getName());
        }
        updateTop5();
        gui.setVisible(true);
        loadingGUI.setVisible(false);
        while (true) {
            gui.updateValues(top5, equity);
            gui.refreshQuoteData(quotes);
        }
    }

    public void trainAll() {
        for (int j = 0; j < 100; j++) {
            for (int i = 0; i < quotes.size(); i++) {

                System.out.print("Training: " + quotes.get(i).getName());
                quotes.get(i).trainAgent();
                System.out.println("Equity " + quotes.get(i).getName() + ": " + quotes.get(i).getEquity());

            }
        }
    }

    public void updateTop5() {
        double[] vals = new double[quotes.size()];
        double[] valsRefrence = new double[quotes.size()];

        for (int i = 0; i < quotes.size(); i++) {
            vals[i] = quotes.get(i).stateSpace[quotes.get(i).getIndexOfBestMove()];
            valsRefrence[i] = quotes.get(i).stateSpace[quotes.get(i).getIndexOfBestMove()];
        }
        Arrays.sort(vals);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < valsRefrence.length; j++) {
                if (valsRefrence[j] == vals[i]) {
                    top5.add(quotes.get(j));
                }
            }
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
        double[] vals = new double[quotes.size()];
        double[] valsRefrence = new double[quotes.size()];

        for (int i = 0; i < quotes.size(); i++) {
            vals[i] = stateSpace[getState() + (int) (Math.pow(3, 10) * i)];
            valsRefrence[i] = stateSpace[getState() + (int) (Math.pow(3, 10) * i)];
        }
        Arrays.sort(vals);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < valsRefrence.length; j++) {
                if (valsRefrence[j] == vals[i]) {
                    top5.add(quotes.get(j));
                    if (i == 0) {
                        indexOfBestMove = getState() + (int) (Math.pow(3, 10) * i);
                    }
                }
            }
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

    //********END OF AGENT METHODS************
    public int getIndexOfBestMove() {
        return indexOfBestMove;
    }

    public int getIndexOfPreviousBestMove() {
        return indexOfPreviousBestMove;
    }

    public int getState() {
        return state;
    }

    public double getReward() {
        return reward;
    }

    public double getTemporalDifference() {
        return temporalDifference;
    }

    public int getTimesteps() {
        return timesteps;
    }

    public void setTimesteps(int timesteps) {
        this.timesteps = timesteps;
    }

    public double getProfit() {
        return profit;
    }

    public double getEquity() {
        return equity;
    }

    public void setTemporalDifference(double temporalDifference) {
        this.temporalDifference = temporalDifference;
    }
}

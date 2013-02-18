/*
 * Q learning RL agent with a learning rate of 0.1 and a discount rate of 0.99
 */
package rlbroker;

import java.awt.Dimension;
import java.awt.Panel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Agent {
    
    /* Alex's notes:
     * - Turn Equity into an array and create getters for 
     * 
     *  a. one day
     *  b. five days
     *  c. 30 days
     *  d. 90 days
     *  e. 365 days
     *  f. 2 years
     *  g. maximum
     * 
     */
    
    
    public enum Actions {

        BUY(0), SELL(1), HOLD(2);
        private int index;

        private Actions(int index) {
            this.index = index;
        }
    };
    
    private double high;
    private double low;
    private double open;
    private double previousHigh;
    private double previousLow;
    private double previousOpen;
    private int indexOfBestMove;
    private int indexOfPreviousBestMove;
    private int state;
    private double[] highInput = new double[5000];//please make this nicer
    private double[] lowInput = new double[5000];// please make this nicer
    private double[] openInput = new double[5000];// please make this nicer
    private double reward;
    private Actions action;
    private String name;
    private double temporalDifference;
    private static double learningRate = 0.1;
    private static double discountRate = 0.99;
    private int timesteps = 0;
    private boolean isHolding;
    Grapher rewardGrapher;
    Grapher equityGrapher;
    Grapher openGrapher;
    private double buyinOpen;
    private double profit;
    private double equity;
    BufferedReader bufferedReader;
    FileWriter fileWriter;
    BufferedWriter bufferedWriter;
    JFrame frame;
    JPanel panel;
    JLabel label;
    double[] stateSpace = new double [10*10*10*3];
    
    public Agent(String Quote) {
        setName(Quote);
        
        label = new JLabel();
        label.setVisible(true);
        frame = new JFrame();
        frame.add(label);
        frame.setVisible(true);
        
        rewardGrapher = new Grapher(getName(), "Reward", "Reward", "Time Steps");
        rewardGrapher.setVisible(true);
        openGrapher = new Grapher("Open Price", "Open Price", "Time Steps", "Open Ptice");
        openGrapher.setVisible(true);
        equityGrapher = new Grapher("Agent Equity", "Equity", "Time Steps", "Equity");
        equityGrapher.setVisible(true);
        readSpace();
        
        Calendar calendar = Calendar.getInstance(); 
        fetchHistoricData(1, 1, 1900, calendar.get( // starts initially at the 1/1/1900
                Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR));
        trainAgent();
    }

    public void trainAgent() {
        
        int steps = 0;
        for (int i = (getHighInput().length - 2); i >= 0; i--) { // moving from back to front
            if (highInput[i]!= 0) {
                act(highInput[i], lowInput[i], openInput[i]);
                steps++;
                setTimesteps(steps);
            }
        }
    }

    public void act(double newHigh, double newLow, double newOpen) {
//        update the previous step
        updateTD(newOpen);
        updateStateVariables();
        updateHoldings();
        writeStateSpace();
        setHigh(newHigh);
        setLow(newLow);
        setOpen(newOpen);
//        choose new step
        graph();
        updateState();
        determineAction();
        
        label.setText(
                "<html>Quoting: " + name
                + "<br>Profit: " + getProfit()
                + "<br>Time step: " + getTimesteps()
                + "<br>Action: " + getAction()
                + "<br>Previous index: " + getIndexOfBestMove()
                + "<br>Precvious State value: " + stateSpace[getIndexOfPreviousBestMove()]
                + "<br>Buy in price: " + getBuyinOpen()
                + "<br>Reward : " + getReward()
                + "<br>Is Holding: " + isHolding 
                + "</html>");  
       
        frame.add(label);
        frame.repaint();
    }

    public void updateTD(double open) {
        determineReward(open);
        setTemporalDifference(
                learningRate
                * (getReward()
                + (discountRate
                * (stateSpace[getIndexOfBestMove()] - stateSpace[getIndexOfPreviousBestMove()]))));

        stateSpace[getIndexOfPreviousBestMove()] += learningRate * getTemporalDifference();
//        System.out.println(
//                "TD: " + getTemporalDifference()+
//                "\nStateValue: " + stateSpace[getIndexOfPreviousBestMove()]+
//                "\nlearning Rate: "+ learningRate+
//                "\nstate: " + getState() +
//                "\ndiscount Rate: "+ discountRate+
//                "\nreward: " + getReward()+
//                "\nI hate life:" + (getTemporalDifference()));   
    }

    public void updateStateVariables() {
        setIndexOfPreviousBestMove(indexOfBestMove);
        setPreviousHigh(getHigh());
        setPreviousLow(getLow());
        setPreviousOpen(getOpen());

    }

    public void updateHoldings() {
        if (getAction() == Actions.BUY) {
            setBuyinOpen(getOpen());
            setIsHolding(true);
            profit -= getOpen();
        }else if (getAction() == Actions.SELL){
            setIsHolding(false);
            profit+=getOpen();
            equity+=(getOpen()-getBuyinOpen());
            setBuyinOpen(0);
        }
    }

    public void graph() {
        rewardGrapher.update(getTimesteps(), getReward());
        openGrapher.update(getTimesteps(), getOpen());
        equityGrapher.update(getTimesteps(), getEquity());
    }

    public void writeStateSpace() {
    try {
            fileWriter = new FileWriter("Statespace" + getName() + ".txt");
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

    public void determineReward(double open) {

        setReward(((open - getOpen()) / getOpen()));
    }

    public void determineAction() {
        if (isHolding) {
            if (stateSpace[getState() + (1000 * Actions.SELL.index)] >= stateSpace[getState()+ (1000 * Actions.HOLD.index)]) {
//                then sell
                setAction(Actions.SELL);
                setIndexOfBestMove(getState()+ (1000*getAction().index));
            } else {
//                then hold
                setAction(Actions.HOLD);
                setIndexOfBestMove(getState()+ (1000*getAction().index));
            }
        } else if (!isHolding) {
            if (stateSpace[getState() + (21 * 21 * 0)] >= stateSpace[getState() + (21 * 21 * 2)]) {
//                then buy
                setAction(Actions.BUY);
                setIndexOfBestMove(getState()+ (1000*getAction().index));
            } else {
//                then hold
                setAction(Actions.HOLD);
                setIndexOfBestMove(getState()+ (1000*getAction().index));
            }
        }
        
        if (Math.random() < 0.1) {
            Random random = new Random();
            int randomAction = random.nextInt(3);
            while ((isHolding && (randomAction == 1 || randomAction == 2)) || (!isHolding && (randomAction == 0 || randomAction == 2))) {
                randomAction = random.nextInt(3);
            }
            if (Actions.BUY.index == randomAction) {
                setIndexOfBestMove(getState()+ (1000 * Actions.BUY.index));   
            }else if (Actions.SELL.index == randomAction){
                setIndexOfBestMove(getState() + 1000*Actions.SELL.index);
            }else if(Actions.HOLD.index == randomAction){
                setIndexOfBestMove(getState() + 1000*Actions.HOLD.index);
            }
        }
    }
    
    public void updateState(){
        double indexHigh = ((getHigh()-getPreviousHigh())/getPreviousHigh());
        double indexLow = (getLow()/getPreviousLow())/getPreviousLow();
        double indexOpen = (getOpen() - getPreviousOpen())/getPreviousOpen();
//        finish disctitisation
    
        setState(
                (discritize(indexHigh) +
                (10 * discritize(indexLow)) +
                (100 * discritize(indexOpen))));
    }
    
    public int discritize(double value){
        if (value < -0.1) {
          return 0;
        }else if(value >= -0.1 && value < -0.08){
          return 1;
        }else if(- 0.08 >= -0.08 && value < -0.06){
          return 2;
        }else if(value >= -0.06 && value < -0.04){
          return 3;
        }else if(value >= -0.04 && value < -0.02){
          return 4;
        }else if(value >= -0.02 && value < 0){
          return 5;
        }else if(value >= 0 && value < 0.02){
          return 6;
        }else if(value >= 0.04 && value < 0.06){
          return 7;
        }else if(value >= 0.06 && value < 0.08){
          return 8;
        }else if(value >= 0.08 && value < 0.1  ){
          return 9;
        }else{
          return 9;
        }
        
        
    }
    
    public void fetchHistoricData(
            int startDay, int startMonth, int startYear, 
            int endDay, int endMonth, int endYear
            ){
        try {
            URL yahoofin = new URL(
                    "http://ichart.finance.yahoo.com/table.csv?s=" + getName() + "&a=" +
                    formatMonth(startMonth) + "&b=" + formatDay(startDay) + "&c=" + startYear +
                    "&d=" + formatMonth(endMonth) + "&e=" + formatMonth(endMonth) + "&f=" + endYear + "&g=d.csv&ignore=.csv");
            
            URLConnection yc = yahoofin.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            int i = 0;
            in.readLine();// skipping the first line, as it's just titles
            while ((inputLine = in.readLine()) != null) {
                String[] info = inputLine.split(",");
                openInput[i] = Double.parseDouble(info[1]);
                highInput[i] = Double.parseDouble(info[2]);
                lowInput[i] = Double.parseDouble(info[3]);
                i++;
            } 
            in.close();
        } catch (Exception ex) {
            System.out.println("Error in historic fetch for " + getName());
        }
    }
    
    public String formatMonth(int month){
        month -= 1;
        if (month < 0) {
            return ("0"+month);
        }else{
            return  "" + month;
        }
    }
    
    public void readSpace() {
        /* Created By: Alex Kearney 
         * Reads statespace from previous execution
         */
        try {
            bufferedReader = new BufferedReader(
                    new FileReader("Statespace" + name + ".txt"));
            String line;
            int index = 0;
            while ((line = bufferedReader.readLine()) != null) {
                stateSpace[index] = Double.parseDouble(line);
                index++;
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Error: In the reading of stateSpace");
        }
    }

    public String formatDay(int day){
        if (day < 10) {
            return "0"+day;
        }else{
            return ""+day;
        }
    }
//***************END OF ACTOR CODE***************
//               Getters And Setters
    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double[] getHighInput() {
        return highInput;
    }

    public void setHighInput(double[] highInput) {
        this.highInput = highInput;
    }

    public double[] getLowInput() {
        return lowInput;
    }

    public void setLowInput(double[] lowInput) {
        this.lowInput = lowInput;
    }

    public double[] getOpenInput() {
        return openInput;
    }

    public void setOpenInput(double[] openInput) {
        this.openInput = openInput;
    }

    public double getTemporalDifference() {
        return temporalDifference;
    }

    public void setTemporalDifference(double temporalDifference) {
        this.temporalDifference = temporalDifference;
    }

    public double[] getStateSpace() {
        return stateSpace;
    }

    public void setStateSpace(double[] stateSpace) {
        this.stateSpace = stateSpace;
    }

    public int getTimesteps() {
        return timesteps;
    }

    public void setTimesteps(int timesteps) {
        this.timesteps = timesteps;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public int getIndexOfBestMove() {
        return indexOfBestMove;
    }

    public void setIndexOfBestMove(int indexOfBestMove) {
        this.indexOfBestMove = indexOfBestMove;
    }

    public int getIndexOfPreviousBestMove() {
        return indexOfPreviousBestMove;
    }

    public void setIndexOfPreviousBestMove(int indexOfPreviousBestMove) {
        this.indexOfPreviousBestMove = indexOfPreviousBestMove;
    }

    public double getPreviousHigh() {
        return previousHigh;
    }

    public void setPreviousHigh(double previousHigh) {
        this.previousHigh = previousHigh;
    }

    public double getPreviousLow() {
        return previousLow;
    }

    public void setPreviousLow(double previousLow) {
        this.previousLow = previousLow;
    }

    public double getPreviousOpen() {
        return previousOpen;
    }

    public void setPreviousOpen(double previousOpen) {
        this.previousOpen = previousOpen;
    }

    public boolean isIsHolding() {
        return isHolding;
    }

    public void setIsHolding(boolean isHolding) {
        this.isHolding = isHolding;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }

    public double getBuyinOpen() {
        return buyinOpen;
    }

    public void setBuyinOpen(double buyinOpen) {
        this.buyinOpen = buyinOpen;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getEquity() {
        return equity;
    }

    public void setEquity(double equity) {
        this.equity = equity;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public static double getLearningRate() {
        return learningRate;
    }

    public static void setLearningRate(double aLearningRate) {
        learningRate = aLearningRate;
    }

    public static double getDiscountRate() {
        return discountRate;
    }

    public static void setDiscountRate(double aDiscountRate) {
        discountRate = aDiscountRate;
    }
    
}
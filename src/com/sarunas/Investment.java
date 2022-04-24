package com.sarunas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;


public class Investment {

    private double invest;
    private double reward;
    private LocalDate startDate;
    private LocalDate endDate;
    private int paymentDay;
    private String reinvest;


    public Investment(double invest, double reward, LocalDate startDate, LocalDate endDate, int paymentDay, String reinvest) {
        this.invest = invest;
        this.reward = reward;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentDay = paymentDay;
        this.reinvest = reinvest;
    }

    public double getInvest() {
        return invest;
    }

    public void setInvest(double invest) {
        this.invest = invest;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(int paymentDay) {
        this.paymentDay = paymentDay;
    }

    public String getReinvest() {
        return reinvest;
    }

    public void setReinvest(String reinvest) {
        this.reinvest = reinvest;
    }




    public List<LocalDate> rewardDate() {                                                    // Reward Date
        List<LocalDate> rewardDateList = new ArrayList<>();
        for (LocalDate date = this.startDate; date.isBefore(this.endDate) || date.isEqual(this.endDate); date = date.plusMonths(1)) {
            if (endDate.isAfter(date.withDayOfMonth(this.paymentDay))) {
                rewardDateList.add(date.withDayOfMonth(this.paymentDay));
            } else {
                rewardDateList.add(date);
            }
        }
        return rewardDateList;
    }



    public List<Double> investmentAmount() {                              // Investment Amount
        List<Double> investmentAmountList = new ArrayList<>();
        LocalDate startPlus = startDate.withDayOfMonth(paymentDay);
        int period = Period.between(startDate, startPlus).getDays();
        System.out.println(period);
        if (!startDate.isAfter(startPlus)) {
            double inv = this.invest;
            double reward = (double) (inv * ((this.reward / 365) * period / 100));
            if (this.reinvest.toLowerCase().equals("yes")) {
                investmentAmountList.add(inv);
                inv += reward;
            } else if (this.reinvest.toLowerCase().equals("no")) {
                investmentAmountList.add(inv);
            }
            for (LocalDate date = startPlus; date.isBefore(this.endDate) || date.isEqual(this.endDate); date = date.plusDays(date.getMonthValue())) {
                reward = (double) (inv * ((this.reward / 365) * date.lengthOfMonth()) / 100);
                if (this.reinvest.toLowerCase().equals("yes")) {
                    investmentAmountList.add(inv);
                    inv += reward;
                } else if (this.reinvest.toLowerCase().equals("no")) {
                    investmentAmountList.add(inv);
                }
            }
        } else {
            double inv = this.invest;
            for (LocalDate date = this.startDate; date.isBefore(this.endDate) || date.isEqual(this.endDate); date = date.plusDays(date.getMonthValue())) {
                double reward = (double) (inv * ((this.reward / 365) * date.lengthOfMonth()) / 100);
                if (this.reinvest.toLowerCase().equals("yes")) {
                    investmentAmountList.add(inv);
                    inv += reward;
                } else if (this.reinvest.toLowerCase().equals("no")) {
                    investmentAmountList.add(inv);
                }
            }
        }
        return investmentAmountList;
    }


    public List<Double> rewardAmount() {                         // Reward Amount
        List<Double> rewardAmountList = new ArrayList<>();
        List<Double> investmentAmountList = investmentAmount();
        LocalDate startPlus = startDate.withDayOfMonth(paymentDay);
        int period = Period.between(startDate, startPlus).getDays();
        System.out.println(period);
        if (!startDate.isAfter(startPlus)) {
            double inv = this.invest;
            double reward = (double) (inv * ((this.reward / 365) * period / 100));
            if (this.reinvest.toLowerCase().equals("yes")) {
                investmentAmountList.add(inv);
                inv += reward;
            }
            rewardAmountList.add(reward);
            for (LocalDate date = startPlus; date.isBefore(this.endDate) || date.isEqual(this.endDate); date = date.plusDays(date.getMonthValue())) {
                reward = (double) (inv * ((this.reward / 365) * date.lengthOfMonth()) / 100);
                if (this.reinvest.toLowerCase().equals("yes")) {
                    investmentAmountList.add(inv);
                    inv += reward;
                }
                rewardAmountList.add(reward);
            }
        } else {
            double inv = this.invest;
            for (LocalDate date = this.startDate; date.isBefore(this.endDate) || date.isEqual(this.endDate); date = date.plusDays(date.getMonthValue())) {
                double reward = (double) (inv * ((this.reward / 365) * date.lengthOfMonth()) / 100);
                if (this.reinvest.toLowerCase().equals("yes")) {
                    investmentAmountList.add(inv);
                    inv += reward;

                }
                rewardAmountList.add(reward);
            }
        }

        return  rewardAmountList;

    }


    public List<Double> totalRewardToDate() {                       //  Total Reward Amount To Date
        List<Double> totalRewardToDateList = new ArrayList<>();
        List<Double> rewardAmountList = rewardAmount();
        double rewardAmount = 0;
        for (int i = 0; i < rewardAmountList.size(); i++) {
            rewardAmount += rewardAmountList.get(i);
            totalRewardToDateList.add(rewardAmount);
        }
        return totalRewardToDateList;
    }

    public void printData() {
        List<LocalDate> rewardDateList = rewardDate();
        List<Double> investmentAmountList = investmentAmount();
        List<Double> rewardAmountList = rewardAmount();
        List<Double> totalRewardToDateList = totalRewardToDate();

        System.out.println("Line#  Reward Date  Investment Amount  Reward Amount  Total Reward Amount To Date   Stacking Reward Rate");
        for (int i = 0; i < rewardDateList.size(); i++) {
            System.out.printf("%s       %s        %.6f       %.6f                %.6f                 %.2f%%\n",
                    i, rewardDateList.get(i),  investmentAmountList.get(i),
                    rewardAmountList.get(i), totalRewardToDateList.get(i), this.reward);
        }
    }

    public void generateToCSV() throws FileNotFoundException {
        List<LocalDate> rewardDateList = rewardDate();
        List<Double> investmentAmountList = investmentAmount();
        List<Double> rewardAmountList = rewardAmount();
        List<Double> totalRewardToDateList = totalRewardToDate();

        File csvFile = new File("file.csv");
        PrintWriter out = new PrintWriter(csvFile);
        out.println("Line#  Reward Date  Investment Amount  Reward Amount  Total Reward Amount To Date   Stacking Reward Rate");
        for (int i = 0; i < rewardDateList.size(); i++) {
            out.printf("%s         %s           %.6f          %.6f             %.6f                %.2f%%\n",
                    i, rewardDateList.get(i),  investmentAmountList.get(i),
                    rewardAmountList.get(i), totalRewardToDateList.get(i), this.reward);
        }
        out.close();
    }
}


package com.sarunas;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {


        Scanner scanner = new Scanner(System.in);
        Investment investment = null;
        System.out.println("\nAvailable actions: \npress");
        System.out.println("1  -  enter data\n" + "2  -  change reinvestment options\n" + "3  -  show list of possible actions\n"
                + "4  -  generate data output to CSV file\n" + "5  -  print data output\n" + "6  -  quit.");

        boolean flag = true;
        while (flag) {
            int actions = scanner.nextInt();
            scanner.nextLine();
            switch (actions) {
                case 1:
                    try {
                        System.out.println("Enter investment amount: ");
                        double invest = scanner.nextDouble();
                        System.out.println("Enter reward rate: ");
                        int reward = scanner.nextInt();
                        System.out.println("Enter years of start date: ");
                        int startYears = scanner.nextInt();
                        System.out.println("Enter month of start date: ");
                        int startMonth = scanner.nextInt();
                        System.out.println("Enter day of start date: ");
                        int startDay = scanner.nextInt();
                        System.out.println("Enter years of end date: ");
                        int endYears = scanner.nextInt();
                        System.out.println("Enter month of end date: ");
                        int endMonth = scanner.nextInt();
                        System.out.println("Enter day of end date: ");
                        int endDay = scanner.nextInt();
                        System.out.println("Enter payment day: ");
                        int paymentDay = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter payment reinvest choice yes/no:");
                        String reinvest = scanner.nextLine();

                        investment = new Investment(invest, reward, LocalDate.of(startYears, startMonth, startDay)
                                , LocalDate.of(endYears, endMonth, endDay), paymentDay, reinvest);
                        investment.printData();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input.");
                    }
                    break;
                case 2:
                    System.out.println("Change your reinvest option yes/no");
                    String reinvestOption = scanner.nextLine();
                    boolean correctInput = true;
                    while (correctInput)
                        if (reinvestOption.toLowerCase().equals("yes") || reinvestOption.toLowerCase().equals("no")) {
                            investment.setReinvest(reinvestOption);
                            correctInput = false;
                        } else {
                            System.out.println("Invalid input. Please, enter \"yes\" or \"no\".");
                            reinvestOption = scanner.nextLine();
                        }
                    break;
                case 3:
                    System.out.println("\nAvailable actions: \npress");
                    System.out.println("1  -  enter data\n" + "2  -  change reinvestment options\n" + "3  -  show list of possible actions\n"
                            + "4  -  generate data output to CSV file\n" + "5  -  print data output\n" + "6  -  quit.");
                    break;
                case 4:
                    investment.generateToCSV();
                    break;
                case 5:
                    investment.printData();
                    break;
                case 6:
                    System.out.println("Quit.");

                    flag = false;
                    break;
                default:
                    System.out.println("Not valid choice. Choose from 1 to 6");
            }
        }
    }
}


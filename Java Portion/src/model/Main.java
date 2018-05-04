package model;

import CDao.DataStorage;
import ValDao.cdw_sapp_branch;
import ValDao.cdw_sapp_creditcard;
import ValDao.cdw_sapp_customer;
import ValDao.queries;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)  {
        DataStorage datasource = new DataStorage();
        if (!datasource.open()) {
            System.out.println("Can't open datasource");
            return;
            }
        int choice;
        int input;
        int choice1;

        //User Interface

        String initial = "_______________________________________\n" +
                " Option 1: Show transaction made by customer residing in a particular zipcode" + "\n" +
                " Option 2: Show number and total values of transaction for a given type " + "\n" +
                " Option 3: Show number and total values of transaction in given state" + "\n" +
                " Option 4: Exit!" + "\n" +
                "________________________________________\n";
        String initial2 = "______________________________________\n" + "Option 1: Show customer details" + "\n" +
                 " Option 2: Edit customer details " + "\n" +
                 "Option 3: Generate a invoice " + "\n" +
                 " Option 4: Show transcation period " + "\n" +
                 " Option 5: Exit!" + "\n" +
                "____________________________________\n";

        String transtype = "_______________________________\n" + " Option 1: Education  " + "\n" +
                "Option 2: Entertainment  " + "\n" +
                "Option 3: Grocery " + "\n" +
                "Option 4: Gas  " + "\n" +
                "Option 5: Bills  " + "\n" +
                "Option 6: Test  " + "\n" +
                "Option 7: HealthCare " + "\n" +
                "\n" +
                "-------------------------------\n";



        String modifyc = "___________________________________\n" + " 1: Change FirstName " + "\n" +
                " 2: Change MiddleName  " + "\n" +
                " 3: Change LastName  " + "\n" +
                " 4: Change SSN  " + "\n" +
                " 5: Change Credit Card No." + "\n" +
                " 6: Change Apt No. " + "\n" +
                " 7: Change Street Name " + "\n" +
                " 8: Change City " + "\n" +
                " 9: Change State " + "\n" +
                " 10: Change Country " + "\n" +
                " 11: Change Zip " + "\n" +
                " 12: Change Phone " + "\n" +
                " 13: Change Email " + "\n" +
                " 14: EXIT" + "\n" +
                "*************************\n";


        System.out.println("Please Pick Module: ");
        System.out.println(initial);

        Scanner scanner = new Scanner(System.in);
        input = scanner.nextInt();

        if (input == 1) {

            do {
                System.out.println("JDBC Menu: What do you want to run ");
                System.out.println(initial2);
                choice1 = scanner.nextInt();
                switch (choice1) {
                    case 1:
                        System.out.println("Fill Zip code, Month and Year");
                        System.out.println("Enter Customer Zip: ");
                        int title2 = scanner.nextInt();
                        System.out.println("Enter Month Number: ");
                        int month1 = scanner.nextInt();
                        System.out.println("Enter Year Number: ");
                        int year2 = scanner.nextInt();

                        List<cdw_sapp_creditcard> TransZip = new ArrayList<cdw_sapp_creditcard>();
                        List<cdw_sapp_customer> TransZipcust = new ArrayList<cdw_sapp_customer>();
                        datasource.query_TransZip(title2, month1, year2, TransZip, TransZipcust);

                        try {
                            if (TransZip.isEmpty() || TransZipcust.isEmpty()) {
                                System.out.println("Customer does not have transcation: ");
                                return;
                            } else
                                for (cdw_sapp_creditcard creditcard : TransZip) {
                                    System.out.println(creditcard);
                                }
                            for (cdw_sapp_customer customer : TransZipcust) {
                                System.out.println("Customer Details[" +
                                        "FIRST NAME='" + customer.getFIRST_NAME() + '\'' +
                                        ", MIDDLE NAME='" + customer.getMIDDLE_NAME() + '\'' +
                                        ", LAST NAME='" + customer.getLAST_NAME() + '\'' +
                                        ", SSN=" + customer.getCREDIT_CARD_NO() +
                                        ", CREDIT CARD NO='" + customer.getAPT_NO() + '\'' +
                                        ", APT NO='" + customer.getSTREET_NAME() + '\'' +
                                        ", STREET NAME='" + customer.getCUST_CITY() + '\'' +
                                        ", CITY='" + customer.getCUST_STATE() + '\'' +
                                        ", STATE='" + customer.getCUST_COUNTRY() + '\'' +
                                        ", COUNTRY='" + customer.getCUST_ZIP() + '\'' +
                                        ", ZIP='" + customer.getCUST_PHONE() + '\'' +
                                        ", PHONE=" + customer.getCUST_EMAIL() +

                                        ']');//fix this by write new output ssn cuz error with ssn
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                        break;

                    case 2:
                        System.out.println("Type option below : ");
                        System.out.println(transtype);
                        String title3 = scanner.next();

                        List<cdw_sapp_creditcard> transtotal = datasource.query_DisplayNumTotal(title3);

                        try {
                            if (transtotal.isEmpty()) {
                                System.out.println("Customer does not have transcation: ");
                                return;
                            } else
                                for (cdw_sapp_creditcard creditcard : transtotal) {
                                    System.out.println("count: " + creditcard.getCount() + " transcation sum: " + creditcard.getTRANSCATION_VALUE());
                                }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }

                        break;
                    case 3:
                        System.out.println("Transaction value based on State: ");
                        String state = scanner.next();

                        List<cdw_sapp_creditcard> TranStat = new ArrayList<cdw_sapp_creditcard>();
                        List<cdw_sapp_branch> TranBranch = new ArrayList<cdw_sapp_branch>();
                        datasource.query_DISPLAY_BYSTATE(state, TranStat, TranBranch);
                        try {
                            if (TranBranch.isEmpty()) {
                                System.out.println("No transaction: ");
                                return;
                            } else {
                                for (cdw_sapp_creditcard cdw_sapp_creditcard : TranStat) {
                                    System.out.println("The Count is: " + cdw_sapp_creditcard.getCount() + " The Transcation is " + cdw_sapp_creditcard.getTRANSCATION_VALUE());

                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());

                        }
                        break;

                }
            }
            while (choice1 != 4);

        } else {
            do {
                System.out.println("JDBC Menu: What do you want to run ");
                System.out.println(initial2);
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Enter Customer SSN");
                        String title = scanner.next();
                        List<cdw_sapp_customer> custDetails = datasource.query_GetCusDetails(title);

                    {
                        try {
                            if (custDetails.isEmpty()) {
                                System.out.println(" Customer does not exist for this SSN:  ");
                                return;
                            } else
                                for (cdw_sapp_customer customer : custDetails) {
                                    System.out.println(customer);
                                }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    break;

                    case 2:
                        String first;
                        long ssn1;

                        System.out.println("ENTER SSN to start change");
                        ssn1 = scanner.nextLong();
                        boolean exited = false;
                        while (!exited) {
                            System.out.println(modifyc);
                            int i = scanner.nextInt();
                            if (i != 14) {
                                switch (i) {


                                    case 1:
                                        System.out.println("Enter " + queries.COLUMN_FIRST_NAME);
                                        first = scanner.next();
                                        datasource.Query_modify(queries.COLUMN_FIRST_NAME, first, ssn1);
                                        break;
                                    case 2:
                                        System.out.println("Enter " + queries.COLUMN_MIDDLE_NAME);
                                        first = scanner.next();
                                        datasource.Query_modify(queries.COLUMN_MIDDLE_NAME, first, ssn1);
                                        break;
                                    case 3:
                                        System.out.println("Enter " + queries.COLUMN_LAST_NAME);
                                        first = scanner.next();
                                        datasource.Query_modify(queries.COLUMN_LAST_NAME, first, ssn1);
                                        break;
                                    case 4:
                                        System.out.println("Enter " + queries.COLUMN_SSN);
                                        first = scanner.next();
                                        datasource.Query_modify(queries.COLUMN_SSN, first, ssn1);
                                        break;
                                    case 5:
                                        System.out.println("Enter " + queries.COLUMN_CREDIT_CARD);
                                        first = scanner.next();
                                        datasource.Query_modify(queries.COLUMN_CREDIT_CARD, first, ssn1);
                                        break;
                                    case 6:
                                        System.out.println("Enter " + queries.COLUMN_APT_NO);
                                        first = scanner.next();
                                        datasource.Query_modify(queries.COLUMN_APT_NO, first, ssn1);
                                        break;
                                    case 7:
                                        System.out.println("Enter " + queries.COLUMN_STREET_NAME);
                                        first = scanner.next();
                                        datasource.Query_modify(queries.COLUMN_STREET_NAME, first, ssn1);
                                        break;
                                    case 8:
                                        System.out.println("Enter " + queries.COLUMN_CUST_CITY);
                                        first = scanner.next();
                                        datasource.Query_modify(queries.COLUMN_CUST_CITY, first, ssn1);
                                        break;
                                    case 9:
                                        System.out.println("Enter " + queries.COLUMN_CUST_STATE);
                                        first = scanner.next();
                                        datasource.Query_modify(queries.COLUMN_CUST_STATE, first, ssn1);
                                        break;
                                    case 10:
                                        System.out.println("Enter " + queries.COLUMN_CUST_COUNTRY);
                                        first = scanner.next();
                                        datasource.Query_modify(queries.COLUMN_CUST_COUNTRY, first, ssn1);
                                        break;
                                    case 11:
                                        System.out.println("Enter " + queries.COLUMN_CUST_ZIP);
                                        first = scanner.next();
                                        datasource.Query_modify(queries.COLUMN_CUST_ZIP, first, ssn1);
                                        break;
                                    case 12:
                                        System.out.println("Enter " + queries.COLUMN_CUST_PHONE);
                                        first = scanner.next();
                                        datasource.Query_modify(queries.COLUMN_CUST_PHONE, first, ssn1);
                                        break;
                                    case 13:
                                        System.out.println("Enter " + queries.COLUMN_CUST_EMAIL);
                                        first = scanner.next();
                                        datasource.Query_modify(queries.COLUMN_CUST_EMAIL, first, ssn1);
                                        break;


                                }
                            } else {
                                break;
                            }
                        }

                        break;

                    case 3:
                        System.out.println("Transaction value based on Card no. : ");
                        String cred = scanner.next();
                        String month = scanner.next();
                        String year = scanner.next();

                        List<cdw_sapp_creditcard> monthTotal = datasource.query_SumTransValueCard(cred, year, month);

                        try {
                            if (monthTotal.isEmpty()) {
                                System.out.println("Credit card has zero Transaction: ");
                                return;
                            } else {
                                for (cdw_sapp_creditcard creditcard : monthTotal) {
                                    System.out.println(creditcard);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("error: " + e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.println("Transaction value based on ssn, year range and month range and day range");
                        String ssn = scanner.next();
                        int year11 = scanner.nextInt();
                        int year12 = scanner.nextInt();
                        int month11 = scanner.nextInt();
                        int month12 = scanner.nextInt();
                        int day11 = scanner.nextInt();
                        int day12 = scanner.nextInt();

                        List<cdw_sapp_creditcard> transactionbyssn = datasource.query_DisplayCustTransDate(ssn, year11, year12, month11, month12, day11, day12);
                        try {
                            if (transactionbyssn.isEmpty()) {
                                System.out.println("No values found");
                                return;
                            } else {
                                for (cdw_sapp_creditcard cdw_sapp_creditcard : transactionbyssn) {
                                    System.out.println(cdw_sapp_creditcard);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("error in code : " + e.getMessage());
                        }
                        break;
                }

            } while (choice != 5);
        }
    }


}




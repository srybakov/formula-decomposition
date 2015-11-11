package com.srybakov.formula;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static int SIZE_RESTRICTION = 1024;
    private static String PLUS_AND_SPACE = "+ ";

    private static final Logger LOG = Logger.getAnonymousLogger();


    public static void main(String[] args) {
        try {
            test(25);
            test(-25);
            test(100);
            test(-100);
        } catch (Exception e){
            LOG.log(Level.INFO, e.getMessage());
        }
    }

    private static void test(int number) throws Exception {
        Represent represent = getRepresent(number);
        printBinaryFormat(number, represent);
        printResult(represent);
    }


    private static Represent getRepresent(int number) throws Exception {
        Represent represent = new Represent(SIZE_RESTRICTION);
        int pos = 0;
        int remain = number;

        while (remain != 0 && pos < SIZE_RESTRICTION) {
            represent.getBitsArray()[pos] = remain & 1;
            remain = remain - represent.getBitsArray()[pos];
            remain = remain / -2;
            pos++;
        }

        checkRepresentFound(number, remain);
        represent.setNumberOfValuableBits(pos);

        return represent;
    }

    private static void checkRepresentFound(int number, int remain) throws Exception {
        if (remain != 0){
            throw new Exception("Number: " + number + " cannot be represented with " + SIZE_RESTRICTION
                    + " bits by given formula");
        }
    }

    private static void printBinaryFormat(int number, Represent represent){
        StringBuilder sb = new StringBuilder("Binary format for (" + number + "): ");
        for (int i = 0; i < represent.getNumberOfValuableBits(); i++){
            sb.append(represent.getBitsArray()[i]).append(" ");
        }
        System.out.println(sb.toString());
    }

    private static void printResult(Represent represent){
        StringBuilder sb = new StringBuilder("Result: ");
        int result = 0;
        for (int i = 0, k = 1; i < represent.getNumberOfValuableBits(); i++, k *= -2){
            int calculatedValue = represent.getBitsArray()[i] * k;
            result += calculatedValue;
            append(sb, calculatedValue);
        }
        System.out.println(getFormedResult(sb, result));
        System.out.println();
    }

    private static String getFormedResult(StringBuilder sb, int calculatedResult){
        return sb.toString().substring(0, sb.length() - PLUS_AND_SPACE.length()) + "= " + calculatedResult;
    }

    private static void append(StringBuilder sb, int calculatedValue){
        if (calculatedValue < 0){
            sb.append("(").append(calculatedValue).append(") ").append(PLUS_AND_SPACE);
        } else {
            sb.append(calculatedValue).append(" ").append(PLUS_AND_SPACE);
        }
    }

    private static class Represent {

        private int[] bitsArray;
        private int numberOfValuableBits = 0;

        public Represent(int arraySize) {
            //skip size checking
            this.bitsArray = new int[arraySize];
        }

        public int getNumberOfValuableBits() {
            return numberOfValuableBits;
        }

        public void setNumberOfValuableBits(int numberOfValuableBits) {
            this.numberOfValuableBits = numberOfValuableBits;
        }

        public int[] getBitsArray() {
            return bitsArray;
        }
    }
}

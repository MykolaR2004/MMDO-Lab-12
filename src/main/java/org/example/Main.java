package org.example;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.Scanner;

public class Main{
    private static final double INITIAL_STEP_SIZE = 1.0;
    private static final double MIN_STEP_SIZE = 0.1;
    private static final int MAX_ITERATIONS = 10000;

    public static void main(String[] args) {
        double a = 1.0;
        double b = 2.0;
        double c = 2.0;

        double[] solution = coordinateDescent(a, b, c, 2, 3);
        System.out.println("Minimum found at: x = " + solution[0] + ", y = " + solution[1]);
    }

    public static double[] coordinateDescent(double a, double b, double c, double x0, double y0) {
        double x = x0;
        double y = y0;
        double stepSize = INITIAL_STEP_SIZE;

        for (int iteration = 1; iteration <= MAX_ITERATIONS; iteration++) {

            double fPrev = f(a, b, c, x, y);

            System.out.println("Iteration " + iteration + ":");
            System.out.println("Starting values: x = " + x + ", y = " + y + ", f(x, y) = " + fPrev);

            // Step 1: Adjust x
            while (true) {
                double fNext = f(a, b, c, x + stepSize, y);
                printComparison(x, x + stepSize, y, fPrev, fNext);
                if (fNext < fPrev) {
                    x += stepSize;
                    fPrev = fNext;
                } else {
                    double fNextDecrease = f(a, b, c, x - stepSize, y);
                    if (fNextDecrease < fPrev) {
                        x -= stepSize;
                        fPrev = fNextDecrease;
                    } else {
                        System.out.println("Variable x locked at: x = " + x + ", f(x, y) = " + f(a, b, c, x, y));
                        break;
                    }
                }
            }

            // Step 2: Adjust y
            while (true) {
                double fNext = f(a, b, c, x, y + stepSize);
                printComparison(y, x, y + stepSize, fPrev, fNext);
                if (fNext < fPrev) {
                    y += stepSize;
                    fPrev = fNext;
                } else {
                    double fNextDecrease = f(a, b, c, x, y - stepSize);
                    if (fNextDecrease < fPrev) {
                        y -= stepSize;
                        fPrev = fNextDecrease;
                    } else {
                        System.out.println("Variable y fixed at: y = " + y + ", f(x, y) = " + f(a, b, c, x, y));
                        break;
                    }
                }
            }

            // If step size is small enough, terminate the search
            if (stepSize < MIN_STEP_SIZE) {
                System.out.println("Search terminated: step size is too small.");
                break;
            }



            // Reduce step size
            stepSize /= 2.0;
            System.out.println("Step size reduced to " + stepSize);
        }

        return new double[]{x, y};
    }

    private static double f(double a, double b, double c, double x, double y) {
        return a * x * x + b * x * y + c * y * y;
    }

    private static void printComparison(double var1, double var2, double var3, double fPrev, double fNext) {
        String comparison;
        if (fPrev < fNext) {
            comparison = ">";
        } else if (fPrev > fNext) {
            comparison = "<";
        } else {
            comparison = "=";
        }
        System.out.println("Comparing f(x = " + var2 + ", y = " + var3 + "): " + fNext + " " + comparison + " " + fPrev);
    }
}

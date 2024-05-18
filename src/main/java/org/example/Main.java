package org.example;


import java.util.Scanner;

public class Main{
    private static final double INITIAL_STEP_SIZE = 1.0;
    private static final double MIN_STEP_SIZE = 0.25;
    private static final int MAX_ITERATIONS = 10000;
    private static final String WARNING_COLOR = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the value for a >> ");
        double a = scanner.nextDouble();
        System.out.print("Enter the value for b >> ");
        double b = scanner.nextDouble();
        System.out.print("Enter the value for c >> ");
        double c = scanner.nextDouble();

//        System.out.println(a+"x^2 + "+b+"xy + "+c+"y^2 = "+f(a, b, c, 2, 3, true));

        double[] solution = coordinateDescent(a, b, c, 2, 3);
        System.out.println("Minimum found at: x = " + solution[0] + ", y = " + solution[1]);
    }

    public static double[] coordinateDescent(double a, double b, double c, double x0, double y0) {
        double x = x0;
        double y = y0;
        double stepSize = INITIAL_STEP_SIZE;

        for (int iteration = 1; iteration <= MAX_ITERATIONS; iteration++) {
            System.out.println();
            System.out.println(ANSI_BLUE + "Iteration " + iteration + " ---------------------------------------------" + ANSI_RESET);

            double fPrev = f(a, b, c, x, y, true);

//            System.out.println("Starting values: x = " + x + ", y = " + y + ", f(x, y) = " + fPrev);

            var iterator = 0;
            // Step 1: Adjust x
            while (true) {
                double fNext = f(a, b, c, x + stepSize, y, iterator <= 0);
                printComparison(new Point(x, y), new Point(x + stepSize, y), fPrev, fNext);
                System.out.println();
                if (fNext < fPrev) {
                    x += stepSize;
                    fPrev = fNext;
                } else {
                    double fNextDecrease = f(a, b, c, x - stepSize, y, true);
                    if (fNextDecrease < fPrev) {
                        x -= stepSize;
                        fPrev = fNextDecrease;
                    } else {
                        printComparison(new Point(x, y), new Point(x - stepSize, y), fNextDecrease,  f(a, b, c, x, y, false));
                        System.out.println(WARNING_COLOR + "Variable x locked at: x = " + x + ANSI_RESET);
                        System.out.println();
                        break;
                    }
                }
                iterator++;
            }

            var iterator2 = 0;
            // Step 2: Adjust y
            while (true) {

                double fNext = f(a, b, c, x, y + stepSize, iterator2 <= 0);
                printComparison(new Point(x, y), new Point(x, y+stepSize), fPrev, fNext);
                System.out.println();
                if (fNext < fPrev) {
                    y += stepSize;
                    fPrev = fNext;
                } else {
                    double fNextDecrease = f(a, b, c, x, y - stepSize, true);
                    if (fNextDecrease < fPrev) {
                        y -= stepSize;
                        fPrev = fNextDecrease;
                    } else {
                        printComparison(new Point(x, y), new Point(x, y + stepSize), fNextDecrease,  f(a, b, c, x, y, false));
                        System.out.println(WARNING_COLOR + "Variable y fixed at: y = " + y + ANSI_RESET);
                        System.out.println();
                        break;
                    }
                }
                iterator2++;
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

    private static double f(double a, double b, double c, double x, double y, boolean show) {
        var firstPart =  a * x * x;
        var secondPart = b * x * y;
        var thirdPart =  c * y * y;
        var result = firstPart + secondPart + thirdPart;
        if (show) System.out.println("F(" + x + ", " + y + ") = " + firstPart + " + " + secondPart + " + " + thirdPart + " = " + result);
        return result;
    }

    private static void printComparison(Point point, Point point2, double fPrev, double fNext) {
        String comparison;
        if (fPrev < fNext) {
            comparison = ">";
        } else if (fPrev > fNext) {
            comparison = "<";
        } else {
            comparison = "=";
        }
        System.out.println(ANSI_GREEN + "Comparing: (" + point2.x() + ", " + point2.y() + ") " + comparison +" (" + point.x() + ", " + point.y() + ")                       (" + fNext + " " + comparison + " " + fPrev + ")" + ANSI_RESET);
    }
}

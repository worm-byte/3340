/*
 * Rosaline Scully
 * January 25, 2025
 * Student ID: 250966670
 * 
 * This program computes the Fibonacci-like sequence L(n) = L(n-1) + L(n-2) with base cases L(0) = 3 and L(1) = 2.
 * It uses recursive method to compute the sequence inefficiently.
 */

public class asn1_a {
    // Recursive method to compute the Fibonacci-like sequence
    public long fib(long n) {
        // Base case: L(0) = 3
        if (n == 0) {
            return 3;
        }
        // Base case: L(1) = 2
        if (n == 1) {
            return 2;
        }
        // Recursive case: L(n) = L(n-1) + L(n-2)
        return fib(n - 1) + fib(n - 2);
    }

    public static void main(String[] args) {
        // Create an instance of the asn1_a class
        asn1_a testing = new asn1_a();
        // Loop to compute and print L(i*5) for i from 0 to 10
        for (int i = 0; i <= 10; i++) {
            System.out.println("L(" + (i * 5) + ") = " + testing.fib(i * 5));
        }
    }
}





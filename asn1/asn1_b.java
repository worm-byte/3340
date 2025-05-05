/*
 * Rosaline Scully
 * January 25, 2025
 * Student ID: 250966670
 * 
 * This program computes the Fibonacci-like sequence L(n) = L(n-1) + L(n-2) with base cases L(0) = 3 and L(1) = 2.
 * It uses matrix exponentiation to compute the sequence efficiently.
 */

public class asn1_b {
    
    // Method to compute the Fibonacci-like sequence using matrix exponentiation
    public static LargeInteger compute(long n) {
        // Base case: L(0) = 3
        if (n == 0) {
            return new LargeInteger("3");
        }
        // Base case: L(1) = 2
        if (n == 1) {
            return new LargeInteger("2");
        }
        // Matrix exponentiation to compute L(n)
        LargeInteger[][] result = matrixPower(new LargeInteger[][]{
            {new LargeInteger("1"), new LargeInteger("1")}, 
            {new LargeInteger("1"), new LargeInteger("0")}
        }, n - 1);
        // Compute L(n) = result[0][0] * 2 + result[0][1] * 3
        return result[0][0].multiply(new LargeInteger("2")).add(result[0][1].multiply(new LargeInteger("3")));
    }

    // Method to perform matrix exponentiation
    private static LargeInteger[][] matrixPower(LargeInteger[][] base, long exp) {
        // Base case: exponent is 0, return identity matrix
        if (exp == 0) {
            return new LargeInteger[][]{
                {new LargeInteger("1"), new LargeInteger("0")}, 
                {new LargeInteger("0"), new LargeInteger("1")}
            };
        }
        // Base case: exponent is 1, return base matrix
        if (exp == 1) {
            return base;
        }
        // Recursive case: compute half power
        LargeInteger[][] halfPower = matrixPower(base, exp / 2);
        // Square the half power to get full power
        LargeInteger[][] fullPower = multiplyMatrices(halfPower, halfPower);
        // If exponent is odd, multiply by base matrix once more
        if (exp % 2 != 0) {
            fullPower = multiplyMatrices(fullPower, base);
        }
        return fullPower;
    }

    // Method to multiply two 2x2 matrices
    private static LargeInteger[][] multiplyMatrices(LargeInteger[][] a, LargeInteger[][] b) {
        LargeInteger[][] result = new LargeInteger[2][2];
        result[0][0] = a[0][0].multiply(b[0][0]).add(a[0][1].multiply(b[1][0]));
        result[0][1] = a[0][0].multiply(b[0][1]).add(a[0][1].multiply(b[1][1]));
        result[1][0] = a[1][0].multiply(b[0][0]).add(a[1][1].multiply(b[1][0]));
        result[1][1] = a[1][0].multiply(b[0][1]).add(a[1][1].multiply(b[1][1]));
        return result;
    }

    public static void main(String[] args) {
        // Loop to compute and print L(i*20) for i from 0 to 25
        for (int i = 0; i <= 25; i++) {
            System.out.println("L(" + (i * 20) + ") = " + compute(i * 20));
        }
    }
}

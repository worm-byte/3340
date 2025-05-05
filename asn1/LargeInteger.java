/*
 * Rosaline Scully
 * January 25, 2025
 * Student ID: 250966670
 * 
 * LargeInteger class to represent large integers and perform arithmetic operations on them.
 * The class provides methods for addition and multiplication of large integers.
 */
public class LargeInteger {
    private final int[] digits; // Array to store the digits of the large integer
    private final int sign; // Sign of the large integer, 1 for positive, -1 for negative

    // Constructor to create a LargeInteger from a string
    public LargeInteger(String value) {
        this.sign = value.startsWith("-") ? -1 : 1; // Determine the sign
        this.digits = new int[value.length() - (value.startsWith("-") ? 1 : 0)]; // Initialize the digits array

        // Fill the digits array with the numeric values of the characters in the string
        for (int i = 0; i < this.digits.length; i++) {
            this.digits[i] = Character.getNumericValue(value.charAt(value.length() - 1 - i));
        }
    }

    // Constructor to create a LargeInteger from an array of digits and a sign
    public LargeInteger(int[] digits, int sign) {
        this.digits = digits;
        this.sign = sign;
    }

    // Method to add two LargeIntegers
    public LargeInteger add(LargeInteger other) { //Assume the signs are the same
        int[] result = new int[Math.max(this.digits.length, other.digits.length) + 1]; // Result array
        int carry = 0; // Carry for addition
        for (int i = 0; i < result.length; i++) {
            int sum = carry;
            if (i < this.digits.length) {
                sum += this.digits[i];
            }
            if (i < other.digits.length) {
                sum += other.digits[i];
            }
            result[i] = sum % 10; // Store the last digit of the sum
            carry = sum / 10; // Update the carry
        }
        return new LargeInteger(result, this.sign); // Return the result as a new LargeInteger
    }

    // Method to multiply two LargeIntegers
    public LargeInteger multiply(LargeInteger other) {
        int[] result = new int[this.digits.length + other.digits.length]; // Result array
        for (int i = 0; i < this.digits.length; i++) {
            int carry = 0; // Carry for multiplication
            for (int j = 0; j < other.digits.length; j++) {
                int product = this.digits[i] * other.digits[j] + result[i + j] + carry;
                result[i + j] = product % 10; // Store the last digit of the product
                carry = product / 10; // Update the carry
            }
            result[i + other.digits.length] = carry; // Store the final carry
        }
        return new LargeInteger(result, this.sign * other.sign); // Return the result as a new LargeInteger
    }

    // Method to convert the LargeInteger to a string
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (sign == -1) {
            sb.append('-'); // Append the negative sign if the number is negative
        }
        boolean leadingZero = true;
        for (int i = digits.length - 1; i >= 0; i--) {
            if (leadingZero && digits[i] == 0) {
                continue; // Skip leading zeros
            }
            leadingZero = false;
            sb.append(digits[i]); // Append the digit
        }
        return leadingZero ? "0" : sb.toString(); // Return "0" if the number is zero
    }


    // Method to compare two LargeIntegers
    public int compareTo(LargeInteger other) {
        if (this.sign != other.sign) {
            return this.sign; // Compare based on sign
        }

        if (this.digits.length != other.digits.length) {
            return this.sign * Integer.compare(this.digits.length, other.digits.length); // Compare based on length
        }

        for (int i = this.digits.length - 1; i >= 0; i--) {
            if (this.digits[i] != other.digits[i]) {
                return this.sign * Integer.compare(this.digits[i], other.digits[i]); // Compare based on digits
            }
        }
        return 0; // Numbers are equal
    }
}

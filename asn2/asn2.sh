#!/bin/bash

# Compile the Java program (if not already compiled)
javac FindConnected.java

# Check if compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed. Please check your Java code."
    exit 1
fi

# Create a temporary file to store the input
temp_file=$(mktemp)

# Read input from stdin and save it to the temporary file
cat > "$temp_file"

# Run the Java program with the temporary file as input
echo "Running the program..."
java FindConnected "$temp_file"

# Clean up the temporary file
rm "$temp_file"

#To get my code to run with command asn2 or ./asn2 a soft link to the bin needs to be made. I used the following command but it may be different depending on your environment setup:ln -s ~/3340/asn2/asn2.sh ~/bin/asn2. If that doesn't work, please use ./asn2.sh infile.txt

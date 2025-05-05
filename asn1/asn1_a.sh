#!/bin/bash

# Compile Java source files
javac asn1_a.java

# Run the program
java asn1_a

#To get my code to run with command asn1_a or ./asn1_a a soft link to the bin needs to be made. I used the following command but it may be different depending on your environment setup:ln -s ~/3340/asn1/asn1_a.sh ~/bin/asn1_a. If that doesn't work, please use ./asn1_a.sh


#!/bin/bash

javac asn1_b.java LargeInteger.java

java asn1_b

#To get my code to run with command asn1_a or ./asn1_a a soft link to the bin needs to be made. I used the following command but it may be different depending on your environment setup:ln -s ~/3340/asn1/asn1_b.sh ~/bin/asn1_b. If this doesn't work, please use ./asn1_b.sh

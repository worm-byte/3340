#!/bin/bash

# Create a temporary file to store the input
temp_file=$(mktemp)

# Read input from stdin and save it to the temporary file
cat > "$temp_file"

# Run the Python program with the temporary file as input
python3 asn3.py "$temp_file"

# Clean up the temporary file
rm "$temp_file"

# Note: To make this script executable as "asn3", use:
# chmod +x asn3.sh
# ln -s ~/3340/asn3/asn3.sh ~/bin/asn3

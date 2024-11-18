#!/bin/bash

# Define the paths to the template and the final header file
TEMPLATE_FILE="header_template.txt"
HEADER_FILE="header.txt"

# Project-specific information
PROJECT_NAME="StarshipOS"
YEAR=$(date +%Y)
USER="Starship Operating System (R. A. James)"

# Generate the ASCII art header using figlet
echo "Generating ASCII art header..."
ASCII_ART=$(figlet "$PROJECT_NAME")
echo "ASCII art header generated."

# Combine the ASCII art with the template
echo "Combining ASCII art with the license template..."
{
  echo "$ASCII_ART"
  sed "s/\${YEAR}/$YEAR/g; s/\${USER}/$USER/g" "$TEMPLATE_FILE"
} > "$HEADER_FILE"
echo "Combined header created."

# Function to check if the header is already present in the file
check_header_exists() {
  local file=$1
  grep -qF "$ASCII_ART" "$file"
}

# Start the script and process files recursively
echo "Starting recursive file processing..."

# Find all relevant files and prepend the header if not present
find . -type f \( -name "*.sh" -o -name "*.java" -o -name "*.py" \) -print0 | while IFS= read -r -d '' file; do
  echo "Processing file: $file"
  if check_header_exists "$file"; then
    echo "Header already exists in $file, skipping."
  else
    echo "Adding header to $file..."
    { cat "$HEADER_FILE"; echo; cat "$file"; } > "${file}.new" && mv "${file}.new" "$file"
    echo "Header added to $file."
  fi
done

echo "File processing complete."

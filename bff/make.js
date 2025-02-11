const fs = require('fs');
const path = require('path');

// Input file path (replace with your file path)
const inputFilePath = 'input.txt'; // Replace with your file
const outputDirectory = 'src'; // Directory to save the generated files

// Function to ensure the output directory and any necessary subdirectories exist
const createDirectoryIfNeeded = (filePath) => {
  const dirName = path.dirname(filePath);
  if (!fs.existsSync(dirName)) {
    fs.mkdirSync(dirName, { recursive: true }); // Create the directory recursively
  }
};

// Create the output directory if it doesn't exist
if (!fs.existsSync(outputDirectory)) {
  fs.mkdirSync(outputDirectory);
}

fs.readFile(inputFilePath, 'utf8', (err, data) => {
  if (err) {
    console.error('Error reading file:', err);
    return;
  }

  // Split the input data by "// src/"
  const splitData = data.split('// src/');

  splitData.forEach(item => {
    // Split each item by ".ts"
    const parts = item.split('.ts');
    
    if (parts.length < 2) return; // Skip if no valid split

    // Get the filename and content
    const fileName = parts[0].trim(); // Trim to remove extra spaces
    const fileContent = parts.slice(1).join('.ts').trim(); // Join if there's more than one part after .ts

    if (fileName && fileContent) {
      // Generate the output file path
      const outputFilePath = path.join(outputDirectory, `${fileName}.ts`);

      // Ensure the directory exists before writing the file
      createDirectoryIfNeeded(outputFilePath);

      // Write the content to the .ts file
      fs.writeFile(outputFilePath, fileContent, 'utf8', (err) => {
        if (err) {
          console.error(`Error writing file ${fileName}.ts:`, err);
        } else {
          console.log(`File ${fileName}.ts created successfully at ${outputFilePath}`);
        }
      });
    }
  });
});

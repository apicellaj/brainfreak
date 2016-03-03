# brainfreak
A GUI interpreter for the esoteric language [BF](https://esolangs.org/wiki/Brainfuck)

### Features
* 30,000 8-bit memory cells
* Optional memory wrap
* Enhanced mode
  - `;` Input a number and store it in the cell at the pointer 
  - `:` Output a number and store it in the cell at the pointer
* ASCII cheat sheet for quick look up
* Programs can be run with `SHIFT+ENTER` from the text window
* Debug information

### Building
* Download source zip file and unzip
* Run `./gradlew build` from within the unzipped folder
* Navigate to `/build/libs/` to find the JAR
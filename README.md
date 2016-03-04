# brainfreak
A GUI interpreter for the esoteric language [BF](https://esolangs.org/wiki/Brainfuck)

### Features
* 30,000 8-bit memory cells
* Optional memory wrap
* Enhanced mode
  - `;` Input a number and store it in the cell at the pointer 
  - `:` Output a number and store it in the cell at the pointer
* ASCII look up table
* Sample programs
* Programs can also be run with `SHIFT+ENTER` from the text window
* Debug information

##Usage

### Building
brainfreak uses [Gradle](gradle.org) for its build system.
* Download source zip file and navigate to unzipped folder.
* Run `./gradlew build` on UNIX or `gradlew.bat` on Windows.
* Navigate to `/build/libs/` to locate and run the interpreter JAR.

### Running
In order to run brainfreak directly from source, simply use `./gradlew run`
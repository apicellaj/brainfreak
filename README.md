# brainfreak
A GUI interpreter for the esoteric language [BF](https://esolangs.org/wiki/Brainfuck).

### Features
* 8-bit memory cells
* Customizable cell count
* Optional memory wrap
* Sample programs
* ASCII lookup table
* Memory dump
* Debug information
* Enhanced mode
  - `;` Input a number and store it in the cell at the pointer 
  - `:` Output a number and store it in the cell at the pointer

Programs can also be run from the text area with `SHIFT+ENTER`
ASCII lookup table and memory dump windows can be closed with `ESC`

##Usage

### Building
brainfreak uses [Gradle](gradle.org) to build.
* Download source zip and navigate to the project folder.
* Run `gradlew.bat` on Windows or type `./gradlew build` from the command line in Linux / OSX.
* The brainfreak JAR can be located in `/build/libs/`.

### Running
In order to run brainfreak directly from source, simply use `./gradlew run` from the command line.
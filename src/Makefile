all: CSVReader.java Tile.java WordRun.java Run.java
	javac -g xboard.java -d ../bin

CSVReader.java:
	javac -g CSVReader.java -d ../bin

Tile.java:
	javac -g Tile.java -d ../bin

WordRun.java:
	javac -g WordRun.java ../bin

clean:
	rm ../bin/*.class

test:
	java -cp ../bin Test
	
debug: CSVReader.java Tile.java WordRun.java Run.java
	javac -g xboard.java -d ../bin
	
drun:
	jdb -sourcepath ../bin Run
	
Run.java:
	javac -g Run.java -d ../bin
	
args:
	java Run 20 false ../csv/dictionary.csv 5

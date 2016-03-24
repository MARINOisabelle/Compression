JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

default: 
	$(JC) *.java

clean:
	$(RM) *.class
 
run:
	java Compression

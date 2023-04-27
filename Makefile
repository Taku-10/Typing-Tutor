JAVAC=/usr/bin/javac
.SUFFEXES: /java .class
SRCDIR=src
BINDIR=bin

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES=WordDictionary.class Score.class ScoreUpdater.class\
		FallingWord.class CatchWord.class GamePanel.class\
		Intersection.class HungryWordMover.class\
		WordMover.class TypingTutorApp.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)
clean:
	rm $(BINDIR)/*.class
run:	$(CLASS_FILES)
	java -cp bin TypingTutorApp
JCC = javac

JFLAGS = -g

SRC_PATH_MODELS = src/main/model/*.java
TEST_PATH = src/test/java/CatanTests.java
TEST_EXEC = src.test.java.CatanTests

CLASS_PATH_MODELS = src/main/model/*.class
CLASS_PATH_TESTS = src/test/java/*.class

default:
	$(JCC) $(JFLAGS) $(SRC_PATH_MODELS)
	$(JCC) $(JFLAGS) $(TEST_PATH)

clean:
	rm $(CLASS_PATH_MODELS) 
	rm $(CLASS_PATH_TESTS)

test:
	make
	java $(TEST_EXEC) 
	make clean

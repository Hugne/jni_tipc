.PHONY: all 
all:    lib java_src

java_src:
	find -name "*.java" > javasrc.txt
	javac @javasrc.txt -d ./java/bin/
	rm javasrc.txt

lib:
	make -C jni_lib

.PHONY:
clean:
	rm -f ./java/bin/*.class
	make -C jni_lib clean

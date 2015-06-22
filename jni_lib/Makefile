all:
	gcc -shared -fPIC -c -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux/ \
		tipc.c
	gcc -o libjnitipc.so -shared tipc.o

clean:
	rm -rf *.o *.so

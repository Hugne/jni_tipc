all:
	gcc -shared -fPIC -c -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux/ \
		tipcdatagramsocket.c
	gcc -o libjnitipc.so -shared tipcdatagramsocket.o 

clean:
	rm -rf *.o *.so

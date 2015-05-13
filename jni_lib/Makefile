all:
	gcc -shared -fPIC -c -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux/ \
		tipcdatagramsocket.c tipcstreamsocket.c common.c
	gcc -o libjnitipc.so -shared tipcdatagramsocket.o tipcstreamsocket.o common.o

clean:
	rm -rf *.o *.so

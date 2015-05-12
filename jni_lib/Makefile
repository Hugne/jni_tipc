all:
	gcc -shared -fPIC -c -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux/ \
		hello.c tipcdatagramsocket.c
	gcc -o libhello.so -shared  hello.o tipcdatagramsocket.o

clean:
	rm -rf *.o *.so

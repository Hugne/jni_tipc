# jni_tipc

JNI bindings for TIPC, to allow apps to use the TIPC IPC mechanism
in systems where it's supported (i.e Linux).
make
followed by
./test.sh
should give you some output saying JNI bindings loaded and a "hello world" string.

The example service just publishes a TIPC name based on an ASCII service name.
The service instance ID's are generated using String.hashcode() for the given name.
(High risk of collision and very unsafe)


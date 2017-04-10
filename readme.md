Multi-Threaded web server with thread pooling

I have refernced following websites for the code.

    1. http://tutorials.jenkov.com/java-multithreaded-servers/thread-pooled-server.html
    2. http://stackoverflow.com/questions/12588476/multithreading-socket-communication-client-server
    3. http://stackoverflow.com/questions/23962359/thread-pool-in-a-web-server-in-java
    4. http://mrbool.com/working-with-java-executor-framework-in-multithreaded-application/27560

Some things to know :

    1. Port the server is running on : 8090
    2. Directory from which files are being served : www_root
    3. Files present in my directory which can be used to verify : Object.html, hello.world, about
    4. URL : a) localhost:8090/Object.html b) localhost:8090/hello.world c) localhost:8090/about

Note : I have tried implementing the optional extension Keep-Alive behaviour but I need to enhance it more if given some time .

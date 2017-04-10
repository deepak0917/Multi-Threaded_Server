Multi-Threaded web server with thread pooling

I have refernced following websites for the code.

    http://tutorials.jenkov.com/java-multithreaded-servers/thread-pooled-server.html
    http://stackoverflow.com/questions/12588476/multithreading-socket-communication-client-server
    http://stackoverflow.com/questions/23962359/thread-pool-in-a-web-server-in-java
    http://mrbool.com/working-with-java-executor-framework-in-multithreaded-application/27560

Some things to know :

    Port the server is running on : 8090
    Directory from which files are being served : www_root
    Files present in my directory which can be used to verify : Object.html, hello.world, about
    URL : a) localhost:8090/Object.html b) localhost:8090/hello.world c) localhost:8090/about

Note : I have tried implementing Keep-Alive behaviour but I need to enhance it more .

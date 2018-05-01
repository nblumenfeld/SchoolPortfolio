# Web Server

This is a web server that will display appropriate pages based on a users request.
No extra credit was done, so these files only relate to the basic requirements of the project.
Included with the project are:

* config.xml
* Configuration.java
* ConfigurationException.java
* Connection.java
* WebServer.java
* HttpHeader.java
* HttpMirror.java
* SimpleServer.java
* Test.java

Some of these files are leftover from examples given to us for the assignment, so the files I was working with primarily were:

* config.xml
* Configuration.java
* ConfigurationException.java
* Connection.java
* WebServer.java


## Getting Started

To launch the webserver, compile WebServer.java and run it using the terminal command:

"java WebServer config.xml"

where config.xml is set up to route to files on your machine.

The defaultDocument should route to the specific location of the index.html file, the 404Document should route to the speficic location of the 404.html document and then the documentRoute will be the route to the root folder of the server.

Any file besides the index.html and 404.html documents will be dynamically found using the documentRoot if they exist. If there is a file requested that doesn't exist, the server will return the 404.html document.

## Running the server

To access the server, open a web browser and enter:

localhost:8080

As you test different parts of the server, you will be able to navigate to:

* index.html
* 404.html
* and any page that contains is of type .html, .gif, .jpeg, .png, and .txt


/ and /index.html will route to the index.html file you specified in the config.xml file and 404.html will be shown if a route is entered that is invalid. Every other file on the server can be accessed by appending the route path after the first / after localhost:8080



## Authors

Noah Blumenfeld 
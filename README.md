## Prerequisites
To use this app, you should have Grails, Node, NPM, and the Angular CLI installed.

* [Grails 3.2](http://docs.grails.org/latest/guide/single.html#gettingStarted)

* [Node 5 && NPM 3](https://docs.npmjs.com/getting-started/installing-node)

* [Angular CLI](https://github.com/angular/angular-cli#installation)

## Running The App

To execute the server side application only, you can execute the bootRun task in the server project:

<pre>
./gradlew server:bootRun
</pre>

The same can be done for the client application:

<pre>
./gradlew client:bootRun
</pre>

To execute both, you must do so in parallel:

<pre>
./gradlew bootRun --parallel
</pre>

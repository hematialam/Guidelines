Guidelines
A1C
==========

Install
-------
This project has several important dependencies:

- A Java 8 SDK. For most Linux distributions you can find this in your software repository. Ubuntu calls it "openjdk-8-jdk" but it is only in utopic or later. Fedora calls it "java-1.8.0-openjdk". For everyone else (Mac, Windows, etc) you will need to download it from the [Oracle website](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
- The [shift-reduce parser models](http://nlp.stanford.edu/software/stanford-srparser-2014-10-23-models.jar). Add them to your eclipse Build Path, or to your classpath as needed. They don't distribute them via the usual methods (e.g. Maven or Gradle) because they are quite sizable.
- Gradle: This is how we install the rest of the dependencies.

Development
-----------
The code is in a very early stage of development. Things will change quickly. Email one of the authors if you want to know more about what we are doing.

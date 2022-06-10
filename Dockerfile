FROM java:latest

COPY Database.java /
COPY ATM.java /
COPY UnitTest.java /
COPY UserInterface.java /
COPY User.java /

WORKDIR /

RUN javac UserInterface.java
ENTRYPOINT [ "java", "UserInterface" ]

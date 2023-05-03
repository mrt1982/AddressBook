# gumtree-addressbook

## Description
Your application needs to read the attached AddressBook file and answer the following questions:

* How many males are in the address book?
* Who is the oldest person in the address book?
* How many days older is Bill than Paul?

## Run
You will need maven and java 17 in order to run the command line interactive application. The application was written in a TDD manner with Domain Driven Design, therefore it is modularised and follows a layered package structure and if needs be, can easily integrate spring-boot to provide a rest-api.
* The main class is [AddressBookLauncher](src/main/java/co/uk/gumtree/address/application/AddressBookLauncher.java)
* mvn test exec:java
  * The interactive application will give you 4 options
    * Do you want to exit? please enter 1 
    * How many males are in the address book? please enter 2 
    * Who is the oldest person in the address book? please enter 3
    * How many days older is Person 1 than Person 2? please enter 4

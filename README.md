# Portfolio project IDATT2003
A JavaFX-based desktop board game application, developed as part of the **IDATT2003 – Programming 2** course at NTNU (Spring 2025), by Aminda Oleane Lunde and Ingrid Neverås Opheim.

## Project description

This project focuses on designing and building a **modular, extensible board game** with an interactive GUI. The application is implemented in **Java** using **JavaFX**, and adheres to software engineering principles like **MVC**, **Factory**, and **Observer** design patterns.

The main goal was to build a fully playable version of *Snakes and Ladders* (stigespill), featuring:
- Player turns
- Dice rolling
- Special tile actions
- Win condition detection
- File handling with CSV (players) and JSON (boards)

All logic is unit-tested and structured for clarity and maintainability.


## Project structure

fyll inn!!


## Link to repository

The GitHub repository for this project can be found at the following link: https://github.com/amindaol/mappevurdering-IDATT2003

## Running the application

To run the project, you first have to clone the repository to your local machine. This can be done by using the command "git clone <repository-link>". Once cloned, open the project in an IDE, such as intelliJ IDEA or Visual Studio Code. Use Maven to run the JavaFX application:

mvn clean javafx:run

Ensure that Maven and Java are properly configured on your system.

## How to run the tests

Tests are written with JUnit 5. You can run them with:


mvn test

Or via your IDE’s built-in test runner under src/test/java.

## Features and highlights

- JavaFX GUI with dynamic rendering
- MVC architecture for clean separation
- JSON and CSV file handling
- Factory pattern for board setup
- Observer pattern for model-view updates
- Domain-specific exceptions
- Fully testable with JUnit

## Technologies used

| Tool                 | Version     | Usage                                      |
|----------------------|-------------|--------------------------------------------|
| IntelliJ IDEA        | 2025.1.1.1  | Coding and debugging                       |
| Git and GitHub       | 2.49.0      | Version control and collaboration          |
| Java                 | 21          | Programming language                       |
| JavaFX               | 21          | GUI development                            |
| JavaFX Maven plugin  | 0.0.8       | Run JavaFX via Maven                       |
| Maven                | 21          | Build tool / dependency management         |
| JUnit                | 5.10.2      | Unit testing                               |
| Gson                 | 2.11        | JSON parsing                               |
| Figma                | -           | Wireframes and prototyping (GUI design)    |
| ChatGPT              | -           | Learning support and debugging assistance  |
| GitHub Copilot       | -           | Code suggestions and refactoring help      |



## References

1.	GeeksforGeeks. (2023, 31. oktober). Single Responsibility in SOLID Design Principle. Hentet 04.05.25 fra https://www.geeksforgeeks.org/single-responsibility-in-solid-design-principle/
2.	GeeksforGeeks. (2023, 15. mars). Single Responsibility Principle in Java with Examples. Hentet 04.05.25 fra https://www.geeksforgeeks.org/single-responsibility-principle-in-java-with-examples/
3.	GeeksforGeeks. (2025, 24. april). Software Engineering – Coupling and Cohesion. Hentet 04.05.25 fra https://www.geeksforgeeks.org/software-engineering-coupling-and-cohesion/
4.	Baeldung. (2025, 26. mars). Cohesion vs Coupling in Software Design. Hentet 04.05.25 fra https://www.baeldung.com/cs/cohesion-vs-coupling
5.	Refactoring.Guru. (n.d.-a). Factory Method Design Pattern. Hentet 04.05.25 fra https://refactoring.guru/design-patterns/factory-method
6.	Refactoring.Guru. (n.d.-b). Observer Design Pattern. Hentet 04.05.25 fra https://refactoring.guru/design-patterns/observer
7.	W3Schools. (n.d.-a). Java Inheritance. Hentet 04.05.25 fra https://www.w3schools.com/java/java_inheritance.asp
8.	W3Schools. (n.d.-b). Java Polymorphism. Hentet 04.05.25 fra https://www.w3schools.com/java/java_polymorphism.asp
9.	Medium / Backticks & Tildes. (2020, 18. mai). The S.O.L.I.D. Principles in Pictures. Hentet 21.05.25 fra https://medium.com/backticks-tildes/the-s-o-l-i-d-principles-in-pictures-b34ce2f1e898
10.	Visual Paradigm. (n.d.). What is Model View Controller (MVC)?. Hentet 21.05.25 fra https://www.visual-paradigm.com/guide/uml-unified-modeling-language/what-is-model-view-control-mvc/
11.	Nalexn. (n.d.). Separation of Concerns. Hentet 21.05.25 fra https://nalexn.github.io/separation-of-concerns/
12.	Chee, H.C. (n.d.). Inheritance and Polymorphism in Java. Nanyang Technological University. Hentet 21.05.25 fra https://www3.ntu.edu.sg/home/ehchua/programming/java/J3b_OOPInheritancePolymorphism.html

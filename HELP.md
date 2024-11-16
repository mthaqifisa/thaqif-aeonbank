# Task
Create a RESTful API that manages a simple library system.

### The API should allow API user to perform the following actions:
1. Register a new borrower to the library.
2. Register a new book to the library.
3. Get a list of all books in the library.

### The API should allow API user to perform the following actions on behalf of a borrower:
1. Borrow a book with a particular book id (refer Book in Data Models).
2. Return a borrowed book.

# Data Models
Borrower should have a unique id, a name and an email address
Book should have a unique id, an ISBN number, a title, and an author
### ISBN number uniquely identifies a book in the following way:
- 2 books with the same title and same author but different ISBN numbers are considered
  as different books
- 2 books with the same ISBN numbers must have the same title and same author
  Multiple copies of books with same ISBN number are allowed in the system

# Requirements
1. Use a programming language and framework of your choice to create the project. 
   1. Use of Java 17 and Spring Boot framework is an added bonus
2. Use a package manager to manage project dependencies.
3. Implement proper data validation and error handling.
4. Use a database to store borrower and book data.
   1. Justify your choice of database
5. Implement REST API endpoints for each action mentioned above.
6. Multiple books with the same ISBN number should be registered as books with different
   ids.
7. Ensure that no more than one member is borrowing the same book (same book id) at a
   time.
8. Provide clear documentation for how to use your API
9. Provide documentation of all your assumptions for any requirements that are not
   explicitly stated in this task

# Nice to have
1. Demonstrate clean code practices.
2. Include unit tests to verify the functionality of your code.
3. Demonstrate use of containerization and CI/CD tools in declarative manner.
4. Demonstrate conformance (up to certain extent) to 12 Factor Application https://12factor.net/ .
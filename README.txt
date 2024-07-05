## Student Information System

### Overview
The Student Information System is a Java desktop application designed to manage student records. It allows users to add, update, delete, and retrieve student information using a graphical user interface (GUI).

### Features
- **Add Student**: Enter student details such as ID, first name, last name, date of birth, and address to add a new student.
- **Update Student**: Modify existing student information by entering the student's ID and updating the necessary fields.
- **Delete Student**: Remove a student from the system by entering their ID.
- **Load Students**: Load student information from a file stored in the current directory.
- **Save to File**: Save all student records to a file in the current directory.
- **Clear Fields**: Clear input fields on the GUI to enter new student information.
- **Get Info**: Retrieve and display student information based on their ID.

### Technologies Used
- Java
- Java Swing for GUI
- File I/O for data storage
- ExecutorService for asynchronous file operations

### Concepts Utilized
- **Serialization**: Objects of the `Student` class are serialized to save and load student information from files.
- **Object-Oriented Programming (OOP)**: Classes like `Student`, `School`, and `FileManager` are used to encapsulate data and operations.

### How to Use
1. Clone the repository: `git clone https://github.com/Akashkumar231/student_information_system`
2. Open the project in IntelliJ IDEA or any Java IDE.
3. Compile and run `StudentGui.java`.
4. Use the GUI buttons to perform operations on student data.
5. Click on "Save to File" to store student records locally.
6. Click on "Load Students" to retrieve previously saved student data.

### Contributing
Contributions to enhance the GUI design or add new features are welcome! Feel free to fork the repository and submit pull requests.




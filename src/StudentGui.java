import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StudentGui extends JFrame {
    private School school;
    private FileManager fileManager;
    private ExecutorService executor;

    // GUI components
    private JTextField idField, firstNameField, lastNameField, dobField, addressField;
    private JTextArea outputArea;

    public StudentGui() {
        super("Student Information System");

        school = new School();
        fileManager = new FileManager();
        executor = Executors.newSingleThreadExecutor(); // Create a single thread executor

        initializeComponents();

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeComponents() {
        // Panel to hold form components
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.add(new JLabel("ID"));
        idField = new JTextField();
        formPanel.add(idField);
        formPanel.add(new JLabel("First Name"));
        firstNameField = new JTextField();
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name"));
        lastNameField = new JTextField();
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Date of Birth (YYYY-MM-DD)"));
        dobField = new JTextField();
        formPanel.add(dobField);
        formPanel.add(new JLabel("Address"));
        addressField = new JTextField();
        formPanel.add(addressField);

        // Panel to hold buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });
        buttonPanel.add(addButton);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });
        buttonPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });
        buttonPanel.add(deleteButton);

        JButton loadButton = new JButton("Load Students");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadStudentsFromFile(); // Load students asynchronously
            }
        });
        buttonPanel.add(loadButton);

        JButton saveToFileButton = new JButton("Save to File");
        saveToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveStudentsToFile(); // Save students asynchronously
            }
        });
        buttonPanel.add(saveToFileButton);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearOutputArea();
            }
        });
        buttonPanel.add(clearButton);

        JButton getInfoButton = new JButton("Get Info");
        getInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getStudentInfo();
            }
        });
        buttonPanel.add(getInfoButton);

        // Panel to hold output area
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(outputPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void addStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String dob = dobField.getText();
            String address = addressField.getText();

            Student student = new Student(id, firstName, lastName, dob, address);
            school.addStudent(student);
            displayStudents();
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID. Please enter a numeric value.");
        }
    }

    private void updateStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String dob = dobField.getText();
            String address = addressField.getText();

            Student updatedStudent = new Student(id, firstName, lastName, dob, address);
            school.updateStudent(id, updatedStudent);
            displayStudents();
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID. Please enter a numeric value.");
        }
    }

    private void deleteStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            school.removeStudent(id);
            displayStudents();
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID. Please enter a numeric value.");
        }
    }

    private void loadStudentsFromFile() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Student> students = fileManager.loadStudentsFromFile();
                    if (students != null && !students.isEmpty()) {
                        // Clear current students and update with loaded data
                        school.getAllStudents().clear();
                        school.getAllStudents().addAll(students);

                        // Update GUI on EDT
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                displayStudents(); // Update GUI with loaded students
                            }
                        });
                    } else {
                        // Handle case where no students are loaded
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                outputArea.setText("No students found in the file.");
                            }
                        });
                    }
                } catch (Exception ex) {
                    // Handle exceptions during file loading
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            outputArea.setText("Error loading students: " + ex.getMessage());
                        }
                    });
                    ex.printStackTrace(); // Print stack trace for debugging
                }
            }
        });
    }

    private void saveStudentsToFile() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                fileManager.saveStudentsToFile(school.getAllStudents());
            }
        });
    }

    private void getStudentInfo() {
        try {
            int id = Integer.parseInt(idField.getText());
            Student student = school.getStudentById(id);
            if (student != null) {
                outputArea.setText(student.toString());
            } else {
                outputArea.setText("Student with ID " + id + " not found.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID. Please enter a numeric value.");
        }
    }

    private void displayStudents() {
        outputArea.setText("");
        List<Student> students = school.getAllStudents();
        for (Student student : students) {
            outputArea.append(student.toString() + "\n");
        }
    }

    private void clearFields() {
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        dobField.setText("");
        addressField.setText("");
    }

    private void clearOutputArea() {
        outputArea.setText("");
    }


}

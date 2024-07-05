import java.util.ArrayList;
import java.util.List;

public class School {
    private List<Student> allStudents;

    public School() {
        this.allStudents = new ArrayList<>();
    }

    public void addStudent(Student student) {
        allStudents.add(student);
    }

    public void updateStudent(int id, Student updatedStudent) {
        for (int i = 0; i < allStudents.size(); i++) {
            if (allStudents.get(i).getId() == id) {
                allStudents.set(i, updatedStudent);
                return;
            }
        }
        System.out.println("Student with ID " + id + " not found.");
    }

    public void removeStudent(int id) {
        allStudents.removeIf(student -> student.getId() == id);
    }

    public List<Student> getAllStudents() {
        return allStudents;
    }

    public Student getStudentById(int id) {
        for (Student student : allStudents) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null; // Student not found
    }
}

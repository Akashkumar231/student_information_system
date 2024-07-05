import java.io.Serial;
import java.io.Serializable;

public class Attendance implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String date;
    private String status;

    public Attendance(String date, String status) {
        this.date = date;
        this.status = status;
    }

    // Getters and setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "date='" + date + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

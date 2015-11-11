import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient {
    private long idNumber;
    private List<GaitHealth> gaitHealth = new ArrayList<GaitHealth>();

    public Patient(long idNumber, List<GaitHealth> gaitHealth) {
        super();

        this.idNumber = idNumber;
        this.gaitHealth = gaitHealth;
    }

    public long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(long idNumber) {
        this.idNumber = idNumber;
    }

    public List<GaitHealth> getGaitHealth() {
        return gaitHealth;
    }

    public void setGaitHealth(List<GaitHealth> gaitHealth) {
        this.gaitHealth = gaitHealth;
    }
}

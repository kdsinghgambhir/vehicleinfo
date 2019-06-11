package vehicletracker.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Alerting
{
    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    private String alertID;

    private  String PRIORITY;
    public Alerting()
    {
        this.alertID = UUID.randomUUID().toString();
    }

    public String getAlertID() {
        return alertID;
    }

    public void setAlertID(String alertID) {
        this.alertID = alertID;
    }

    public String getPRIORITY() {
        return PRIORITY;
    }

    public void setPRIORITY(String PRIORITY) {
        this.PRIORITY = PRIORITY;
    }
}

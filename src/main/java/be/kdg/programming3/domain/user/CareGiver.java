package be.kdg.programming3.domain.user;

public class CareGiver {
    private int caregiver_id;
    private String caregiver_name;
    private String company;
    private String address;

    public CareGiver(int caregiver_id, String caregiver_name, String company, String address) {
        this.caregiver_id = caregiver_id;
        this.caregiver_name = caregiver_name;
        this.company = company;
        this.address = address;
    }

    public CareGiver() {
        //implemented later
    }

    public int getCaregiver_id() {
        return caregiver_id;
    }

    public void setCaregiver_id(int caregiver_id) {
        this.caregiver_id = caregiver_id;
    }

    public String getCaregiver_name() {
        return caregiver_name;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "CareGiver{" +
                "caregiver_id=" + caregiver_id +
                ", caregiver_name='" + caregiver_name + '\'' +
                ", company='" + company + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

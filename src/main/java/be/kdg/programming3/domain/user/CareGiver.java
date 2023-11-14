package be.kdg.programming3.domain.user;

import java.util.ArrayList;
import java.util.List;

public class CareGiver {
    private int caregiver_id;
    private String caregiver_name;
    private String company;
    private String address;
    private List<Customer> customers;

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

public List<Customer> getCustomers() {
        return customers;
    }

    public void setCaregiver_name(String caregiver_name) {
        this.caregiver_name = caregiver_name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
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

    public void addCustomer(Customer customer) {
        if (customers == null) customers = new ArrayList<>();
        customers.add(customer);
    }
}

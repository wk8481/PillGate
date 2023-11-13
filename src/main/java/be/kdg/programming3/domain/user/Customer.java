package be.kdg.programming3.domain.user;

public class Customer {
    private int customer_id;
    private String customer_name;
    private int age;
    private String email;
    private boolean hasCareGiver;
    private CareGiver careGiver;



    public Customer(int customer_id, String customer_name, int age, String email, boolean hasCareGiver) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.age = age;
        this.email = email;
        this.hasCareGiver = hasCareGiver;
    }

    public Customer() {
        //implemented later
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public boolean isHasCareGiver() {
        return hasCareGiver;
    }

    public CareGiver getCareGiver() {
        return careGiver;
    }



    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", customer_name='" + customer_name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", hasCareGiver=" + hasCareGiver +
                '}';
    }


    public boolean addCaregiver(CareGiver careGiver) {
        // Check if the customer already has a caregiver
        if (!hasCareGiver) {
            this.careGiver = careGiver;
            hasCareGiver = true; // Set hasCareGiver to true since now the customer has a caregiver
            return true; // Return true indicating that the caregiver was added
        } else {
            return false; // Return false indicating that the customer already has a caregiver
        }
    }



}

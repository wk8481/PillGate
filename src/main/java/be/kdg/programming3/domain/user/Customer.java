package be.kdg.programming3.domain.user;

public class Customer {
    private int customer_id;
    private String customer_name;
    private int age;

    private String email;
    private boolean hasCareGiver;

    CareGiver careGiver;

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

    public void setCareGiver(CareGiver careGiver) {
        this.careGiver = careGiver;
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
}

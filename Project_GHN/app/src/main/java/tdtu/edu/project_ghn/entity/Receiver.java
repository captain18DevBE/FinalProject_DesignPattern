package tdtu.edu.project_ghn.entity;

import java.io.Serializable;

public class Receiver implements Serializable {
    private String fullName;
    private String phoneNumber;
    private String address;
    private String detailLocal;
    private String notes;
    private boolean isPaid;
    private double amountPay;


    public Receiver() {
    }

    public Receiver(String fullName, String phoneNumber, String address, String detailLocal, String notes, boolean isPaid, double amountPay) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.detailLocal = detailLocal;
        this.notes = notes;
        this.isPaid = isPaid;
        this.amountPay = amountPay;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public double getAmountPay() {
        return amountPay;
    }

    public void setAmountPay(double amountPay) {
        this.amountPay = amountPay;
    }

    public void setPaid(boolean paid) {
        this.isPaid = paid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailLocal() {
        return detailLocal;
    }

    public void setDetailLocal(String detailLocal) {
        this.detailLocal = detailLocal;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


}

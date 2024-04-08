package tdtu.edu.project_ghn.entity;

import java.io.Serializable;

public class Shipper implements Serializable {
    public String email;
    public String fullName;
    public String address;
    public String phoneNumber;
    public String typeOfTransport;
    public int roleId;

    public Shipper() {}

    public Shipper(String email, String fullName, String address, String phoneNumber, String typeOfTransport, int roleId) {
        this.email = email;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.typeOfTransport = typeOfTransport;
        this.roleId = roleId;
    }

    public String getTypeOfTransport() {
        return typeOfTransport;
    }

    public void setTypeOfTransport(String typeOfTransport) {
        this.typeOfTransport = typeOfTransport;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}

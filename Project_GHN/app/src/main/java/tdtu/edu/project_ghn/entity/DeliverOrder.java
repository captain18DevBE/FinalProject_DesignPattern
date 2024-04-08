package tdtu.edu.project_ghn.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DeliverOrder implements Serializable {

    private Customer customer;
    private String receiverAddress;
    private Receiver receiver;
    private LocalDateTime dateTime;
    private String service;
    private String typeOfTransport;
    private Product product;
    private float lengthOfRoad;
    private double totalPrice = 0.0;
    private int status = 0;

    public DeliverOrder() {}

    public DeliverOrder(Customer customer, String receiverAddress, Receiver receiver, LocalDateTime dateTime, String service, String typeOfTransport, Product product, float lengthOfRoad, double totalPrice, int status) {
        this.customer = customer;
        this.receiverAddress = receiverAddress;
        this.receiver = receiver;
        this.dateTime = dateTime;
        this.service = service;
        this.typeOfTransport = typeOfTransport;
        this.product = product;
        this.lengthOfRoad = lengthOfRoad;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getLengthOfRoad() {
        return lengthOfRoad;
    }

    public void setLengthOfRoad(float lengthOfRoad) {
        this.lengthOfRoad = lengthOfRoad;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTypeOfTransport() {
        return typeOfTransport;
    }

    public void setTypeOfTransport(String typeOfTransport) {
        this.typeOfTransport = typeOfTransport;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

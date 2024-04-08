package tdtu.edu.project_ghn.model;

import java.io.Serializable;
import java.time.LocalDateTime;


public class OrderDTO implements Serializable {

    private String key;

    private String customerAddress;
    private String customerName;
    private String detailAddress;
    private String customerPhoneNumber;
    private String kindOfTransport;
    private String kindOfService;
    private String sizeProduct;
    private String typeOfProduct;
    private String noteForShipper;
    private Double totalPrice;

    private String email;
    private String id;
    private String address;

    private String dateTime;
    private String type;
    private String phoneNumber;
    private Long state;

    public OrderDTO() {
    }

    public OrderDTO(String key, String customerAddress, String customerName, String detailAddress, String customerPhoneNumber, String kindOfTransport, String kindOfService, String sizeProduct, String typeOfProduct, String noteForShipper, Double totalPrice, String email, String id, String address, String dateTime, String type, String phoneNumber, Long state) {
        this.key = key;
        this.customerAddress = customerAddress;
        this.customerName = customerName;
        this.detailAddress = detailAddress;
        this.customerPhoneNumber = customerPhoneNumber;
        this.kindOfTransport = kindOfTransport;
        this.kindOfService = kindOfService;
        this.sizeProduct = sizeProduct;
        this.typeOfProduct = typeOfProduct;
        this.noteForShipper = noteForShipper;
        this.totalPrice = totalPrice;
        this.email = email;
        this.id = id;
        this.address = address;
        this.dateTime = dateTime;
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.state = state;
    }


    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getKindOfTransport() {
        return kindOfTransport;
    }

    public void setKindOfTransport(String kindOfTransport) {
        this.kindOfTransport = kindOfTransport;
    }

    public String getKindOfService() {
        return kindOfService;
    }

    public void setKindOfService(String kindOfService) {
        this.kindOfService = kindOfService;
    }

    public String getSizeProduct() {
        return sizeProduct;
    }

    public void setSizeProduct(String sizeProduct) {
        this.sizeProduct = sizeProduct;
    }

    public String getTypeOfProduct() {
        return typeOfProduct;
    }

    public void setTypeOfProduct(String typeOfProduct) {
        this.typeOfProduct = typeOfProduct;
    }

    public String getNoteForShipper() {
        return noteForShipper;
    }

    public void setNoteForShipper(String noteForShipper) {
        this.noteForShipper = noteForShipper;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getType() {
        return type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getKey() {
        return key;
    }

    public Long getState() { return state; }

    public void setKey(String key) {
        this.key = key;
    }

}

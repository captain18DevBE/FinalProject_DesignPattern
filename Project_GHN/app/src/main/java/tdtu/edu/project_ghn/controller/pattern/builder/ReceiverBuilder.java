package tdtu.edu.project_ghn.controller.pattern.builder;

import java.io.Serializable;

import tdtu.edu.project_ghn.entity.Receiver;

public class ReceiverBuilder implements IReceiverBuilder, Serializable {
    private String fullName;
    private String phoneNumber;
    private String address;
    private String detailLocal;
    private String notes;
    private boolean isPaid;
    private double amountPay;
    @Override
    public IReceiverBuilder fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Override
    public IReceiverBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public IReceiverBuilder address(String address) {
        this.address = address;
        return this;
    }

    @Override
    public IReceiverBuilder detailLocal(String detailLocal) {
        this.detailLocal = detailLocal;
        return this;
    }

    @Override
    public IReceiverBuilder notes(String notes) {
        this.notes = notes;
        return this;
    }

    @Override
    public IReceiverBuilder isPaid(boolean isPaid) {
        this.isPaid = isPaid;
        return this;
    }

    @Override
    public IReceiverBuilder amountPay(double amountPay) {
        this.amountPay = amountPay;
        return this;
    }

    @Override
    public Receiver builder() {
        return new Receiver(fullName, phoneNumber, address, detailLocal, notes, isPaid, amountPay);
    }
}

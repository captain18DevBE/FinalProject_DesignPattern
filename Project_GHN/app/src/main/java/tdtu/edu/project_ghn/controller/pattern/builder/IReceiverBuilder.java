package tdtu.edu.project_ghn.controller.pattern.builder;

import tdtu.edu.project_ghn.entity.Receiver;

public interface IReceiverBuilder {
    IReceiverBuilder fullName(String fullName);
    IReceiverBuilder phoneNumber(String phoneNumber);
    IReceiverBuilder address(String address);
    IReceiverBuilder detailLocal(String detailLocal);
    IReceiverBuilder notes(String notes);
    IReceiverBuilder isPaid(boolean isPaid);
    IReceiverBuilder amountPay(double amountPay);
    Receiver builder();

}

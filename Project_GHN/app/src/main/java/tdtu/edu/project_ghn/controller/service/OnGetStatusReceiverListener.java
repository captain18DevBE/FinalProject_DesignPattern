package tdtu.edu.project_ghn.controller.service;

import tdtu.edu.project_ghn.entity.StatusDeliverOrder;

public interface OnGetStatusReceiverListener {
    void onSuccess(StatusDeliverOrder statusReceiver);
    void onFailure(String err);
}

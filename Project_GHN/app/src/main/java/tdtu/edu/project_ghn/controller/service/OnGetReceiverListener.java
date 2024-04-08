package tdtu.edu.project_ghn.controller.service;

import tdtu.edu.project_ghn.entity.Receiver;

public interface OnGetReceiverListener {
    void onSuccess(Receiver receiver);
    void onFailure(String err);
}

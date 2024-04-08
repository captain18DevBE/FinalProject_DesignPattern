package tdtu.edu.project_ghn.controller.service;

import tdtu.edu.project_ghn.entity.DeliverOrder;

public interface OnGetDeliverOrderListener {
    void onSuccess(DeliverOrder deliverOrder);
    void onFailure(String err);
}

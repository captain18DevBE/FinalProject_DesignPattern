package tdtu.edu.project_ghn.controller.service;

import java.util.Map;

import tdtu.edu.project_ghn.entity.DeliverOrder;

public interface OnGetDeliverOrderByEmailUserListener {
    void onSuccess(Map<String, Object> values);
    void onFailure(String err);
}

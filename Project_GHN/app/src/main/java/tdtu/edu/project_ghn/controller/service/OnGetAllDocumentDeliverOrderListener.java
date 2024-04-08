package tdtu.edu.project_ghn.controller.service;

import java.util.List;
import java.util.Map;

import tdtu.edu.project_ghn.entity.DeliverOrder;

public interface OnGetAllDocumentDeliverOrderListener {
    void onSuccess(List<Map<String, Object>> collectionOrders);
    void onFailure(String err);
}

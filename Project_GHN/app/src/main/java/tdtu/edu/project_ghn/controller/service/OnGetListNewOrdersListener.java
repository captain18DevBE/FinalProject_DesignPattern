package tdtu.edu.project_ghn.controller.service;

import java.util.Map;

public interface OnGetListNewOrdersListener {
    void onSuccess(Map<String, Object> data);
    void onFailure(String err);
}

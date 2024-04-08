package tdtu.edu.project_ghn.controller.service;

import tdtu.edu.project_ghn.entity.Shipper;

public interface OnGetShipperListener {
    void onSuccess(Shipper shipper);
    void onFailure(String err);
}

package tdtu.edu.project_ghn.controller.service;

import tdtu.edu.project_ghn.entity.Customer;

public interface OnGetCustomerByEmail {
    void onSuccess(Customer customer);
    void onFailure(String msgErr);
}

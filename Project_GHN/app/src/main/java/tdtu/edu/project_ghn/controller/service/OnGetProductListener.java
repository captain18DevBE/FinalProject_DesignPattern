package tdtu.edu.project_ghn.controller.service;

import tdtu.edu.project_ghn.entity.Product;

public interface OnGetProductListener {
    void onSuccess(Product product);
    void onFailure(String err);
}

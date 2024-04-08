package tdtu.edu.project_ghn.controller.service;

public interface OnUploadProductImageListener {
    void onSuccess(String imgPath);
    void onFailure(String err);
}

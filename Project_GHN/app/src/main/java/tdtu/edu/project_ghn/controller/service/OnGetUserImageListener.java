package tdtu.edu.project_ghn.controller.service;

public interface OnGetUserImageListener {
    void onSuccess(String imgPath);
    void onFailure(String err);
}

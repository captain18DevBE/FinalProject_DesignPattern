package tdtu.edu.project_ghn.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import tdtu.edu.project_ghn.R;
import tdtu.edu.project_ghn.controller.UserController;
import tdtu.edu.project_ghn.controller.service.OnUpdatePasswordListener;

public class ChangeCustomerPassActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    UserController userController = new UserController();
    Toolbar toolbar;
    EditText edtCurrentPassword, edtNewPassword, edtConfirmNewPassword;
    ProgressDialog progressDialog;
    Button btnUpdatePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_customer_pass);

        initUI();
        initListener();
        buildMenuAction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_information_receiver, menu);
        return true;
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        edtCurrentPassword = findViewById(R.id.edt_current_password);
        edtNewPassword = findViewById(R.id.edt_new_password);
        edtConfirmNewPassword = findViewById(R.id.edt_confirm_new_password);
        progressDialog = new ProgressDialog(ChangeCustomerPassActivity.this);
        btnUpdatePassword = findViewById(R.id.btn_save);
    }
    private void initListener() {
        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAuthenticationPassword();
            }
        });
    }
    private void buildMenuAction() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Đổi mật khẩu");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkAuthenticationPassword() {
        String currentPassword = edtCurrentPassword.getText().toString().trim();
        String newPassword = edtNewPassword.getText().toString().trim();
        String confirmNewPassword = edtConfirmNewPassword.getText().toString().trim();

        if (TextUtils.isEmpty(currentPassword)) {
            edtCurrentPassword.setError("Nhập mật khẩu cũ!");
        } else if (newPassword.isEmpty()) {
            edtNewPassword.setError("Nhập mật khẩu mới!");
        } else if (newPassword.length() < 6) {
            edtNewPassword.setError("Độ dài mật khẩu phải lớn hơn 5!");
        } else if (confirmNewPassword.isEmpty()) {
            edtConfirmNewPassword.setError("Xác nhận lại mật khẩu!");
        } else if (confirmNewPassword.compareTo(newPassword) != 0) {
            edtConfirmNewPassword.setError("Xác nhận mật khẩu phải giống nhau!");
        } else {
            updatePassword(currentPassword, newPassword);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

    }

    private void updatePassword(String currentPassword, String newPassword) {
        userController.updateUserPassword(currentPassword, newPassword, new OnUpdatePasswordListener() {
            @Override
            public void onSuccess() {
                progressDialog.dismiss();
                Toast.makeText(ChangeCustomerPassActivity.this, "Thay đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(String err) {
                progressDialog.dismiss();
                Toast.makeText(ChangeCustomerPassActivity.this, "Thay đổi mật khẩu không thành công, đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
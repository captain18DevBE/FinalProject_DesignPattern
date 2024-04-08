package tdtu.edu.project_ghn.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import tdtu.edu.project_ghn.MainActivity;
import tdtu.edu.project_ghn.R;
import tdtu.edu.project_ghn.controller.CustomerController;
import tdtu.edu.project_ghn.entity.Customer;

public class CustomerSignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView txtNotice;
    EditText edt_CustomerEmailSignup, edt_CustomerPassSignup, edt_CustomerPassConfirmSignup;
    Button btnCustomerSignup;
    Toolbar toolbar;
    ProgressBar progressBar;

    CustomerController customerController = new CustomerController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up);

        initUI();
        initListener();
        buildMenuAction(toolbar);

        txtNotice.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();


    }

    private void initListener() {
        btnCustomerSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_CustomerEmailSignup.getText().toString();
                String password = edt_CustomerPassSignup.getText().toString();
                String confirmPass = edt_CustomerPassConfirmSignup.getText().toString();

                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    txtNotice.setVisibility(View.VISIBLE);
                    txtNotice.setTextColor(Color.RED);
                    txtNotice.setText("Xin hãy nhập đúng email!");
                } else if (password.length() <= 5) {
                    txtNotice.setVisibility(View.VISIBLE);
                    txtNotice.setTextColor(Color.RED);
                    txtNotice.setText("Độ dài mật khẩu phải lớn hơn 5!");
                } else if (!password.equals(confirmPass)) {
                    txtNotice.setVisibility(View.VISIBLE);
                    txtNotice.setTextColor(Color.RED);
                    txtNotice.setText("Xác nhận mật khẩu phải giống nhau!");
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(CustomerSignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        progressBar.setVisibility(View.VISIBLE);
                                        txtNotice.setVisibility(View.VISIBLE);
                                        txtNotice.setTextColor(Color.GREEN);
                                        txtNotice.setText("Đăng ký tài khoản thành công!");
                                        Customer newCustomer = new Customer();
                                        newCustomer.setEmail(user.getEmail());
                                        newCustomer.setRole("customer");
                                        customerController.signUp(newCustomer);
                                        Intent intent = new Intent(CustomerSignUpActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finishAffinity();
                                    } else {
                                        txtNotice.setVisibility(View.VISIBLE);
                                        txtNotice.setTextColor(Color.RED);
                                        txtNotice.setText("Đăng ký tài khoản thất bại!");
                                    }
                                }
                            });
                }
            }
        });
    }

    private void initUI() {
        edt_CustomerEmailSignup = findViewById(R.id.edt_CustomerEmailSignup);
        edt_CustomerPassSignup = findViewById(R.id.edt_CustomerPassSignup);
        edt_CustomerPassConfirmSignup = findViewById(R.id.edt_CustomerPassConfirmSignup);
        btnCustomerSignup = findViewById(R.id.btnCustomerSignup);
        txtNotice = findViewById(R.id.txtNotice);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBar);
    }

    private void buildMenuAction(androidx.appcompat.widget.Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Đăng ký");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
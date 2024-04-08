package tdtu.edu.project_ghn.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import tdtu.edu.project_ghn.MainActivity;
import tdtu.edu.project_ghn.R;

public class CustomerLoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button btnCustomerSignUp, btnCustomerLogin;
    EditText edtUserName, edtPassWord;
    TextView tvAlertLogin;
    Toolbar toolbar;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        initFindView();
        buildMenuAction(toolbar);

        initListener();

    }

    private void initListener() {
        btnCustomerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAuthentication();
            }
        });

        btnCustomerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(CustomerLoginActivity.this, CustomerSignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkAuthentication() {

        mAuth = FirebaseAuth.getInstance();
        String email = edtUserName.getText().toString().trim();
        String password = edtPassWord.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    tvAlertLogin.setTextColor(Color.GREEN);
                    tvAlertLogin.setText("Đăng nhập thành công");
                    progressBar.setVisibility(View.VISIBLE);

                    FirebaseUser user = mAuth.getCurrentUser();
//                    updateUI()
                    Intent intent = new Intent(CustomerLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    tvAlertLogin.setTextColor(Color.RED);
                    tvAlertLogin.setText("Sai tên đăng nhập hoặc mật khẩu!");
                }
            }
        });
    }


    private void buildMenuAction(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Đăng nhập");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initFindView() {
        btnCustomerSignUp = findViewById(R.id.btnSignUp);
        btnCustomerLogin = findViewById(R.id.btnLogin);
        toolbar = findViewById(R.id.toolbar);
        edtUserName = findViewById(R.id.edtShopOwnerName);
        edtPassWord = findViewById(R.id.edtShopPhone1);
        tvAlertLogin = findViewById(R.id.tvAlertLogin);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }


}
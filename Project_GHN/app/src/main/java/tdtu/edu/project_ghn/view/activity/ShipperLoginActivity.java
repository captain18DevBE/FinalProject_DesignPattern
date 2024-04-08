package tdtu.edu.project_ghn.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import tdtu.edu.project_ghn.MainActivity;
import tdtu.edu.project_ghn.R;
import tdtu.edu.project_ghn.ShipperMainActivity;

public class ShipperLoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button btnLoginShipper;
    Toolbar toolbar;
    ProgressBar progressBar;
    EditText txtShipperCode, edtShipperPassword;
    TextView tvAlertLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_login);
        
        initUI();

        btnLoginShipper = findViewById(R.id.btnLoginShipper);
        progressBar = findViewById(R.id.progressBar);

        toolbar = findViewById(R.id.toolbar);
        buildMenuAction(toolbar);
        btnLoginShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);


                checkAuthentication();
            }
        });
    }

    private void initUI() {
        txtShipperCode = findViewById(R.id.txtShipperCode);
        edtShipperPassword = findViewById(R.id.edtShipperPassword);
        tvAlertLogin = findViewById(R.id.tvAlertLogin);
    }

    private void checkAuthentication() {
        mAuth = FirebaseAuth.getInstance();
        String email = txtShipperCode.getText().toString().trim();
        String password = edtShipperPassword.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    progressBar.setVisibility(View.VISIBLE);

//                    updateUI()
                    Intent intent = new Intent(ShipperLoginActivity.this, ShipperMainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    tvAlertLogin.setTextColor(Color.RED);
                    tvAlertLogin.setText("Sai tên đăng nhập hoặc mật khẩu!");
                }
            }
        });
    }

    private void buildMenuAction(androidx.appcompat.widget.Toolbar toolbar) {
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
}
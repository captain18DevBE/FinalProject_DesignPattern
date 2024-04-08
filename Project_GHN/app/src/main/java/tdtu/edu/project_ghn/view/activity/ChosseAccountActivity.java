package tdtu.edu.project_ghn.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import tdtu.edu.project_ghn.R;

public class ChosseAccountActivity extends AppCompatActivity {

    Button btnCustomerLogin, btnShipperLogin;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosse_account);

        initFindView();

        btnShipperLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(ChosseAccountActivity.this, ShipperLoginActivity.class);
                startActivity(intent);

            }
        });

        btnCustomerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(ChosseAccountActivity.this, CustomerLoginActivity.class);
                startActivity(intent);

            }
        });

    }

    private void initFindView() {
        btnShipperLogin = findViewById(R.id.btnShipperLogin);
        btnCustomerLogin = findViewById(R.id.btnCustomerLogin);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
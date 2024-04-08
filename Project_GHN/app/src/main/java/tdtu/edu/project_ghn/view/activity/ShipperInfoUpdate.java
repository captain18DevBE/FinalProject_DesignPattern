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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import tdtu.edu.project_ghn.R;
import tdtu.edu.project_ghn.ShipperMainActivity;

public class ShipperInfoUpdate extends AppCompatActivity {

    EditText edtShipperName, edtWorkArea, edtShipperPhone;
    CheckBox checkBike, checkTricycle;
    Button btnSave;
    TextView txtNotice;
    Toolbar toolbar;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_info_update);

        toolbar = findViewById(R.id.toolbar5);
        edtShipperName = findViewById(R.id.edtShipperName);
        edtWorkArea = findViewById(R.id.edtWorkArea);
        edtShipperPhone = findViewById(R.id.edtShipperPhone);
        checkBike = findViewById(R.id.checkBike);
        checkTricycle = findViewById(R.id.checkTricycle);
        btnSave = findViewById(R.id.button5);
        txtNotice = findViewById(R.id.textView15);
        progressBar = findViewById(R.id.progressBar);

        buildMenuAction(toolbar);
        txtNotice.setVisibility(View.GONE);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtShipperName.getText().toString().isEmpty()) {
                    edtShipperName.setError("Nhập tên của bạn!");
                    txtNotice.setVisibility(View.VISIBLE);
                    txtNotice.setTextColor(Color.RED);
                    txtNotice.setText("Vui lòng nhập tên của bạn!");
                } else if (edtWorkArea.getText().toString().isEmpty()) {
                    edtWorkArea.setError("Nhập khu vực hoạt động!");
                    txtNotice.setVisibility(View.VISIBLE);
                    txtNotice.setTextColor(Color.RED);
                    txtNotice.setText("Vui lòng nhập khu vực hoạt động!");
                } else if (edtShipperPhone.getText().toString().isEmpty()) {
                    edtShipperName.setError("Nhập số điện thoại!");
                    txtNotice.setVisibility(View.VISIBLE);
                    txtNotice.setTextColor(Color.RED);
                    txtNotice.setText("Vui lòng nhập số điện thoại!");
                } else if (!checkBike.isChecked() && !checkTricycle.isChecked()) {
                    txtNotice.setVisibility(View.VISIBLE);
                    txtNotice.setTextColor(Color.RED);
                    txtNotice.setText("Hãy chọn ít nhất 1 loại phương tiện!");
                } else {
                    txtNotice.setVisibility(View.VISIBLE);
                    txtNotice.setTextColor(Color.GREEN);
                    txtNotice.setText("Thay đổi thông tin thành công!");
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(ShipperInfoUpdate.this, "Thay đổi thông tin thành công!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ShipperInfoUpdate.this, ShipperMainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void buildMenuAction(androidx.appcompat.widget.Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cập nhật thông tin");
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
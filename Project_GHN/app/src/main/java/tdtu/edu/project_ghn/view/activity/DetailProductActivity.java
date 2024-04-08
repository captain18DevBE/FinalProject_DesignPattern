package tdtu.edu.project_ghn.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import tdtu.edu.project_ghn.R;
import tdtu.edu.project_ghn.controller.ProductController;
import tdtu.edu.project_ghn.entity.DeliverOrder;
import tdtu.edu.project_ghn.entity.Product;

public class DetailProductActivity extends AppCompatActivity {
    ProductController productController = new ProductController();

    Button btnContinue, btnChose_imgProduct;
    Toolbar toolbar;
    EditText editTextNumber;
    TextView txtTotalPrice;
    DeliverOrder deliverOrder;
    ImageView imgProduct;
    Double insurancePrice = 0.0;
    RadioButton radio_Size_S, radio_Size_M, radio_Size_L, radio_Size_XL,
                radio_Type_TP, radio_Type_TT, radio_Type_DT,
                radio_insurance_default, radio_insurance_normal, radio_insurance_advance;
    private Product product = new Product();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        initUI();
        initListener();
        buildMenu();

        //get object data
        deliverOrder = (DeliverOrder) getIntent().getSerializableExtra("deliverOrder");
        product.setWeight(0);
        product.setProductType("thoi trang");
        product.setProductSize("S");
        product.setTypeOfInsurance("default");

        //set enable ui
        double totalPrice = deliverOrder.getTotalPrice();
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        Log.d("total price test", String.valueOf(totalPrice));
        txtTotalPrice.setText("Cước phí vận chuyển: " + Double.parseDouble(decimalFormat.format(totalPrice)));
    }

    private void buildMenu() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thông tin gói hàng");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_product, menu);
        return true;
    }

    private void initListener() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weight = editTextNumber.getText().toString().trim();
                if (weight.isEmpty()) {
                    editTextNumber.setError("Nhập cân nặng!");
                    Toast.makeText(DetailProductActivity.this, "Vui lòng nhập trong lượng gói hàng!", Toast.LENGTH_LONG).show();
                } else {

                    product.setWeight(Float.parseFloat(editTextNumber.getText().toString().trim()));
                    String address = getIntent().getStringExtra("address");
                    Intent intent = new Intent(DetailProductActivity.this, CustomerCreateOrderActivity3.class);

                    deliverOrder.setTotalPrice(deliverOrder.getTotalPrice()+insurancePrice);
                    deliverOrder.setProduct(product);
                    intent.putExtra("deliverOrder", deliverOrder);
                    intent.putExtra("address", address);
                    startActivity(intent);
                }

            }
        });

        btnChose_imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProductActivity.this, CameraXActivity.class);

                startActivityForResult(intent, 1234);
            }
        });

        radio_Size_S.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    product.setProductSize(radio_Size_S.getText().toString().trim());
                }
            }
        });

        radio_Size_M.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    product.setProductSize(radio_Size_M.getText().toString().trim());
                }
            }
        });

        radio_Size_L.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    product.setProductSize(radio_Size_L.getText().toString().trim());
                }
            }
        });

        radio_Size_XL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                product.setProductSize(radio_Size_XL.getText().toString().trim());
            }
        });

        radio_Type_DT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    product.setProductType("dien tu");
                }
            }
        });

        radio_Type_TP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    product.setProductType("thuc pham");
                }
            }
        });

        radio_Type_TT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    product.setProductType("thoi trang");
                }
            }
        });

        radio_insurance_normal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    product.setTypeOfInsurance("normal");
                    insurancePrice = 4000.0;

                    double totalPrice = deliverOrder.getTotalPrice()+insurancePrice;
                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

                    txtTotalPrice.setText("Cước phí vận chuyển: "+Double.parseDouble(decimalFormat.format(totalPrice)));
                }
            }
        });

        radio_insurance_default.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    product.setTypeOfInsurance("default");
                    insurancePrice = 0.0;

                    double totalPrice = deliverOrder.getTotalPrice()+insurancePrice;
                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

                    txtTotalPrice.setText("Cước phí vận chuyển: "+Double.parseDouble(decimalFormat.format(totalPrice)));
                }
            }
        });

        radio_insurance_advance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    product.setTypeOfInsurance("advance");
                    insurancePrice = 10000.0;
                    double totalPrice = deliverOrder.getTotalPrice()+insurancePrice;
                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

                    txtTotalPrice.setText("Cước phí vận chuyển: "+Double.parseDouble(decimalFormat.format(totalPrice)));
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            String imgPath = data.getStringExtra("imgPath");
            if (imgPath != null) {
                File file = new File(imgPath);
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                imgProduct.setImageBitmap(bitmap);

            }
        }
    }

    private void initUI() {
        btnContinue = findViewById(R.id.btnContinue);
        btnChose_imgProduct = findViewById(R.id.chose_imgProduct);
        toolbar = findViewById(R.id.toolbar);

        radio_Size_S = findViewById(R.id.radio_Size_S);
        radio_Size_M = findViewById(R.id.radio_Size_M);
        radio_Size_L = findViewById(R.id.radio_Size_L);
        radio_Size_XL = findViewById(R.id.radio_Size_XL);

        radio_Type_TT = findViewById(R.id.radio_Type_TT);
        radio_Type_TP = findViewById(R.id.radio_Type_TP);
        radio_Type_DT = findViewById(R.id.radio_Type_DT);

        radio_insurance_default = findViewById(R.id.radio_insurance_default);
        radio_insurance_normal = findViewById(R.id.radio_insurance_normal);
        radio_insurance_advance = findViewById(R.id.radio_insurance_advance);

        editTextNumber = findViewById(R.id.editTextNumber);

        imgProduct = findViewById(R.id.img_product);

        radio_Size_S.setChecked(true);
        radio_Type_TT.setChecked(true);
        radio_insurance_default.setChecked(true);

        txtTotalPrice = findViewById(R.id.txtTotalPrice);




    }
}
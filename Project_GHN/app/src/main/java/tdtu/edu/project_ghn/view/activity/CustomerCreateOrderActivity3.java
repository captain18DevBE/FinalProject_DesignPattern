package tdtu.edu.project_ghn.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.zip.Inflater;

import tdtu.edu.project_ghn.R;
import tdtu.edu.project_ghn.controller.DeliverOrderController;
import tdtu.edu.project_ghn.controller.service.OnAddDeliverOrderListener;
import tdtu.edu.project_ghn.controller.service.OnAddOrderMapListener;
import tdtu.edu.project_ghn.controller.service.OnGetDeliverOrderByEmailUserListener;
import tdtu.edu.project_ghn.entity.DeliverOrder;
import tdtu.edu.project_ghn.entity.Product;
import tdtu.edu.project_ghn.entity.Receiver;

public class CustomerCreateOrderActivity3 extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ImageButton btnMap;
    TextInputEditText txtAddress, edtAddressDetail, edt_ReceiverName, edt_ReceiverPhoneNumber, edt_ReceiverNotes, edt_amount_paid;
    CheckBox checkBox;
    LinearLayout cashOnDelivery;
    Toolbar toolbar;

    Button btnFinishOrder;

    Product productDetail;
    DeliverOrder deliverOrder;

    Receiver receiver = new Receiver();
    DeliverOrderController deliverOrderController = new DeliverOrderController();
    Map<String, Object> orderMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_create_order_3);

        initUI();
        initListener();
        buildMenu();

        String address = getIntent().getStringExtra("address");
        txtAddress.setText(address);

        deliverOrder = (DeliverOrder) getIntent().getSerializableExtra("deliverOrder");
        productDetail = deliverOrder.getProduct();

        deliverOrderController.getListDeliverOrdersByEmailUser(new OnGetDeliverOrderByEmailUserListener() {
            @Override
            public void onSuccess(Map<String, Object> values) {
                orderMap = values;
                Log.d("lay danh sach du lieu", orderMap.toString());
            }

            @Override
            public void onFailure(String err) {
                orderMap = new HashMap<>();
                Log.d("lay danh sach du lieu", "that bai");
            }
        });

    }

    private void buildMenu() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thông tin người nhận");
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
        getMenuInflater().inflate(R.menu.menu_information_receiver, menu);
        return true;
    }

    private void initListener() {
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerCreateOrderActivity3.this, GoogleMapActivity.class);
                intent.putExtra("address", txtAddress.getText().toString());
                startActivity(intent);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    receiver.setPaid(true);
                    cashOnDelivery.setVisibility(View.VISIBLE);

                }
                else {
                    receiver.setPaid(false);
                    cashOnDelivery.setVisibility(View.GONE);
                }
            }
        });

        btnFinishOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiver.setAddress(txtAddress.getText().toString().trim());
                receiver.setDetailLocal(edtAddressDetail.getText().toString().trim());
                receiver.setFullName(edt_ReceiverName.getText().toString().trim());
                receiver.setPhoneNumber(edt_ReceiverPhoneNumber.getText().toString().trim());
                receiver.setNotes(edt_ReceiverNotes.getText().toString().trim());


                deliverOrder.setReceiver(receiver);
                if (receiver.isPaid()) {
                    deliverOrder.setTotalPrice(deliverOrder.getTotalPrice() + Double.parseDouble(edt_amount_paid.getText().toString().trim())+10000);
                    receiver.setAmountPay(Double.parseDouble(edt_amount_paid.getText().toString().trim()));
                } else {
                    deliverOrder.setTotalPrice(deliverOrder.getTotalPrice());
                    receiver.setAmountPay(0.0);
                }
                LocalDateTime dateTime = LocalDateTime.now();
                deliverOrder.setDateTime(dateTime);



                int idItem = orderMap.size()+1;
                orderMap.put(""+idItem, deliverOrder);
                deliverOrderController.addDeliverOrderMapByUserEmail(orderMap, new OnAddOrderMapListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(CustomerCreateOrderActivity3.this, "Tạo đơn hàng thành công!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(String err) {
                        Toast.makeText(CustomerCreateOrderActivity3.this, "Đã có lỗi xảy ra, tạo đơn hàng thất bại!", Toast.LENGTH_LONG).show();
                    }
                });

                Intent intent = new Intent(CustomerCreateOrderActivity3.this, ListOrderActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar2);
        btnMap = findViewById(R.id.btnMap);
        txtAddress = findViewById(R.id.txtAddress);
        checkBox = findViewById(R.id.checkBox);
        cashOnDelivery = findViewById(R.id.cashOnDelivery);

        btnFinishOrder = findViewById(R.id.btnFinishOrder);

        edt_ReceiverName = findViewById(R.id.edt_ReceiverName);
        edt_ReceiverNotes = findViewById(R.id.edt_ReceiverNotes);
        edt_ReceiverPhoneNumber = findViewById(R.id.edt_ReceiverPhoneNumber);
        edtAddressDetail = findViewById(R.id.edtAddressDetail);

        edt_amount_paid = findViewById(R.id.edt_amount_paid);
    }

}
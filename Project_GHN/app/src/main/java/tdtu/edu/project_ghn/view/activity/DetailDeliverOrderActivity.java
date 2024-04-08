package tdtu.edu.project_ghn.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import tdtu.edu.project_ghn.MainActivity;
import tdtu.edu.project_ghn.R;
import tdtu.edu.project_ghn.controller.DeliverOrderController;
import tdtu.edu.project_ghn.controller.service.OnAddDeliverOrderListener;
import tdtu.edu.project_ghn.model.OrderDTO;

public class DetailDeliverOrderActivity extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    TextView txtDateCreated2,
            txtShopAddress, txtShopPhone, txtReceiverName, txtReceiverAddress,
            txtAddressDetail, txtReceiverPhone, txtTransport, txtService,
            txtProductSize, txtProductType, txtDescription,

            txtWaitForShipper, txtBeingShipped, txtFinishedShipping, txtIsPaid, txtTotalMoney2;

    Button btnUpdateOrderState;
    Toolbar toolbar;
    OrderDTO orderDTO;
    DeliverOrderController deliverOrderController = new DeliverOrderController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_deliver_order);

        initUI();
        initListener();
        buildMenu();
        orderDTO = (OrderDTO) getIntent().getSerializableExtra("orderSelected");

        updateUI();
        
    }

    private void buildMenu() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thông tin đơn hàng");
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

    private void updateUI() {
        if (orderDTO != null) {
            txtDateCreated2.setText("Ngày tạo: "+orderDTO.getDateTime());
            txtShopAddress.setText("Địa chỉ shop:  " + orderDTO.getAddress());
            txtShopPhone.setText("SDT Shop:  "+orderDTO.getCustomerPhoneNumber());
            txtReceiverName.setText("Tên người nhận:  " + orderDTO.getCustomerName());
            txtReceiverAddress.setText("Địa chỉ người nhận:  "+orderDTO.getAddress());
            txtAddressDetail.setText("Chi tết số nhà, số tầng:  "+orderDTO.getDetailAddress());
            txtReceiverPhone.setText("SĐT người nhận:  "+orderDTO.getPhoneNumber());
            txtTransport.setText("Loại xe:  "+orderDTO.getKindOfTransport());
            txtService.setText("Dịch vụ: "+orderDTO.getKindOfService());
            txtProductSize.setText("Kích cỡ: "+orderDTO.getSizeProduct());
            txtProductType.setText("Loại hàng: "+orderDTO.getTypeOfProduct());
            txtDescription.setText(orderDTO.getNoteForShipper());

            txtTotalMoney2.setText("TỔNG TIỀN:  "+orderDTO.getTotalPrice());

            updateOrderState(orderDTO.getState());

        }
    }

    private void updateOrderState(Long state) {
        if (state == -1) {
            btnUpdateOrderState.setEnabled(false);
            btnUpdateOrderState.setText("Đơn đã bị hủy");
        }
        if(state >= 0) {
            txtWaitForShipper.setTextColor(Color.GREEN);
        }
        if(state >= 1) {
            txtBeingShipped.setTextColor(Color.GREEN);
            btnUpdateOrderState.setVisibility(View.GONE);
            btnUpdateOrderState.setEnabled(false);
        }
        if(state >= 2) {
            txtFinishedShipping.setTextColor(Color.GREEN);
            txtFinishedShipping.setVisibility(View.VISIBLE);
            btnUpdateOrderState.setVisibility(View.GONE);
            btnUpdateOrderState.setEnabled(false);
        }
        if(state >= 3) {
            txtIsPaid.setTextColor(Color.GREEN);
            btnUpdateOrderState.setEnabled(false);
        } else {
            txtIsPaid.setText("4. Chưa thanh toán: XXX VND");
        }
    }
    private void initListener() {
        btnUpdateOrderState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DetailDeliverOrderActivity.this);
                builder.setTitle("HỦY ĐƠN HÀNG");
                builder.setMessage("Bạn có muốn hủy đơn hàng này?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deliverOrderController.updateStateDeliverOrder(user.getEmail(), orderDTO.getId(), -1, new OnAddDeliverOrderListener() {
                            @Override
                            public void onSuccess() {
                                Log.d("huy don hang", "thanh cong");

                                Intent intent = new Intent(DetailDeliverOrderActivity.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            }

                            @Override
                            public void onFailure(String err) {
                                Log.d("huy don hang", "that bai");
                            }
                        });
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void initUI() {
        txtAddressDetail = findViewById(R.id.txtAddressDetail);
        txtDateCreated2 = findViewById(R.id.txtDateCreated2);
        txtShopPhone = findViewById(R.id.txtShopPhone);
        txtShopAddress = findViewById(R.id.txtShopAddress);
        txtReceiverName = findViewById(R.id.txtReceiverName);
        txtReceiverAddress = findViewById(R.id.txtReceiverAddress);
        txtReceiverPhone = findViewById(R.id.txtReceiverPhone);
        txtTransport = findViewById(R.id.txtTransport);
        txtService = findViewById(R.id.txtService);
        txtProductSize = findViewById(R.id.txtProductSize);
        txtProductType = findViewById(R.id.txtProductType);
        txtDescription = findViewById(R.id.txtDescription);
        txtWaitForShipper = findViewById(R.id.txtWaitForShipper);
        txtBeingShipped = findViewById(R.id.txtBeingShipped);
        txtFinishedShipping = findViewById(R.id.txtFinishedShipping);
        txtIsPaid = findViewById(R.id.txtIsPaid);
        txtTotalMoney2 = findViewById(R.id.txtTotalMoney2);

        btnUpdateOrderState = findViewById(R.id.btnUpdateOrderState);

        toolbar = findViewById(R.id.toolbar4);
    }
}
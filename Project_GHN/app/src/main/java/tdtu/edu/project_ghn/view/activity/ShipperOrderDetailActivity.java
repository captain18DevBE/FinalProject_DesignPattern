package tdtu.edu.project_ghn.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tdtu.edu.project_ghn.R;
import tdtu.edu.project_ghn.controller.DeliverOrderController;
import tdtu.edu.project_ghn.controller.ShipperController;
import tdtu.edu.project_ghn.controller.service.OnAddOrderMapListener;
import tdtu.edu.project_ghn.controller.service.OnAddReceivedOrderListener;
import tdtu.edu.project_ghn.controller.service.OnGetAllDocumentDeliverOrderListener;
import tdtu.edu.project_ghn.controller.service.OnGetDeliverOrderByEmailUserListener;
import tdtu.edu.project_ghn.model.OrderDTO;
import tdtu.edu.project_ghn.controller.service.OnAddDeliverOrderListener;
import tdtu.edu.project_ghn.controller.service.OnGetAllDocumentDeliverOrderListener;
import tdtu.edu.project_ghn.model.OrderDTO;

public class ShipperOrderDetailActivity extends AppCompatActivity {
        private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        OrderDTO orderDTO = new OrderDTO();

        TextView txtWaitForShipper, txtBeingShipped, txtFinishedShipping, txtIsPaid, txtFinishedDate, txtDateCreated, txtShopAddress, txtShopPhone, txtReceiverName, txtReceiverAddress, txtAddressDetail, txtReceiverPhone, txtTransport, txtService, txtProductSize, txtProductWeight, txtProductType, txtDescription, txtTotalMoney;
        Button btnUpdateOrderState;
        Toolbar toolbar;
        ImageView imgProduct;
        //start test update orderdetail UI corresponds to data.
        boolean waitForShipper = false, beingShipped = false, FinishedShipping = false, IsPaid = false;
        private ShipperController shipperController = new ShipperController();
        private DeliverOrderController deliverOrderController = new DeliverOrderController();
        String receivedKey, customerEmail;

        Map<String, Object> orderMap;

        private void updateOrderState() {
            if (waitForShipper) {
                txtWaitForShipper.setTextColor(Color.GREEN);
                btnUpdateOrderState.setText("Đang giao hàng");
            }
            if (beingShipped) {
                waitForShipper = true;
                txtWaitForShipper.setTextColor(Color.GREEN);
                txtBeingShipped.setTextColor(Color.GREEN);
                if (IsPaid) btnUpdateOrderState.setText("Hoàn thành vận chuyển");
                else {
                    btnUpdateOrderState.setText("Hoàn thành thanh toán");
                }
            }
            if (FinishedShipping) {
                waitForShipper = true;
                txtWaitForShipper.setTextColor(Color.GREEN);
                beingShipped = true;
                txtBeingShipped.setTextColor(Color.GREEN);
                if (!IsPaid) {
                    IsPaid = true;
                }
                txtFinishedShipping.setTextColor(Color.GREEN);
                txtFinishedDate.setText("Hoàn thành vào ngày: XX - XX - XXXX");
                txtFinishedDate.setVisibility(View.VISIBLE);
                btnUpdateOrderState.setVisibility(View.GONE);
            }
            if (IsPaid) {
                txtIsPaid.setTextColor(Color.GREEN);
            } else {
                txtIsPaid.setText("4. Chưa thanh toán: XXX VND");
            }
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_shipper_order_detail);

            shipperController.getListDeliverOrdersByEmailUser(new OnGetDeliverOrderByEmailUserListener() {
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

//            orderDTO = (OrderDTO) getIntent().getSerializableExtra("orderSelected");

            txtWaitForShipper = findViewById(R.id.txtWaitForShipper);
            txtBeingShipped = findViewById(R.id.txtBeingShipped);
            txtFinishedShipping = findViewById(R.id.txtFinishedShipping);
            txtIsPaid = findViewById(R.id.txtIsPaid);
            btnUpdateOrderState = findViewById(R.id.btnUpdateOrderState);
            txtFinishedDate = findViewById(R.id.txtFinishedDate2);
            toolbar = findViewById(R.id.toolbar4);

            txtDateCreated = findViewById(R.id.txtDateCreated2);
            txtShopAddress = findViewById(R.id.txtShopAddress);
            txtShopPhone = findViewById(R.id.txtShopPhone);
            txtReceiverName = findViewById(R.id.txtReceiverName);
            txtReceiverAddress = findViewById(R.id.txtReceiverAddress);
            txtAddressDetail = findViewById(R.id.txtAddressDetail);
            txtReceiverPhone = findViewById(R.id.txtReceiverPhone);
            txtTransport = findViewById(R.id.txtTransport);
            txtService = findViewById(R.id.txtService);
            txtProductSize = findViewById(R.id.txtProductSize);
            txtProductWeight = findViewById(R.id.txtProductWeight);
            txtProductType = findViewById(R.id.txtProductType);
            txtDescription = findViewById(R.id.txtDescription);
            txtTotalMoney = findViewById(R.id.txtTotalMoney2);
            Log.d("check crash 1", "ok");
            buildMenuAction(toolbar);

            //testing saving and loading image on local storage
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            String imagePath = sharedPreferences.getString("imgPath", "");

            imgProduct = findViewById(R.id.imgProduct);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imgProduct.setImageBitmap(bitmap);

            receivedKey = getIntent().getStringExtra("key");
            customerEmail = getIntent().getStringExtra("email");
            getOrderDetail();


            updateOrderState();

            btnUpdateOrderState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!waitForShipper) {

                        int idItem = orderMap.size() + 1;
                        orderMap.put("" + idItem, orderDTO);
                        Log.d("test du lieu map", orderMap.toString());
                        shipperController.addReceivedOrderByShipperUserName(user.getEmail(), orderMap, new OnAddReceivedOrderListener() {

                            @Override
                            public void onSuccess() {
                                Toast.makeText(ShipperOrderDetailActivity.this, "Nhận đơn hàng mới thành công!", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(String err) {
                                Toast.makeText(ShipperOrderDetailActivity.this, "Đã có lỗi xảy ra, Nhận đơn hàng mới thất bại!", Toast.LENGTH_LONG).show();

                            }
                        });


                        waitForShipper = true;
                        deliverOrderController.updateStateDeliverOrder(orderDTO.getEmail(), orderDTO.getId(), 1, new OnAddDeliverOrderListener() {

                            @Override
                            public void onSuccess() {
                                Log.d("cap nhat trang thai don hang", "thanh cong");
                            }

                            @Override
                            public void onFailure(String err) {
                                Log.d("cap nhat trang thai don hang", "that bai");
                            }
                        });
                    } else if (!beingShipped) {
                        beingShipped = true;
                        deliverOrderController.updateStateDeliverOrder(orderDTO.getEmail(), orderDTO.getId(), 2, new OnAddDeliverOrderListener() {
                            @Override
                            public void onSuccess() {
                                Log.d("cap nhat trang thai don hang", "thanh cong");
                            }

                            @Override
                            public void onFailure(String err) {
                                Log.d("cap nhat trang thai don hang", "that bai");
                            }
                        });
                    } else if (!FinishedShipping) {
                        FinishedShipping = true;
                        deliverOrderController.updateStateDeliverOrder(orderDTO.getEmail(), orderDTO.getId(), 3, new OnAddDeliverOrderListener() {
                            @Override
                            public void onSuccess() {
                                Log.d("cap nhat trang thai don hang", "thanh cong");
                            }

                            @Override
                            public void onFailure(String err) {
                                Log.d("cap nhat trang thai don hang", "that bai");
                            }
                        });
                    }
                    updateOrderState();
                }
            });
        }

        private void getOrderDetail() {
            deliverOrderController.getAllDocumentDeliverOrder(new OnGetAllDocumentDeliverOrderListener() {

                @Override
                public void onSuccess(List<Map<String, Object>> collectionOrders) {
                    for (Map<String, Object> objectMap : collectionOrders) {
                        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                            String entryKey = entry.getKey();
                            if (entryKey.equals(receivedKey)) {

                                orderDTO.setId(entryKey);

                                Map<String, Object> value = (Map<String, Object>) entry.getValue();
                                Map<String, Object> dateTime = (Map<String, Object>) value.get("dateTime");

                                // Log the data to Logcat
                                Log.d("Order Data:", "Key: " + entryKey);
                                Log.d("Order Data:", "Value: " + value);
                                Log.d("Order Data:", "Date Time: " + dateTime);

                                Long year = (Long) dateTime.get("year");
                                Long month = (Long) dateTime.get("monthValue");
                                Long dayOfMonth = (Long) dateTime.get("dayOfMonth");

                                String strDateTime = "" + year + "-" + month + "-" + dayOfMonth;
                                txtDateCreated.setText("Ngày tạo: " + strDateTime);

                                orderDTO.setDateTime(strDateTime);


                                Map<String, Object> productMap = (Map<String, Object>) value.get("product");
                                String productType = (String) productMap.get("productType");
                                String productTypeUnicode = "";
                                switch (productType) {
                                    case "thoi trang":
                                        productTypeUnicode = "Thời trang";
                                        break;
                                    case "dien tu":
                                        productTypeUnicode = "Điện tử";
                                        break;
                                    case "thuc pham":
                                        productTypeUnicode = "Thực phẩm";
                                        break;
                                }
                                txtProductType.setText("Loại hàng: " + productTypeUnicode);
                                orderDTO.setTypeOfProduct(productTypeUnicode);

                                String productSize = (String) productMap.get("productSize");
                                txtProductSize.setText("Kích cỡ: " + productSize);
                                orderDTO.setSizeProduct(productSize);


                                String productWeight = (String) productMap.get("weight").toString();
                                txtProductWeight.setText("Trọng lượng: " + productWeight + "KG");


                                Map<String, Object> receiverMap = (Map<String, Object>) value.get("receiver");
                                String receiverAddress = (String) receiverMap.get("address");
                                txtReceiverAddress.setText("Địa chỉ người nhận: " + receiverAddress);


                                String phoneNumber = (String) receiverMap.get("phoneNumber");
                                txtReceiverPhone.setText("SĐT người nhận: " + phoneNumber);
                                orderDTO.setPhoneNumber(phoneNumber);

                                String detailAddress = (String) receiverMap.get("detailLocal");
                                txtAddressDetail.setText("Chi tết số nhà, số tầng: " + detailAddress);
                                String notesForShipper = (String) receiverMap.get("notes");
                                txtDescription.setText(notesForShipper);
                                String receiverName = (String) receiverMap.get("fullName");
                                txtReceiverName.setText("Tên người nhận: " + receiverName);


                                Map<String, Object> customerMap = (Map<String, Object>) value.get("customer");

                                String customerAddress = (String) customerMap.get("address");
                                txtShopAddress.setText("Địa chỉ shop: " + customerAddress);
                                String customerPhoneNumber = (String) customerMap.get("phoneNumber");
                                txtShopPhone.setText("SĐT shop: " + customerPhoneNumber);
                                String customerEmail = (String) customerMap.get("email");
                                orderDTO.setEmail(customerEmail);

                                Long statusOrder = (Long) value.get("status");
                                orderDTO.setState(statusOrder);

                                String kindOfService = (String) value.get("service");
                                String serviceTypeUnicode = "";
                                switch (kindOfService) {
                                    case "cheap":
                                        serviceTypeUnicode = "Tiết kiệm (30.000 VND)";
                                        break;
                                    case "fast":
                                        serviceTypeUnicode = "Siêu tốc (100.000 VND)";
                                        break;
                                    default:
                                        serviceTypeUnicode = kindOfService;
                                }
                                txtService.setText("Dịch vụ: " + serviceTypeUnicode);
                                String kindOfTransport = (String) value.get("typeOfTransport");
                                String transportTypeUnicode = "";
                                switch (kindOfTransport) {
                                    case "motorBike":
                                        transportTypeUnicode = "Xe máy (30.000 VND)";
                                        break;
                                    case "tricycle":
                                        transportTypeUnicode = "Xe ba gác (300.000 VND)";
                                        break;
                                    default:
                                        transportTypeUnicode = kindOfTransport;
                                }
                                txtTransport.setText("Loại xe: " + transportTypeUnicode);

                                Double totalPrice = (Double) value.get("totalPrice");
                                DecimalFormat df = new DecimalFormat("0");
                                String formattedPrice = df.format(totalPrice);
                                txtTotalMoney.setText("TỔNG TIỀN: " + formattedPrice + " VND");


                                break; // Exit the loop as the matching order is found
                            }
                        }
                    }
                    Log.d("key received", receivedKey);
                    Log.d("test detail order", "ok");
                }

                @Override
                public void onFailure(String err) {
                    Log.d("test detail order", "not ok");
                }
            });
        }

        private void buildMenuAction(androidx.appcompat.widget.Toolbar toolbar) {
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
    }

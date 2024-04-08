package tdtu.edu.project_ghn.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.window.OnBackInvokedDispatcher;

import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tdtu.edu.project_ghn.MainActivity;
import tdtu.edu.project_ghn.R;
import tdtu.edu.project_ghn.controller.DeliverOrderController;
import tdtu.edu.project_ghn.controller.service.OnGetAllDocumentDeliverOrderListener;
import tdtu.edu.project_ghn.controller.service.OnGetDeliverOrderByEmailUserListener;
import tdtu.edu.project_ghn.entity.DeliverOrder;
import tdtu.edu.project_ghn.model.OrderDTO;
import tdtu.edu.project_ghn.view.adapter.ListOrderAdapter;

public class ListOrderActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    private ListOrderAdapter listOrderAdapter;
    private List<OrderDTO> orderDTOList = new ArrayList<>();
    private DeliverOrderController deliverOrderController = new DeliverOrderController();
    private Map<String, Object> orderMap;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        initUI();
        setSupportActionBar(toolbar);



        deliverOrderController.getListDeliverOrdersByEmailUser(new OnGetDeliverOrderByEmailUserListener() {
            @Override
            public void onSuccess(Map<String, Object> values) {
                orderMap = values;
                for (Map.Entry<String, Object> entry : values.entrySet()) {
                    OrderDTO orderDTO = new OrderDTO();
                    String key = entry.getKey();
                    Map<String, Object> value = (Map<String, Object>) entry.getValue();
//                    DeliverOrder value = (DeliverOrder) entry.getValue();
                    String address = (String) value.get("receiverAddress");
                    Map<String, Object> dateTime = (Map<String, Object>) value.get("dateTime");

                    Long year = (Long) dateTime.get("year");
                    Long month = (Long) dateTime.get("monthValue");
                    Long dayOfMonth = (Long) dateTime.get("dayOfMonth");
                    Long timeOfDay = (Long) dateTime.get("hour");
                    Long minute = (Long) dateTime.get("minute");
                    Long second = (Long) dateTime.get("second");

                    String strDateTime = ""+year+"-"+month+"-"+dayOfMonth+"  "+timeOfDay+":"+minute+":"+second;

                    Map<String, Object> productMap = (Map<String, Object>) value.get("product");
                    String productType = (String) productMap.get("productType");
                    String productSize = (String) productMap.get("productSize");
                    String kindOfProduct = (String) productMap.get("productType");

                    Map<String, Object> receiverMap = (Map<String, Object>) value.get("receiver");
                    String phoneNumber = (String) receiverMap.get("phoneNumber");
                    String detailAddress = (String) receiverMap.get("detailLocal");
                    String notesForShipper = (String) receiverMap.get("notes");

                    Map<String, Object> customerMap = (Map<String, Object>) value.get("customer");
                    String customerEmail = (String) customerMap.get("email");
                    String customerAddress = (String) customerMap.get("address");
                    String customerName = (String) customerMap.get("fullName");
                    String customerPhoneNumber = (String) customerMap.get("phoneNumber");

                    Long statusOrder = (Long) value.get("status");
                    Double totalPrice = (Double) value.get("totalPrice");
                    String kindOfService = (String) value.get("service");
                    String kindOfTransport = (String) value.get("typeOfTransport");


                    orderDTO.setPhoneNumber(phoneNumber);
                    orderDTO.setAddress(address);
                    orderDTO.setDateTime(strDateTime);
                    orderDTO.setType(productType);
                    orderDTO.setEmail(customerEmail);
                    orderDTO.setId(key);
                    orderDTO.setState(statusOrder);

                    orderDTO.setCustomerAddress(customerAddress);
                    orderDTO.setCustomerName(customerName);
                    orderDTO.setDetailAddress(detailAddress);
                    orderDTO.setCustomerPhoneNumber(customerPhoneNumber);
                    orderDTO.setKindOfTransport(kindOfTransport);
                    orderDTO.setKindOfService(kindOfService);
                    orderDTO.setSizeProduct(productSize);
                    orderDTO.setTypeOfProduct(kindOfProduct);
                    orderDTO.setNoteForShipper(notesForShipper);
                    orderDTO.setTotalPrice(totalPrice);

                    orderDTOList.add(orderDTO);
//                    Log.d("lay danh sach du lieu"+key, orderDTO.getId());
                    initRCV();

                }
            }

            @Override
            public void onFailure(String err) {
                Log.d("lay danh sach du lieu", "that bai");
            }
        });

    }

    private void initRCV() {

        listOrderAdapter = new ListOrderAdapter(this, orderDTOList);
        recyclerView.setAdapter(listOrderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listOrderAdapter.setOnItemClickListener(new ListOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(OrderDTO orderDTO) {
                Intent intent = new Intent(ListOrderActivity.this, DetailDeliverOrderActivity.class);
                intent.putExtra("orderSelected", orderDTO);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_order, menu);
        return  true;
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbarListOrder);
        recyclerView = findViewById(R.id.rcv_listOrder);
    }

    @NonNull
    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        Intent intent = new Intent(ListOrderActivity.this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
        return super.getOnBackInvokedDispatcher();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ListOrderActivity.this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
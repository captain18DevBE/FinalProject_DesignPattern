package tdtu.edu.project_ghn.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tdtu.edu.project_ghn.R;

public class CustomerOrderDetailActivity extends AppCompatActivity {

    TextView txtWaitForShipper, txtBeingShipped, txtFinishedShipping, txtIsPaid, txtFinishedDate;
    Button btnCancelOrder;
    ImageView imgProduct;
    //start test update orderdetail UI corresponds to data.
    boolean waitForShipper = true, beingShipped = false, FinishedShipping = false, IsPaid = false;

    private void updateOrderState() {
        if(waitForShipper) {
            txtWaitForShipper.setTextColor(Color.GREEN);
        }
        if(beingShipped) {
            txtBeingShipped.setTextColor(Color.GREEN);
            btnCancelOrder.setVisibility(View.GONE);
        }
        if(FinishedShipping) {
            txtFinishedShipping.setTextColor(Color.GREEN);
            txtFinishedDate.setText("Hoàn thành vào ngày: XX - XX - XXXX");
            txtFinishedDate.setVisibility(View.VISIBLE);
            btnCancelOrder.setVisibility(View.GONE);
        }
        if(IsPaid) {
            txtIsPaid.setTextColor(Color.GREEN);
        } else {
            txtIsPaid.setText("4. Chưa thanh toán: XXX VND");
        }
    }
    //end test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_detail);

        txtWaitForShipper = findViewById(R.id.txtWaitForShipper);
        txtBeingShipped = findViewById(R.id.txtBeingShipped);
        txtFinishedShipping = findViewById(R.id.txtFinishedShipping);
        txtIsPaid = findViewById(R.id.txtIsPaid);
        btnCancelOrder = findViewById(R.id.btnCancelOrder);
        txtFinishedDate = findViewById(R.id.txtFinishedDate);

        //testing saving and loading image on local storage
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String imagePath = sharedPreferences.getString("imgPath", "");

        imgProduct = findViewById(R.id.imgProduct);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imgProduct.setImageBitmap(bitmap);

        updateOrderState();
    }
}
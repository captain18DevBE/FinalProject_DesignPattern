package tdtu.edu.project_ghn.view.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import tdtu.edu.project_ghn.R;
import tdtu.edu.project_ghn.controller.CustomerController;
import tdtu.edu.project_ghn.controller.service.OnGetCustomerByEmail;
import tdtu.edu.project_ghn.controller.service.OnUpdateCustomerInfoListener;
import tdtu.edu.project_ghn.entity.Customer;


public class CustomerProfileActivity extends AppCompatActivity {
    TextInputEditText edt_CustomerFullNameUpdate, edt_CustomerAddressUpdate, edt_CustomerPhoneNumberUpdate;
    Button btnUpdateCustomerInf, btnUpdateImg, btnStartCamera, btnStartGallery;
    ImageView imgUserImg;
    TextView txtNotice;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    CustomerController customerController = new CustomerController();
    String imgPath;

    FirebaseStorage storage = FirebaseStorage.getInstance();
//    ProgressDialog dialog = new ProgressDialog(CustomerProfileActivity.this);

    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean o) {
            if (o) {
                openGallery();
            }
        }
    });

    final private ActivityResultLauncher<Intent> getImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == RESULT_OK) {
                Intent intent = o.getData();
                if (intent == null) {
                    return;
                }
                Uri uri = intent.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imgUserImg.setImageBitmap(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        
        initUI();
        initListener();
        updateUI();
        updateCustomerInf();
    }

    private void initListener() {
        btnUpdateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionTakePhoto();
            }
        });

    }

    private void showOptionTakePhoto() {
        Dialog dialog = new Dialog(CustomerProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_chose_photo);

        btnStartCamera = dialog.findViewById(R.id.btnStartCamera);
        btnStartGallery = dialog.findViewById(R.id.btnStartGallery);

        dialog.show();

        btnStartCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(CustomerProfileActivity.this, CameraXActivity.class);
                startActivityForResult(intent, 1234);

            }
        });

        btnStartGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onClickRequestPermission();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            imgPath = data.getStringExtra("imgPath");
            if (!imgPath.isEmpty()) {
                File file = new File(imgPath);
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                imgUserImg.setImageBitmap(bitmap);

                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                        .setDisplayName(edt_CustomerFullNameUpdate.getText().toString().trim())
                        .setPhotoUri(Uri.parse(imgPath))
                        .build();

                user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            txtNotice.setText("Cập nhật thông tin thành công!");
                        } else {
                            txtNotice.setText("Cập nhật thông tin thất bại, đã có lỗi xảy ra!");
                        }
                    }
                });
            }
        }
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (ContextCompat.checkSelfPermission(CustomerProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            activityResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        getImageLauncher.launch(Intent.createChooser(intent, "select picture"));
    }

    private void updateUI() {
        customerController.getByEmail(user.getEmail(), new OnGetCustomerByEmail() {
            @Override
            public void onSuccess(Customer customer) {
                edt_CustomerFullNameUpdate.setText(customer.getFullName());
                edt_CustomerAddressUpdate.setText(customer.getAddress());
                edt_CustomerPhoneNumberUpdate.setText(customer.getPhoneNumber());
                //get image
                String pathImgAvatar = "images/avatar_users/"+customer.getImgPath();
                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(Uri.parse(pathImgAvatar))
                        .build();

                user.updateProfile(profileUpdate)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("firebase data", "User profile updated."+user.getPhotoUrl());
                                }
                            }
                        });

                StorageReference storageRef = storage.getReference(pathImgAvatar);
                try {
                    File localFile = File.createTempFile("tempfile", ".jpeg");
                    storageRef.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    imgUserImg.setImageBitmap(bitmap);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    imgUserImg.setImageResource(R.drawable.avatar_user_default);
                                    Log.d("lay anh firebase", "da co loi xay ra lay avatar kh thanh cong");
                                }
                            });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            @Override
            public void onFailure(String msgErr) {
                Toast.makeText(CustomerProfileActivity.this, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initUI() {
        btnUpdateCustomerInf = findViewById(R.id.btnUpdateCustomerInf);
        edt_CustomerPhoneNumberUpdate = findViewById(R.id.edt_CustomerPhoneNumberUpdate);
        edt_CustomerAddressUpdate = findViewById(R.id.edt_CustomerAddressUpdate);
        edt_CustomerFullNameUpdate = findViewById(R.id.edt_CustomerFullNameUpdate);
        btnUpdateImg = findViewById(R.id.btnUpdateImg);
        imgUserImg = findViewById(R.id.img_nav_UserImg);

        txtNotice = findViewById(R.id.txtNotice);

    }

    private void updateCustomerInf() {

        btnUpdateCustomerInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.show();
                Customer customer = new Customer();
                customer.setEmail(user.getEmail());
                customer.setFullName(edt_CustomerFullNameUpdate.getText().toString().trim());
                customer.setAddress(edt_CustomerAddressUpdate.getText().toString().trim());
                customer.setPhoneNumber(edt_CustomerPhoneNumberUpdate.getText().toString().trim());
                customer.setImgPath(imgPath);

                customerController.updateCustomerInf(customer, new OnUpdateCustomerInfoListener() {
                    @Override
                    public void onSuccess() {
//                        dialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onFailure(String err) {
//                        dialog.dismiss();
                        Toast.makeText(CustomerProfileActivity.this, "Cập nhật thông tin thất bại!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
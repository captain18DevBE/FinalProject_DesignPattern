package tdtu.edu.project_ghn.controller;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import tdtu.edu.project_ghn.controller.service.OnGetCustomerByEmail;
import tdtu.edu.project_ghn.controller.service.OnUpdateCustomerInfoListener;
import tdtu.edu.project_ghn.entity.Customer;
import tdtu.edu.project_ghn.view.activity.CustomerProfileActivity;

public class CustomerController {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    public boolean signUp(Customer customer) {

        Map<String, Object> objectMap = new HashMap<>();
        db.collection("customers").document(customer.getEmail()).set(customer);
        db.collection("deliver_orders").document(customer.getEmail()).set(objectMap);
        return true;
    }

    public boolean signIn(String email) {
        boolean result = false;

        return result;
    }

    //get customer by email
    public void getByEmail(String email, final OnGetCustomerByEmail listener) {
        DocumentReference docRef = db.collection("customers").document(email);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Customer customer = documentSnapshot.toObject(Customer.class);
                Log.d("firebase", "lay du lieu thanh cong");
                listener.onSuccess(customer);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure(e.getMessage());
            }
        });
    }

    //update detail information of customer
    public void updateCustomerInf(Customer resp, OnUpdateCustomerInfoListener listener) {
        DocumentReference documentReference = db.collection("customers").document(resp.getEmail());
        String imgLocalPath = resp.getImgPath();
        try {
            if (!imgLocalPath.isEmpty()) {
                Uri imgFile = Uri.fromFile(new File(resp.getImgPath()));
                StorageReference userImageProfile = storageRef.child("images/avatar_users/"+imgFile.getLastPathSegment());
                resp.setImgPath(imgFile.getLastPathSegment());
                UploadTask uploadTask = userImageProfile.putFile(imgFile);

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("firebase", "upload image is failed");
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("firebase", "upload image is success");
                    }
                });

            }
        } catch (Exception e) {
            Log.d("Cap nhat thong tin", "that bai!");
        }

            documentReference
                    .update(
                            "fullName", resp.getFullName(),
                            "address", resp.getAddress(),
                            "phoneNumber", resp.getPhoneNumber(),
                            "imgPath", resp.getImgPath()
                    ).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("cap nhat thong tin", "thanh cong");
                            listener.onSuccess();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("cap nhat thong tin", "that bai");
                            listener.onFailure(e.getMessage());
                        }
                    });

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(resp.getFullName())
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Update fullname", "success!");
                        }
                    }
                });

    }
}

package tdtu.edu.project_ghn.controller;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import tdtu.edu.project_ghn.controller.service.OnUploadProductImageListener;

public class ProductController {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    String lastPath;

    //Upload detail product image
    public void uploadImageProduct(String localFilePath, OnUploadProductImageListener listener) {
        if (!localFilePath.isEmpty()) {
            Uri imgFile = Uri.fromFile(new File(localFilePath));
            lastPath = imgFile.getLastPathSegment();
            StorageReference imgDetailProduct = storageReference.child("images/products/"+lastPath);

            UploadTask uploadTask = imgDetailProduct.putFile(imgFile);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    listener.onSuccess(lastPath);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    listener.onFailure(e.getMessage());
                }
            });
        }
    }
}

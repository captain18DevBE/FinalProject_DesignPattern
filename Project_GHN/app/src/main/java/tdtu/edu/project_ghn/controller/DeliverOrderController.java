package tdtu.edu.project_ghn.controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tdtu.edu.project_ghn.controller.service.OnAddDeliverOrderListener;
import tdtu.edu.project_ghn.controller.service.OnAddOrderMapListener;
import tdtu.edu.project_ghn.controller.service.OnGetAllDocumentDeliverOrderListener;
import tdtu.edu.project_ghn.controller.service.OnGetDeliverOrderByEmailUserListener;
import tdtu.edu.project_ghn.controller.service.OnGetListNewOrdersListener;
import tdtu.edu.project_ghn.entity.DeliverOrder;

public class DeliverOrderController {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Map<String, Object> deliverOrderMap;
    public void addDeliverOrderByEmail(DeliverOrder deliverOrder, final OnAddDeliverOrderListener listener) {
        db.collection("deliver_orders").document(user.getEmail()).set(deliverOrder)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onFailure(e.getMessage());
                    }
                });
    }

    public void addDeliverOrderMapByUserEmail(Map<String, Object> orderMap, OnAddOrderMapListener listener) {
        db.collection("deliver_orders").document(user.getEmail())
                .set(orderMap, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listener.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onFailure(e.getMessage());
                    }
                });
    }


    public void getAllDocumentDeliverOrder(OnGetAllDocumentDeliverOrderListener listener) {
        db.collection("deliver_orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Map<String, Object>> mapList = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                mapList.add(data);
//                                Log.d("lay danh sach du lieu", ""+ document.getData());
//                                Log.d("lay danh sach tung du lieu", document.getId() + " => " + document.getData());
                            }
                            listener.onSuccess(mapList);
                        } else {
                            listener.onFailure("da co loi xay ra");
                            Log.d("lay danh sach du lieu that bai", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getListDeliverOrdersByEmailUser(OnGetDeliverOrderByEmailUserListener listener) {
        db.collection("deliver_orders").document(user.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Map<String, Object> resultValue = document.getData();
                                listener.onSuccess(resultValue);
                            }
                        } else {
                            Log.d("lay danh sach du lieu", user.getEmail());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onFailure(e.getMessage());
                    }
                });
    }

    public void updateStateDeliverOrder(String email, String idOrder, long stateUpdate, OnAddDeliverOrderListener listener) {
        DocumentReference deliverOrder = db.collection("deliver_orders").document(email);

        getListDeliverOrdersByEmailUser(new OnGetDeliverOrderByEmailUserListener() {
            @Override
            public void onSuccess(Map<String, Object> values) {
                deliverOrderMap = values;
            }

            @Override
            public void onFailure(String err) {

            }
        });

        if (deliverOrderMap != null) {
            Map<String, Object> orderSelected = (Map<String, Object>) deliverOrderMap.get(idOrder);
            orderSelected.put("status", stateUpdate);
            deliverOrderMap.replace(idOrder, orderSelected);

            db.collection("deliver_orders").document(user.getEmail())
                    .set(deliverOrderMap, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            listener.onSuccess();
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
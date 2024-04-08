package tdtu.edu.project_ghn.controller.pattern.singleton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAuth {
    private static UserAuth uniInstance;
    private FirebaseUser userDb;

    private UserAuth() {
        userDb = FirebaseAuth.getInstance().getCurrentUser();
    }

    public static synchronized UserAuth getInstance() {
        if (uniInstance == null) {
            uniInstance = new UserAuth();
        }
        return uniInstance;
    }

    public FirebaseUser getUserDb() {
        return this.userDb;
    }
}

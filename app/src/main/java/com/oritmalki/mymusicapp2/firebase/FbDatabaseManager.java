package com.oritmalki.mymusicapp2.firebase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FbDatabaseManager {

    private static FirebaseDatabase mfbDatabase;
    private static DatabaseReference mDatabaseReference;

    private static final String REF_USERS = "users";
    private static final String REF_SHEETS = "sheets";
    private static final String REF_USERS_BY_EMAILS = "usersByEmails";
    private static final String REF_BEATS = "beats";
    private static final String REF_TIME_SIG = "timeSig";
    private static final String REF_SHEET_ID = "sheetId";
    private static final String REF_USER_ACCESS = "userAccess";
    private static final String REF_EMAIL = "email";



    public FbDatabaseManager() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }


    public static FbDatabaseManager getInstance() {
        return new FbDatabaseManager();
    }


    public void saveUserToFirebase(String uId, String email, IFbDatabase listener) {
        Map<String, Object> user = new HashMap<>();
        user.put(REF_EMAIL, email);
        mDatabaseReference.child(REF_USERS).child(uId).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    listener.onSuccess("");
                    return;
                }
                listener.onError("Couldn't save user.");
            }
        });
    }
}

package com.oritmalki.mymusicapp2.firebase;

import com.google.firebase.auth.FirebaseAuth;

public class AuthManager {

    private static FirebaseAuth mFirebaseAuth;

    public static FirebaseAuth getFirebaseAuth() {
        if (mFirebaseAuth == null) {
            mFirebaseAuth = FirebaseAuth.getInstance();
        }
        return mFirebaseAuth;
    }


}

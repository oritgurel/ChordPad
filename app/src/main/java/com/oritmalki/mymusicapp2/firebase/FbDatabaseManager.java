package com.oritmalki.mymusicapp2.firebase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oritmalki.mymusicapp2.model.Measure;
import com.oritmalki.mymusicapp2.model.Sheet;

import java.util.HashMap;
import java.util.List;
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
    private static final String REF_OWNER = "owner";
    private static final String REF_VIEWER = "viewer";
    private static final String REF_VIEWER_OF_SHEETS = "viewerOfSheets";





    public FbDatabaseManager() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }


    public static FbDatabaseManager getInstance() {
        return new FbDatabaseManager();
    }


    public void saveUserToFirebase(String uId, String email, IFbDatabase listener) {
        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put(REF_USERS + "/" + uId + "/" + REF_EMAIL, email);
        userUpdates.put(REF_USERS_BY_EMAILS + "/" + reformatEmail(email), uId);
        mDatabaseReference.updateChildren(userUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public void saveSheetToFirebase(Sheet sheet, IFbDatabase listener) {

        Map<String, Object> sheetUpdates = new HashMap<>();
        sheetUpdates.put(REF_SHEETS + "/" + String.valueOf(sheet.getId()), sheet);
        sheetUpdates.put(REF_USERS + "/" + sheet.getUserId() + "/" + REF_SHEETS + "/" + String.valueOf(sheet.getId()), true);
        sheetUpdates.put(REF_USERS + "/" + sheet.getUserId() + "/" + REF_SHEETS + "/" + REF_USER_ACCESS + "/" + sheet.getUserId(), REF_OWNER);
        mDatabaseReference.updateChildren(sheetUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    listener.onSuccess("");
                    return;
                }
                listener.onError("Couldn't save sheet to server");
            }
        });

    }

    public void saveMeasuresContentAndShare(List<Measure> measures, String email, IFbDatabase listener) {
        Map<String, Object> sheetContent = new HashMap<>();
        for (Measure measure : measures) {
            sheetContent.put(String.valueOf(measure.getSheetId()), measure);
            mDatabaseReference.updateChildren(sheetContent).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        listener.onSuccess("");
                        return;
                    }
                    listener.onError("Couldn't save measures to server");
                }
            });
        }

        getUserByEmail(email, new IFbDatabase() {
            @Override
            public void onError(String error) {
                listener.onError(error);
            }

            @Override
            public void onSuccess(String message) {
                //share
                //TODO fix saving so it won't overwrite exsiting data
                Map<String, Object> shareInfo = new HashMap<>();
                shareInfo.put(REF_SHEETS + "/" + measures.get(0).getSheetId() + "/" + REF_USER_ACCESS + "/" + message, REF_VIEWER);
                shareInfo.put(REF_USERS + "/" + message + "/" + REF_VIEWER_OF_SHEETS + "/" + measures.get(0).getSheetId(), true);
                mDatabaseReference.updateChildren(shareInfo);
                listener.onSuccess(message);
            }
        });

    }

    public void getUserByEmail(String email, IFbDatabase listener) {
        mDatabaseReference.child(REF_USERS_BY_EMAILS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String reformattedEmail = reformatEmail(email);
                if (dataSnapshot.hasChild(reformatEmail(reformattedEmail))) {
                    listener.onSuccess(dataSnapshot.child(reformattedEmail).getValue().toString());
                    return;
                }
                listener.onError("User with email not found.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String reformatEmail(String email) {
        return email.replace('.', ',');
    }
}

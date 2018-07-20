package com.oritmalki.mymusicapp2.ui.mainscreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.oritmalki.mymusicapp2.R;
import com.oritmalki.mymusicapp2.firebase.AuthManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    @BindView(R.id.si_email_et)
    TextInputEditText mEmailEt;
    @BindView(R.id.si_email_et_wrapper)
    TextInputLayout mEmailEtWrapper;
    @BindView(R.id.si_password_et)
    TextInputEditText mPasswordEt;
    @BindView(R.id.si_password_et_wrapper)
    TextInputLayout mPasswordEtWrapper;
    @BindView(R.id.si_confirm_password_et)
    TextInputEditText mConfirmPasswordEt;
    @BindView(R.id.si_confirm_password_et_wrapper)
    TextInputLayout mConfirmPasswordEtWrapper;
    @BindView(R.id.si_login_btn)
    Button mLoginBtn;
    @BindView(R.id.newExisting_user_tv)
    TextView mNewExistingUserTv;
    @BindView(R.id.si_constraint_layout)
    ConstraintLayout mConstraintLayout;

    private boolean mNewUserView;
    private String mEmail;
    private String mPassword;
    private String mConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initViews();
        mAuth = AuthManager.getFirebaseAuth();
    }

    private void initViews() {
        ButterKnife.bind(this);
        mLoginBtn.setOnClickListener(this);
        mNewExistingUserTv.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            startActivity(SheetsActivity.getIntent(this));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.si_login_btn:
                if (mNewUserView) {
                    createNewUser();
                    return;
                }
                loginWithEmail();
                break;

            case R.id.newExisting_user_tv:
                if (!mNewUserView) {
                    showNewUserView();
                } else {
                    showExistingUserView();
                }
                break;
        }

    }

    private void showNewUserView() {
        mNewExistingUserTv.setText(R.string.si_already_have_an_account);
        mLoginBtn.setText(R.string.si_create_new_account);
        mConfirmPasswordEtWrapper.setVisibility(View.GONE);
        mConfirmPasswordEt.setVisibility(View.GONE);
        mNewUserView = true;
    }

    private void showExistingUserView() {
        mNewExistingUserTv.setText(getString(R.string.si_new_user_prompt_text));
        mLoginBtn.setText(getString(R.string.si_login_btn_text));
        mConfirmPasswordEtWrapper.setVisibility(View.VISIBLE);
        mConfirmPasswordEt.setVisibility(View.VISIBLE);
        mNewUserView = false;
    }

    private void loginWithEmail() {
        mEmail = mEmailEt.getText().toString().trim();
        mPassword = mPasswordEt.getText().toString().trim();
        //TODO add fields validation
        mAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    showError(task.getException().getLocalizedMessage());
                    return;
                }

                startActivity(SheetsActivity.getIntent(getApplicationContext()));
                showMessage(getString(R.string.si_login_successful_msg));
            }

        });
    }

    private void createNewUser() {
        mEmail = mEmailEt.getText().toString().trim();
        mPassword = mPasswordEt.getText().toString().trim();
        mConfirmPassword = mConfirmPasswordEt.getText().toString().trim();
        //TODO add fields validation
        mAuth.createUserWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    showError(task.getException().getLocalizedMessage());
                    return;
                }

                startActivity(SheetsActivity.getIntent(getApplicationContext()));
                showMessage(getString(R.string.si_created_new_user_msg));
            }
        });
    }

    private void showError(String error) {

       Snackbar.make(mConstraintLayout, error, Snackbar.LENGTH_LONG).show();
    }

    private void showMessage(String message) {

        Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_LONG).show();
    }


}

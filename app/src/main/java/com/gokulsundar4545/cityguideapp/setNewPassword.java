package com.gokulsundar4545.cityguideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class setNewPassword extends AppCompatActivity {

    TextInputLayout newpassword,confirmpassword;
    Button update;


    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_new_password);

        newpassword=findViewById(R.id.new_password);
        confirmpassword=findViewById(R.id.comfiirm_password);
        update=findViewById(R.id.set_new_password_Btn);

        animation= AnimationUtils.loadAnimation(this,R.anim.side_anim);

        newpassword.setAnimation(animation);
        confirmpassword.setAnimation(animation);
        update.setAnimation(animation);
    }

    public void setNewPasswordBtn(View view){

        if (!validateNewPassword() | !validateConfirmPassword()){
            return;
        }

        String _newPassword=newpassword.getEditText().getText().toString().trim();
        String _phoneNumber=getIntent().getStringExtra("phoneNo");

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child(_phoneNumber).child("password").setValue(_newPassword);

        startActivity(new Intent(getApplicationContext(),FortgetSuccessMessage.class));
        finish();


    }

    private boolean validateNewPassword() {
        String val = newpassword.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            newpassword.setError("Field can not be empty");
            return false;
        }  else if (val.length()<7) {
            newpassword.setError("Password should contain 6 characters1");
            return false;
        }else{
            newpassword.setError(null);
            newpassword.setErrorEnabled(false);
            return true;

        }

    }

    private boolean validateConfirmPassword() {
        String val = confirmpassword.getEditText().getText().toString().trim();
        String val1 = newpassword.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            confirmpassword.setError("Field can not be empty");
            return false;
        }  else if (!val.contains(val1)) {
            confirmpassword.setError("Password Does not match");
            return false;
        }else{
            confirmpassword.setError(null);
            confirmpassword.setErrorEnabled(false);
            return true;

        }

    }
}
package com.gokulsundar4545.cityguideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ForgetPassword extends AppCompatActivity {

    private ImageView screenIcon;
    private TextView title,description;
    private TextInputLayout phoneNumberTextField;
    private CountryCodePicker countryCodePicker;
    private Button nextBtn;
    private Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);


        screenIcon=findViewById(R.id.forget_password_icon);
        description=findViewById(R.id.forget_password_description);
        title=findViewById(R.id.forget_password_title);
        phoneNumberTextField=findViewById(R.id.forget_password_phone_number);
        countryCodePicker=findViewById(R.id.country_code_picker);
        nextBtn=findViewById(R.id.forget_password_next_btn);

        animation= AnimationUtils.loadAnimation(this,R.anim.side_anim);
        screenIcon.setAnimation(animation);
        description.setAnimation(animation);
        title.setAnimation(animation);
        phoneNumberTextField.setAnimation(animation);
        countryCodePicker.setAnimation(animation);
        nextBtn.setAnimation(animation);


    }

    public  void verifyPhoneNumber(View view){

        if (!validatephonenumber()){
            return;
        }

        String _phoneNumber = phoneNumberTextField.getEditText().getText().toString().trim();
        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }

        final String _completePhoneNumber = "+" + countryCodePicker.getFullNumber() + _phoneNumber;
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if (snapshot.exists()){
                    phoneNumberTextField.setError(null);
                    phoneNumberTextField.setErrorEnabled(false);
                    Intent intent=new Intent(getApplicationContext(),verifyOtP1.class);
                    intent.putExtra("phoneNo",_completePhoneNumber);
                    startActivity(intent);
                    finish();
                }else {
                    phoneNumberTextField.setError("No such User exist!");
                    phoneNumberTextField.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {



            }
        });


    }

    private boolean validatephonenumber() {
        String val = phoneNumberTextField.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            phoneNumberTextField.setError("Field can not be empty");
            return false;
        } else {
            phoneNumberTextField.setError(null);
            return true;

        }

    }
}
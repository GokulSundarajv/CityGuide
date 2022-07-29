package com.gokulsundar4545.cityguideapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {


    private CircleImageView profileimage;
    private TextView SaveButton;


    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private Uri imageUri;
    private String myUri="";
    private StorageTask uploadeTask;
    private StorageReference storageProfilePicsRef;

    ImageView selectimage;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        storageProfilePicsRef= FirebaseStorage.getInstance().getReference().child("Profile Pic");

        SaveButton=findViewById(R.id.btnsave);
        profileimage=findViewById(R.id.Profile_image);
        selectimage=findViewById(R.id.select_image);






        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadProfileImage();
            }
        });

        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setAspectRatio(1,1).start(Profile.this);

            }
        });

        getUserinfo();
    }

    public  void callsettings(View view){
        startActivity(new Intent(Profile.this,Settings.class));
    }



    private void getUserinfo() {
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount()>0)
                {
                    if (snapshot.hasChild("image"))
                    {
                        String image=snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profileimage);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode ==RESULT_OK && data !=null)
        {

            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            imageUri=result.getUri();

            profileimage.setImageURI(imageUri);
        }
        else{
            Toast.makeText(this, "Error,Try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadProfileImage() {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Setting Your profile");
        progressDialog.setMessage("Please wait , While we are setting your data");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (imageUri!=null){
            final StorageReference fileref=storageProfilePicsRef
                    .child(mAuth.getCurrentUser().getPhoneNumber()+".jpg");
            uploadeTask=fileref.putFile(imageUri);

            uploadeTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull  Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri=task.getResult();
                        myUri=downloadUri.toString();

                        HashMap<String,Object> userMap =new HashMap<>();
                        userMap.put("image",myUri);

                        databaseReference.child(mAuth.getCurrentUser().getPhoneNumber()).updateChildren(userMap);



                        progressDialog.dismiss();



                    }
                }
            });
        }else{
            progressDialog.dismiss();
            Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show();

        }


    }

    public void CallMainActivityback(View view){
        startActivity(new Intent(Profile.this,MainActivity.class));
    }


}




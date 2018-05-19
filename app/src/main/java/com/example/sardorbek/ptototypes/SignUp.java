package com.example.sardorbek.ptototypes;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sardorbek.ptototypes.Common.Common;
import com.example.sardorbek.ptototypes.Model.new_requests.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    TextView textView;
    MaterialEditText edtIdNumber,edtName, edtPassword, edtSecureCode;
    Button btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textView =(TextView)findViewById(R.id.txt_createAccount);
        edtIdNumber=(MaterialEditText)findViewById(R.id.edit_idnumber);
        edtName=(MaterialEditText)findViewById(R.id.edit_name);
        edtPassword=(MaterialEditText)findViewById(R.id.edit_password);
        edtSecureCode=(MaterialEditText)findViewById(R.id.edit_SecureCode);
        btnSignUp=(Button)findViewById(R.id.btn_signUp);

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user= database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Common.isConnectedInternet(getBaseContext())) {
                    final ProgressDialog dialog = new ProgressDialog(SignUp.this);
                    dialog.setMessage("Please wait...");
                    dialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(edtIdNumber.getText().toString()).exists()) {
                                dialog.dismiss();
                                Toast.makeText(SignUp.this, "Id Number Already exists", Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                                User user = new User(edtName.getText().toString(),
                                        edtPassword.getText().toString(),
                                        edtSecureCode.getText().toString());
                                table_user.child(edtIdNumber.getText().toString()).setValue(user);

                                Toast.makeText(SignUp.this, "Sign up successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(SignUp.this, "Please check your Internet connection !!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }
}

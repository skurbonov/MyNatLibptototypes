package com.example.sardorbek.ptototypes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import io.paperdb.Paper;

public class Login extends AppCompatActivity {

    EditText edtIdNumber, edtPassword;
    Button btnLogin;
    com.rey.material.widget.CheckBox ckbRemember;
    TextView txtForgotPwd;

    FirebaseDatabase database;
    DatabaseReference table_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edtIdNumber=(MaterialEditText)findViewById(R.id.edtIdNumber);
        edtPassword=(MaterialEditText)findViewById(R.id.edtPassword);

        btnLogin =(Button)findViewById(R.id.btn_login);
        ckbRemember=(com.rey.material.widget.CheckBox)findViewById(R.id.ckbRemember);
        txtForgotPwd=(TextView)findViewById(R.id.txtForgotPwd);

        //initilize Paper
        Paper.init(this);


         database=FirebaseDatabase.getInstance();
         table_user= database.getReference("User");


        txtForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPwdDialog();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isConnectedInternet(getBaseContext())) {
                    //save user and password
                    if(ckbRemember.isChecked())
                    {
                        Paper.book().write(Common.USER_KEY,edtIdNumber.getText().toString());
                        Paper.book().write(Common.PWD_KEY,edtPassword.getText().toString());
                    }

                    final ProgressDialog dialog = new ProgressDialog(Login.this);
                    dialog.setMessage("Please wait...");
                    dialog.show();
                    table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(edtIdNumber.getText().toString()).exists()) {

                                dialog.dismiss();
                                User user = dataSnapshot.child(edtIdNumber.getText().toString()).getValue(User.class);
                                user.setPhone(edtIdNumber.getText().toString());
                                if (user.getPassword().equals(edtPassword.getText().toString())) {
                                    Intent homeIntent = new Intent(Login.this, Home.class);
                                    Common.currentUser = user;
                                    startActivity(homeIntent);
                                    finish();

                                    table_user.removeEventListener(this);
                                } else {
                                    Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                dialog.dismiss();
                                Toast.makeText(Login.this, "User not exist", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {


                        }
                    });
                }
                else
                {
                    Toast.makeText(Login.this, "Please check your Internet connection !!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        });
    }

    private void showForgotPwdDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Forgot Password");
        builder.setMessage("Enter your secure code");

        LayoutInflater inflater=this.getLayoutInflater();
        View forgot_view=inflater.inflate(R.layout.forgot_password_layout,null);

        builder.setView(forgot_view);
        builder.setIcon(R.drawable.ic_security_black_24dp);

        final MaterialEditText edtIdNumber=(MaterialEditText)forgot_view.findViewById(R.id.edit_idFnumber);
        final MaterialEditText edtSecureCode=(MaterialEditText)forgot_view.findViewById(R.id.edit_SecureCode);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Check if user available
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user=dataSnapshot.child(edtIdNumber.getText().toString())
                                .getValue(User.class);

                        if(user.getSecureCode().equals(edtSecureCode.getText().toString()))
                            Toast.makeText(Login.this, "Your password: "+user.getPassword(), Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Login.this, "Wrong Secure code !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }
}

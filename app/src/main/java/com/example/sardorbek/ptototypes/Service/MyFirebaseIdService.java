package com.example.sardorbek.ptototypes.Service;

import com.example.sardorbek.ptototypes.Common.Common;
import com.example.sardorbek.ptototypes.Model.new_requests.Token;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by sardorbek on 4/24/18.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService{
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String tokenRefreshed=FirebaseInstanceId.getInstance().getToken();
        if(Common.currentUser !=null)
            updateTokenToFirebase(tokenRefreshed);
    }

    private void updateTokenToFirebase(String tokenRefreshed) {
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference tokens=db.getReference("Tokens");
        Token token=new Token(tokenRefreshed,false);//false because this token is sent from user
        tokens.child(Common.currentUser.getPhone()).setValue(token);



    }
}

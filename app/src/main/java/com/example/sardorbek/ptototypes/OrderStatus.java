package com.example.sardorbek.ptototypes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sardorbek.ptototypes.Common.Common;
import com.example.sardorbek.ptototypes.Model.new_requests.Requests;
import com.example.sardorbek.ptototypes.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;


    FirebaseRecyclerAdapter<Requests,OrderViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");
        recyclerView=(RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

     //   if(getIntent()==null)
            loadOrders(Common.currentUser.getName());
       // else
         //   loadOrders(getIntent().getStringExtra("userName"));
    }

    private void loadOrders(String userName) {
        adapter=new FirebaseRecyclerAdapter<Requests, OrderViewHolder>(
                Requests.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("userName")
                .equalTo(userName)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Requests model, int position) {
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
//                viewHolder.tvTitle.setText(model.get);
            }
        };
        recyclerView.setAdapter(adapter);
    }


}

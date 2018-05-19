package com.example.sardorbek.ptototypes;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sardorbek.ptototypes.Common.Common;
import com.example.sardorbek.ptototypes.Database.Database;
import com.example.sardorbek.ptototypes.Model.new_requests.Book;
import com.example.sardorbek.ptototypes.Model.new_requests.Order;
import com.example.sardorbek.ptototypes.Model.new_requests.Rating;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.Arrays;

public class BookDetailActivity extends AppCompatActivity implements RatingDialogListener{
    private TextView tvBookName;
    private TextView tvPageCount;
    private TextView tvAuthor;
    private TextView tvISBN;
    private TextView tvPublishDate;
    private TextView tvDescription;

    ImageView book_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart, btnRating;
    RatingBar ratingBar;

    String bookId ="";
    FirebaseDatabase database;
    DatabaseReference book;
    DatabaseReference ratingTable;
    Book currentBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        database = FirebaseDatabase.getInstance();
        book = database.getReference("Book");

        ratingTable=database.getReference("Rating");

        btnCart=(FloatingActionButton)findViewById(R.id.btnCart);
        btnRating=(FloatingActionButton)findViewById(R.id.btn_rating);

        ratingBar=(RatingBar)findViewById(R.id.ratingBar);


        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                new Database(getBaseContext()).addToCart(new Order(
                        bookId,
                        currentBook.getTitle(),
                        "1"));
                Toast.makeText(BookDetailActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });

        tvBookName=(TextView)findViewById(R.id.book_name);
        tvPageCount=(TextView)findViewById(R.id.tvPageCount);
        tvAuthor=(TextView)findViewById(R.id.tvAuthor);
        tvISBN=(TextView) findViewById(R.id.tvISBN);
        tvDescription=(TextView) findViewById(R.id.book_description);
        tvPublishDate = findViewById(R.id.tvPublishDate);
        book_image = findViewById(R.id.img_food);

        collapsingToolbarLayout =(CollapsingToolbarLayout)findViewById(R.id.collapsing);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if(getIntent()!=null)
            bookId =getIntent().getStringExtra("bookId");
        if(!bookId.isEmpty())
        {

            if(Common.isConnectedInternet(getBaseContext()))

            {
                getDetailFood(bookId);
                getRatingBook(bookId);
            }
            else
            {
                Toast.makeText(BookDetailActivity.this, "Please check your Internet connection !!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    private void getRatingBook(String bookId) {

        com.google.firebase.database.Query bookRating=ratingTable.orderByChild("bookId").equalTo(bookId);
        bookRating.addValueEventListener(new ValueEventListener() {
            int count=0, sum=0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Rating item=postSnapshot.getValue(Rating.class);
                    sum+= Integer.parseInt(item.getRateValue());
                    count++;
                }
                if(count!=0)
                {
                    float average=sum/count;
                    ratingBar.setRating(average);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showRatingDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Very Bad","Not Good","Quite Good","Very Good","Excellent"))
                .setDefaultRating(1)
                .setTitle("Rate this book")
                .setDescription("Please select some stars and give your feedback")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Please write your comment here...")
                .setHintTextColor(R.color.colorPrimary)
                .setHint("Please write your comment here...")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(BookDetailActivity.this)
                .show();

    }

    private void getDetailFood(String foodId) {
        book.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentBook =dataSnapshot.getValue(Book.class);
                Picasso.with(getBaseContext()).load(currentBook.getImage()).into(book_image);
                tvBookName.setText(currentBook.getTitle());
                tvAuthor.setText("Author : " + currentBook.getAuthor());
                tvISBN.setText("ISBN : " + currentBook.getiSBN());
                tvPageCount.setText("Page count : " + currentBook.getPageCount());
                tvPublishDate.setText("Publish date : " + currentBook.getPublishDate());
                tvDescription.setText("" + currentBook.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onPositiveButtonClicked(int value, String comments) {
        //Get rating and Upload to FireBase
        final Rating rating= new Rating(Common.currentUser.getPhone(),
                bookId,
                String.valueOf(value),
                comments);
        ratingTable.child(Common.currentUser.getPhone()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(Common.currentUser.getPhone()).exists())
                {
                    //Remove old value

                    ratingTable.child(Common.currentUser.getPhone()).removeValue();
                    //Update new Table
                    ratingTable.child(Common.currentUser.getPhone()).setValue(rating);

                }
                else
                {
                    ratingTable.child(Common.currentUser.getPhone()).setValue(rating);
                }
                Toast.makeText(BookDetailActivity.this, "Thank you for your rating!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onNegativeButtonClicked() {

    }
}

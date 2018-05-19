package com.example.sardorbek.ptototypes.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.sardorbek.ptototypes.Model.new_requests.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sardorbek on 3/29/18.
 */

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "prototype.db";
    private static final int DB_VER = 2;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCarts() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"BookId", "BookName", "Quantity"};
        String sqlTable = "OrderDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        final List<Order> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                result.add(new Order(c.getString(c.getColumnIndex("BookId")),
                        c.getString(c.getColumnIndex("BookName")),
                        c.getString(c.getColumnIndex("Quantity"))));
            } while (c.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(BookId,BookName,Quantity) VALUES('%s','%s','%s');",
                order.getBookId(),
                order.getBookName(),
                order.getQuantity());
        db.execSQL(query);
    }

    public void cleanCart() {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }

    public void addToFavorites(String bookId)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("INSERT INTO Favorites(BookId) VALUES('%s');",bookId);
        db.execSQL(query);
    }

    public void removeFromFavorites(String bookId)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("DELETE FROM Favorites WHERE BookId='%s';",bookId);
        db.execSQL(query);
    }
    public boolean isFavorite(String bookId)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("SELECT * FROM Favorites WHERE BookId='%s';",bookId);
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.getCount()<=0)
        {
            cursor.close();
            return false;
        }
            cursor.close();
            return true;
    }
}

package com.pc1.store;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class Book extends Db {
    public Book(Context context) {
        super(context);
    }
    public Long insert(ContentValues values){

        SQLiteDatabase db=getWrite();
        return db.insert("book",null,values);


    }
    public List<com.pc1.entity.Book> getAll(){
        SQLiteDatabase db=getRead();
        List<com.pc1.entity.Book> books=new ArrayList<>();
        Cursor cursor=db.query("book",new String[]{
                "id","name","autor","price","stock","type","created_at",
                "description"
        },null,null,null,null,"id");
        if(cursor.moveToFirst()){
            do{
                com.pc1.entity.Book book=new com.pc1.entity.Book();
                book.setId(cursor.getInt(0));
                book.setName(cursor.getString(1));
                book.setAutor(cursor.getString(2));
                book.setPrice(cursor.getFloat(3));
                books.add(book);

            }while (cursor.moveToNext());
        }
return books;
    }
    public int delete(int id){
        SQLiteDatabase db=getWritableDatabase();
       return db.delete("book","id=?",new String[]{
          ""+id
       });
    }
    public void update(ContentValues values,int id){
        SQLiteDatabase db=getWrite();
        db.update("book",values,"id=?",new String[]{
                ""+id
        });
    }
    public com.pc1.entity.Book get(int id){
        SQLiteDatabase db=getRead();
        Cursor cursor=db.query("book",
                new String[]{"id,name,autor,price,stock,type,description","has_igv"},
                "id=?",new String[]{
                ""+id
        },null,null,null);
        com.pc1.entity.Book b=new com.pc1.entity.Book();
        if(cursor.moveToFirst()){
            b.setPrice(cursor.getFloat(3));
            b.setAutor(cursor.getString(2));
            b.setName(cursor.getString(1));
            b.setId(cursor.getInt(0));
            b.setDescription(cursor.getString(6));
            b.setStock(cursor.getInt(4));
            b.setType(cursor.getInt(5));
            b.setHas_igv(cursor.getInt(7));
        }
        return b;
    }
}

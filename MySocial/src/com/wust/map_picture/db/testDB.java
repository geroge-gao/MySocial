package com.wust.map_picture.db;

import java.util.List;

import com.example.mysocial.R;

import android.app.Activity;
import android.os.Bundle;

public class testDB extends Activity {
	


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
         
         pictureDAO pic=new pictureDAO(testDB.this);
       }
}

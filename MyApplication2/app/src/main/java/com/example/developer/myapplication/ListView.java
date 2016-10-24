package com.example.developer.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * Created by Developer on 22-Oct-16.
 */
public class ListView extends Activity  {



    public  ArrayAdapter<String> listView(Context context, String[]data) {

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(context,R.layout.view,R.id.textView2,data);
        return arrayAdapter;
    }
}

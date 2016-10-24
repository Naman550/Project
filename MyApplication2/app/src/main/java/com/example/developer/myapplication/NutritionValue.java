package com.example.developer.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NutritionValue extends AppCompatActivity {


    String dataAll="",api;
    List<String> nutritionDetails = new ArrayList<String>();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_value);
        Intent intent = getIntent();
        String ndbno = intent.getStringExtra("ndbno");
        api = "http://api.nal.usda.gov/ndb/reports/?format=xml&ndbno="+ndbno+"&type=b&api_key=iOIKNSIzFXbKKdRDZv9zwwYePgJFy4gb5emFxsEI";


        Toast.makeText(this,"Toast"+ndbno,Toast.LENGTH_SHORT).show();
        textView=(TextView)findViewById(R.id.nTextView);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    HttpConnect httpConnect = new HttpConnect();
                    InputStream inputStream = httpConnect.connect(api);
                    XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                    XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();

                    xmlPullParser.setInput(inputStream, null);
                    int eventType = xmlPullParser.getEventType();

                    while (eventType != xmlPullParser.END_DOCUMENT) {

                        if (eventType == xmlPullParser.START_DOCUMENT) {

                            eventType = xmlPullParser.next();

                        } else if (eventType == xmlPullParser.START_TAG) {
                            if (xmlPullParser.getName().equals("nutrient")) {

                                if (xmlPullParser.getAttributeValue(null, "name").equals("Energy")) {
                                    nutritionDetails.add("Energy- " + xmlPullParser.getAttributeValue(null, "value") + "" + xmlPullParser.getAttributeValue(null, "unit")+"\n");
                                } else if (xmlPullParser.getAttributeValue(null, "name").equals("Protein")) {
                                    nutritionDetails.add("Protein- " + xmlPullParser.getAttributeValue(null, "value") + "" + xmlPullParser.getAttributeValue(null, "unit")+"\n");
                                } else if (xmlPullParser.getAttributeValue(null, "name").equals("Carbohydrate, by difference")) {
                                    nutritionDetails.add("Carbohydrate- " + xmlPullParser.getAttributeValue(null, "value") + "" + xmlPullParser.getAttributeValue(null, "unit")+"\n");
                                } else if (xmlPullParser.getAttributeValue(null, "name").equals("Fiber, total dietary")) {
                                    nutritionDetails.add("Fiber- " + xmlPullParser.getAttributeValue(null, "value") + "" + xmlPullParser.getAttributeValue(null, "unit")+"\n");
                                } else if (xmlPullParser.getAttributeValue(null, "name").equals("Sugars, total")) {
                                    nutritionDetails.add("Sugars- " + xmlPullParser.getAttributeValue(null, "value") + "" + xmlPullParser.getAttributeValue(null, "unit")+"\n");
                                }
                                eventType = xmlPullParser.next();
                            }
                            eventType = xmlPullParser.next();
                        } else if (eventType == xmlPullParser.END_TAG) {
                            eventType = xmlPullParser.next();
                        } else if (eventType == xmlPullParser.END_DOCUMENT) {
                            eventType = xmlPullParser.next();
                        } else if (eventType == xmlPullParser.TEXT) {
                            eventType = xmlPullParser.next();
                        }
                    }
                    for(String nutritionValue: nutritionDetails){
                        dataAll +=nutritionValue;

                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(dataAll);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}

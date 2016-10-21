package com.example.developer.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String api1;
    InputStream inputStream = null;
    ArrayList<String> itemDetails = new ArrayList<String>();
    static int counter = 0;
    String allData="";
    EditText editText;
    Button button;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = editText.getText().toString();
                text=text.replace(" ","");
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
                show();
            }
        });
    }
        public void show(){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    counter=0;
                    api1 = "http://api.nal.usda.gov/ndb/search/?format=xml&q="+text+"&max=30&api_key=iOIKNSIzFXbKKdRDZv9zwwYePgJFy4gb5emFxsEI";
                    HttpConnect httpConnect = new HttpConnect();
                    inputStream = httpConnect.connect(api1);

                    XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setInput(inputStream, null);

                    int eventType = myparser.getEventType();

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_DOCUMENT) {
                            System.out.println("Start document");
                            eventType = myparser.next();
                            System.out.println("" + eventType);
                            System.out.println("" + (eventType == XmlPullParser.START_TAG));
                            System.out.println("" + (myparser.getName().equals("list")));


                        } else if (eventType == XmlPullParser.START_TAG) {
                            if (myparser.getName().equals("list")) {
                                eventType = myparser.next();
                                eventType = myparser.next();
                                Log.e("list", "tag");
                                System.out.println("0" + (eventType != XmlPullParser.END_DOCUMENT));
                                System.out.println("1" + (eventType == XmlPullParser.START_TAG));
                                //   System.out.println("2" + (myparser.getName().equals("item")));

                            }
                            if (myparser.getName().equals("item")) {
                                counter += 1;
                                itemDetails.add(myparser.getName() + counter);
                                eventType = myparser.next();
                                Log.e("item", "item");
                            } else if (myparser.getName().equals("group")) {
                                itemDetails.add(myparser.getName());
                                eventType = myparser.next();
                                if (eventType == XmlPullParser.TEXT) {
                                    itemDetails.add(myparser.getText());
                                    eventType = myparser.next();
                                    if (eventType == XmlPullParser.END_TAG) {
                                        eventType = myparser.next();
                                    }
                                }
                            } else if (myparser.getName().equals("name")) {
                                itemDetails.add(myparser.getName());
                                eventType = myparser.next();
                                if (eventType == XmlPullParser.TEXT) {
                                    itemDetails.add(myparser.getText());
                                    eventType = myparser.next();
                                    if (eventType == XmlPullParser.END_TAG) {
                                        eventType = myparser.next();
                                        //itemDetails.add("\n");
                                    }
                                }
                            } else if (myparser.getName().equals("ndbno")) {
                                itemDetails.add(myparser.getName());
                                eventType = myparser.next();
                                if (eventType == XmlPullParser.TEXT) {
                                    itemDetails.add(myparser.getText());
                                    eventType = myparser.next();
                                    if (eventType == XmlPullParser.END_TAG) {
                                        eventType = myparser.next();
                                        itemDetails.add("\n\n\n");
                                    }
                                }
                            }
                        } else if (eventType == XmlPullParser.END_TAG) {
                            eventType = myparser.next();
                            Log.e("End123", " TAG");
                        } else if (eventType == XmlPullParser.TEXT) {
                            eventType = myparser.next();
                            Log.e("TEXT - ", " TEXT");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("naman1234", "" + e);

                }
                for (String data : itemDetails) {
                    System.out.println(data);
                    allData += data;
                }


                runOnUiThread(new Runnable() {
                    public void run() {

                        TextView textView = (TextView) findViewById(R.id.textView);
                        textView.setText(" ");
                        textView.setText(allData);
                        itemDetails.clear();
                        itemDetails.removeAll(itemDetails);
                    }
                });


            }
        });

        thread.start();
    }




    }



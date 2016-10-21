package com.example.developer.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String api1 = "http://api.nal.usda.gov/ndb/search/?format=xml&q=apple&max=5&api_key=iOIKNSIzFXbKKdRDZv9zwwYePgJFy4gb5emFxsEI";
    InputStream inputStream = null;
    ArrayList<String> itemDetails = new ArrayList<String>();
    static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    HttpConnect httpConnect = new HttpConnect();
                    inputStream = httpConnect.connect(api1);

                    XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setInput(inputStream, null);

                    int eventType = myparser.getEventType();

                    Log.e("na-man", "" + XmlPullParser.START_DOCUMENT);

                    while (eventType != XmlPullParser.END_DOCUMENT) {

//                        if(eventType == XmlPullParser.START_DOCUMENT) {
//                            System.out.println("Start document");
//                        }
//                        else if(eventType == XmlPullParser.START_TAG) {
//                               System.out.println("Start tag "+myparser.getName());
//
//                        }
//                        else if(eventType == XmlPullParser.END_TAG) {
//                              System.out.println("End tag "+myparser.getName());
//                        }
//                        else if(eventType == XmlPullParser.TEXT) {
//                              System.out.println("Text "+myparser.getText());
//                        }
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
                                System.out.println("2" + (myparser.getName().equals("item")));

                            }
                            if (myparser.getName().equals("item")) {
                                counter += 1;
                                itemDetails.add(myparser.getName() + counter);
                                eventType = myparser.next();
                                Log.e("item", "item");
                            }
                           else if (myparser.getName().equals("group")) {
                                itemDetails.add(myparser.getName());
                                eventType = myparser.next();
                                if (eventType == XmlPullParser.TEXT) {
                                    itemDetails.add(myparser.getText());
                                    eventType = myparser.next();
                                    if (eventType == XmlPullParser.END_TAG) {
                                        eventType = myparser.next();
                                    }
                                }
                            }
                          else  if (myparser.getName().equals("name")) {
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
                            }
                          else  if (myparser.getName().equals("ndbno")) {
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
                for (String ar : itemDetails) {
                    System.out.println(ar);

                }
            }
        });

        thread.start();

    }

}

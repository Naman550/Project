package com.example.developer.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String api1, allData1[], allData = "", text;
    ArrayAdapter<String> data;
    InputStream inputStream = null;
    ArrayList<String> itemDetails = new ArrayList<String>();
    static int counter = 0;
    EditText editText;
    Button button;
    TextView textView;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text = editText.getText().toString();
                text = text.replace(" ", "%20");
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
                show();
            }
        });
    }

    public void show() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
<<<<<<< HEAD

                    counter = 0;
                    allData = "";
                    itemDetails.clear();
                    api1 = "http://api.nal.usda.gov/ndb/search/?format=xml&q=" + text + "&max=30&api_key=iOIKNSIzFXbKKdRDZv9zwwYePgJFy4gb5emFxsEI";
=======
                    allData="";
                    counter=0;
                    api1 = "http://api.nal.usda.gov/ndb/search/?format=xml&q="+text+"&max=30&api_key=iOIKNSIzFXbKKdRDZv9zwwYePgJFy4gb5emFxsEI";
>>>>>>> 6abef508402fa10eca15a6a1fcbb154e2cfcb7c5
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

                            }
                            if (myparser.getName().equals("item")) {
                                counter += 1;
                                itemDetails.add(myparser.getName() + "-" + counter + "\n");
                                eventType = myparser.next();
                                Log.e("item", "item");
                            } else if (myparser.getName().equals("group")) {
                                itemDetails.add(myparser.getName() + "-");
                                eventType = myparser.next();
                                if (eventType == XmlPullParser.TEXT) {
                                    itemDetails.add(myparser.getText() + "\n");
                                    eventType = myparser.next();
                                    if (eventType == XmlPullParser.END_TAG) {
                                        eventType = myparser.next();
                                    }
                                }
                            } else if (myparser.getName().equals("name")) {
                                itemDetails.add(myparser.getName() + "-");
                                eventType = myparser.next();
                                if (eventType == XmlPullParser.TEXT) {
                                    itemDetails.add(myparser.getText() + "\n");
                                    eventType = myparser.next();
                                    if (eventType == XmlPullParser.END_TAG) {
                                        eventType = myparser.next();
                                        //itemDetails.add("\n");
                                    }
                                }
                            } else if (myparser.getName().equals("ndbno")) {
                                itemDetails.add(myparser.getName() + "-");
                                eventType = myparser.next();
                                if (eventType == XmlPullParser.TEXT) {
                                    itemDetails.add(myparser.getText() + "\n");
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

               final String allData2[] = allData.split("\n\n\n");

                // Log.e("wip",allData1[2]);
                runOnUiThread(new Runnable() {
                    public void run() {
                        com.example.developer.myapplication.ListView listViewClass = new com.example.developer.myapplication.ListView();
                        data = listViewClass.listView(MainActivity.this, allData2);
                        listView.setAdapter(data);
                    }
                });
            }
        });

        thread.start();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String allData2[] = allData.split("\n\n\n");
                Toast.makeText(MainActivity.this, allData2[position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,NutritionValue.class);
                String data = allData2[position];
                String dataArray[] = data.split("ndbno-");
                data = dataArray[1];
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
                intent.putExtra("ndbno",data);
                startActivity(intent);
            }
        });
    }
}



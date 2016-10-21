package jacobrawlinson.assignmentapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodDetails extends Activity{

    public String ItemID;
    public String selectedDate;
    public boolean fromDiary;
    public String email;
    private ProgressDialog mProgressDialog;

    xmlDetailedPullParser.xmlItem foodDetails;

    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        ItemID = getIntent().getStringExtra("DatabaseNumber");
        TextView dbno = (TextView)findViewById(R.id.textViewDBNO);
        TextView dateView = (TextView)findViewById(R.id.dateTextView);
        dateView.setTextColor(Color.argb(100, 0, 0, 0));
        dbno.setTextColor(Color.argb(100, 0, 0, 0));
        dbno.setText(ItemID);
        Button addButton = (Button)findViewById(R.id.diaryAddItemButton);
        if (getIntent().getBooleanExtra("fromSearch", false)) {
            ParseDetailedXml xmlParser = new ParseDetailedXml();
            xmlParser.execute();
            showProgressDialog();
        }
        else{
            //getInfoFromRecent();
        }

        fromDiary = getIntent().getBooleanExtra("fromDiary", false);
        if (fromDiary){
            selectedDate = getIntent().getStringExtra("date");
            dateView.setText("Selected Date: " + selectedDate);
        }else{
            addButton.setVisibility(View.GONE);
            dateView.setVisibility(View.GONE);
        }
    }

    public class ParseDetailedXml extends AsyncTask<String,String,String> {
        String URL = "http://api.nal.usda.gov/ndb/reports/?format=xml&ndbno=" + ItemID + "&type=b&api_key=iOIKNSIzFXbKKdRDZv9zwwYePgJFy4gb5emFxsEI";
        xmlDetailedPullParser XMLPullParser = new xmlDetailedPullParser();
        public List<xmlDetailedPullParser.xmlItem> Items = null;

        @Override
        protected String doInBackground(String... params) {
            httpConnect connection = new httpConnect();
            InputStream stream = null;
            try {
                stream = connection.getXMLFromUrl(URL);
                Items = XMLPullParser.parse(stream);
                //System.out.println("Naman - "+Items);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            foodDetails = Items.get(0);
            TextView name = (TextView)findViewById(R.id.foodTitle);
            TextView caloriesValue = (TextView)findViewById(R.id.CaloriesValue);
            TextView proteinValue = (TextView)findViewById(R.id.ProteinValue);
            TextView carbohydrateValue = (TextView)findViewById(R.id.CarbohydrateValue);
            TextView sugarsValue = (TextView)findViewById(R.id.SugarsValue);
            TextView fatValue = (TextView)findViewById(R.id.FatValue);
            TextView saturatesValue = (TextView)findViewById(R.id.SaturatesValue);
            TextView fibreValue = (TextView)findViewById(R.id.FibreValue);
            TextView saltValue = (TextView)findViewById(R.id.SaltValue);

            name.setText(foodDetails.foodName);
            caloriesValue.setText(foodDetails.calories + "kcal");
            proteinValue.setText(foodDetails.protein + "g");
            carbohydrateValue.setText(foodDetails.carbohydrate + "g");
            sugarsValue.setText(foodDetails.sugars + "g");
            fatValue.setText(foodDetails.fat + "g");
            saturatesValue.setText(foodDetails.saturates + "g");
            fibreValue.setText(foodDetails.fibre + "g");
            saltValue.setText(foodDetails.salt + "g");

            hideProgressDialog();
        }
    }

    public void addItemToDiary(View view){
        if(getIntent().getBooleanExtra("fromSearch", false)) {
            email = getIntent().getStringExtra("email");
            int foodID = 0;
            myDB = new DatabaseHelper(this);
            myDB.addFood(email, foodDetails.foodName, foodDetails.databaseNumber, foodDetails.calories, foodDetails.protein, foodDetails.carbohydrate, foodDetails.sugars, foodDetails.fat, foodDetails.saturates, foodDetails.fibre, foodDetails.salt);
            foodID = myDB.getID(foodDetails.databaseNumber);
            if (foodID !=0) {
                myDB.addItem(email, selectedDate, foodID);
                Toast.makeText(getApplicationContext(), foodDetails.foodName + " Added", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading Food Info");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

}

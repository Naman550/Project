package jacobrawlinson.assignmentapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class Summary extends Activity {
    public String gender = "male";
    public int caloriesGDA;
    public int proteinGDA;
    public int carbohydrateGDA;
    public int sugarsGDA;
    public int fatGDA;
    public int saturatesGDA;
    public int fibreGDA;
    public int saltGDA;
    public String selectedDate;

    DatabaseHelper myDB;

    public ToggleButton genderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        TextView dateView = (TextView)findViewById(R.id.dateView);

        Intent diaryIntent = getIntent();
        genderButton = (ToggleButton)findViewById(R.id.genderButton);
        selectedDate = diaryIntent.getStringExtra("DATE");

        dateView.setText("Summary for: " + selectedDate);

        genderButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gender = "female";
                    populateTable();
                } else {
                    gender = "male";
                    populateTable();
                }
            }
        });

        populateTable();
    }



    private void populateTable(){

        myDB = new DatabaseHelper(this);
        float[] totals = myDB.getTotals(selectedDate, getIntent().getStringExtra("email"));

        if (gender.equals("male")){
        caloriesGDA = 2500;
        proteinGDA = 55;
        carbohydrateGDA = 300;
        sugarsGDA = 120;
        fatGDA = 95;
        saturatesGDA = 30;
        fibreGDA = 24;
        saltGDA = 6;
    }
    else{
        caloriesGDA = 2000;
        proteinGDA = 45;
        carbohydrateGDA = 230;
        sugarsGDA = 90;
        fatGDA = 70;
        saturatesGDA = 20;
        fibreGDA = 24;
        saltGDA = 6;
    }
        TextView row1col1 = (TextView)findViewById(R.id.row1col1);
        TextView row2col1 = (TextView)findViewById(R.id.row2col1);
        TextView row3col1 = (TextView)findViewById(R.id.row3col1);
        TextView row4col1 = (TextView)findViewById(R.id.row4col1);
        TextView row5col1 = (TextView)findViewById(R.id.row5col1);
        TextView row6col1 = (TextView)findViewById(R.id.row6col1);
        TextView row7col1 = (TextView)findViewById(R.id.row7col1);
        TextView row8col1 = (TextView)findViewById(R.id.row8col1);
        TextView row1col2 = (TextView)findViewById(R.id.row1col2);
        TextView row2col2 = (TextView)findViewById(R.id.row2col2);
        TextView row3col2 = (TextView)findViewById(R.id.row3col2);
        TextView row4col2 = (TextView)findViewById(R.id.row4col2);
        TextView row5col2 = (TextView)findViewById(R.id.row5col2);
        TextView row6col2 = (TextView)findViewById(R.id.row6col2);
        TextView row7col2 = (TextView)findViewById(R.id.row7col2);
        TextView row8col2 = (TextView)findViewById(R.id.row8col2);
        TextView row1col3 = (TextView)findViewById(R.id.row1col3);
        TextView row2col3 = (TextView)findViewById(R.id.row2col3);
        TextView row3col3 = (TextView)findViewById(R.id.row3col3);
        TextView row4col3 = (TextView)findViewById(R.id.row4col3);
        TextView row5col3 = (TextView)findViewById(R.id.row5col3);
        TextView row6col3 = (TextView)findViewById(R.id.row6col3);
        TextView row7col3 = (TextView)findViewById(R.id.row7col3);
        TextView row8col3 = (TextView)findViewById(R.id.row8col3);

        row1col1.setText("Calories");
        row2col1.setText("Protein");
        row3col1.setText("Carbohydrate");
        row4col1.setText("Sugars");
        row5col1.setText("Fat");
        row6col1.setText("Saturates");
        row7col1.setText("Fibre");
        row8col1.setText("Salt");

        row1col2.setText(Integer.toString(caloriesGDA));
        row2col2.setText(Integer.toString(proteinGDA));
        row3col2.setText(Integer.toString(carbohydrateGDA));
        row4col2.setText(Integer.toString(sugarsGDA));
        row5col2.setText(Integer.toString(fatGDA));
        row6col2.setText(Integer.toString(saturatesGDA));
        row7col2.setText(Integer.toString(fibreGDA));
        row8col2.setText(Integer.toString(saltGDA));

        row1col3.setText(Float.toString(totals[0]));
        row2col3.setText(Float.toString(totals[1]));
        row3col3.setText(Float.toString(totals[2]));
        row4col3.setText(Float.toString(totals[3]));
        row5col3.setText(Float.toString(totals[4]));
        row6col3.setText(Float.toString(totals[5]));
        row7col3.setText(Float.toString(totals[6]));
        row8col3.setText(Float.toString(totals[7]));

        if (caloriesGDA < totals[0]){
            row1col3.setTextColor(Color.rgb(255, 0, 0));
        }
        else{row1col3.setTextColor(Color.rgb(0, 0, 0));}
        if (proteinGDA < totals[1]){
            row2col3.setTextColor(Color.rgb(255, 0, 0));
        }
        else{row2col3.setTextColor(Color.rgb(0, 0, 0));}
        if (carbohydrateGDA < totals[2]){
            row3col3.setTextColor(Color.rgb(255, 0, 0));
        }
        else{row3col3.setTextColor(Color.rgb(0, 0, 0));}
        if (sugarsGDA < totals[3]){
            row4col3.setTextColor(Color.rgb(255, 0, 0));
        }
        else{row4col3.setTextColor(Color.rgb(0, 0, 0));}
        if (fatGDA < totals[4]){
            row5col3.setTextColor(Color.rgb(255, 0, 0));
        }
        else{row5col3.setTextColor(Color.rgb(0, 0, 0));}
        if (saturatesGDA < totals[5]){
            row6col3.setTextColor(Color.rgb(255, 0, 0));
        }
        else{row6col3.setTextColor(Color.rgb(0, 0, 0));}
        if (fibreGDA < totals[6]){
            row7col3.setTextColor(Color.rgb(255, 0, 0));
        }
        else{row7col3.setTextColor(Color.rgb(0, 0, 0));}
        if (saltGDA < totals[7]){
            row8col3.setTextColor(Color.rgb(255, 0, 0));
        }
        else{row8col3.setTextColor(Color.rgb(0, 0, 0));}

        TextView gdaInfo = (TextView)findViewById(R.id.gdaInfo);
        gdaInfo.setText("Showing Guideline Daily Amounts for a " + gender);

    }

}

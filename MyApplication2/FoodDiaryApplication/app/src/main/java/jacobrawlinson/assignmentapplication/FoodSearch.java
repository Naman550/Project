package jacobrawlinson.assignmentapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodSearch extends Activity{

    public DatabaseHelper dbHelper;
    static String xml = "";
    public ListView recentList;
    public ListView searchList;
    public EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);

        recentList = (ListView)findViewById(R.id.listViewRecent);
        searchList = (ListView)findViewById(R.id.listViewSearch);
        searchText = (EditText)findViewById(R.id.searchText);




        dbHelper = new DatabaseHelper(this);
        populateRecent();
        dbHelper.close();
    }

    public class ParseXml extends AsyncTask<String,String,String>{


        String query = searchText.getText().toString();
        String newQuery = query.replaceAll(" ", "+");
        String URL = "http://api.nal.usda.gov/ndb/search/?format=xml&q=" + newQuery + "&max=50&api_key=iOIKNSIzFXbKKdRDZv9zwwYePgJFy4gb5emFxsEI";
        xmlPullParser XMLPullParser = new xmlPullParser();
        public List<xmlPullParser.xmlItem> Items = null;

        @Override
        protected String doInBackground(String... params) {
            httpConnect connection = new httpConnect();
            InputStream stream = null;
            try {
                stream = connection.getXMLFromUrl(URL);
                Items = XMLPullParser.parse(stream);

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
            String[] from = new String[]{"name", "group", "dbno"};
            int[] to = new int[]{R.id.resultFoodName, R.id.resultFoodGroup, R.id.resultDBNO};

            List<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();

            for (xmlPullParser.xmlItem foodItem: Items) {
                HashMap<String, String> entry = new HashMap<String, String>();
                entry.put("name", foodItem.foodName);
                entry.put("group", foodItem.group);
                entry.put("dbno", foodItem.databaseNumber);

                rows.add(entry);
            }
            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), rows, R.layout.search_results, from, to);
            searchList.setAdapter(adapter);

            searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(FoodSearch.this, FoodDetails.class);
                    intent.putExtra("fromDiary", getIntent().getBooleanExtra("fromDiary", false));
                    if (getIntent().getBooleanExtra("fromDiary", false)) {
                        intent.putExtra("date", getIntent().getStringExtra("date"));
                        intent.putExtra("email", getIntent().getStringExtra("email"));
                    }
                    xmlPullParser.xmlItem clickedItem = Items.get(position);
                    String DatabaseID = clickedItem.databaseNumber;
                    intent.putExtra("DatabaseNumber", DatabaseID);
                    intent.putExtra("fromSearch", true);
                    startActivity(intent);
                }
            });
        }
    }

    public void populateRecent(){
        Cursor cursor = dbHelper.getRecent();
        String[] from = new String[]{dbHelper.KEY_NAME, dbHelper.KEY_CALORIES};
        int[] to = new int[]{R.id.listViewName, R.id.listViewCal};

        SimpleCursorAdapter sca = new SimpleCursorAdapter(getBaseContext(), R.layout.food_layout, cursor, from, to, 0);
        recentList.setAdapter(sca);
    }

    public void searchFoods(View view){
        recentList.setVisibility(View.GONE);
        searchList.setVisibility(View.VISIBLE);
        ParseXml xmlParser = new ParseXml();
        xmlParser.execute();
    }
}

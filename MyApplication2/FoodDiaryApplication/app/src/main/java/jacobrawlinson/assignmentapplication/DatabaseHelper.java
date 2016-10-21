package jacobrawlinson.assignmentapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wolf Gevaudan on 02/12/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String dbName = "fooddiary.db";
    public static final String tableName = "diary_table";
    public static final String foodTableName = "food_table";
    public String KEY_ID = "_id";
    public String KEY_USER = "USER";
    public String KEY_NAME = "NAME";
    public String KEY_CALORIES = "CALORIES";





    public DatabaseHelper(Context context) {
        super(context, dbName, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + tableName + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, USER TEXT, DATE DATE, FOOD INTEGER, FOREIGN KEY (FOOD) REFERENCES food_table(_id))");
        db.execSQL("create table if not exists " + foodTableName + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, USER TEXT, NAME TEXT, FOODID TEXT, CALORIES TEXT, PROTEIN TEXT, CARBOHYDRATE TEXT, SUGARS TEXT, FAT TEXT, SATURATES TEXT, FIBRE TEXT, SALT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        db.execSQL("DROP TABLE IF EXISTS " + foodTableName);
        db.execSQL("create table " + tableName + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, USER TEXT, DATE DATE, FOOD INTEGER, FOREIGN KEY (FOOD) REFERENCES food_table(_id))");
        db.execSQL("create table " + foodTableName + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, USER TEXT, NAME TEXT, FOODID TEXT, CALORIES TEXT, PROTEIN TEXT, CARBOHYDRATE TEXT, SUGARS TEXT, FAT TEXT, SATURATES TEXT, FIBRE TEXT, SALT TEXT)");
    }

    public boolean addFood(String user, String name, String foodID, String calories, String protein, String carbohydrate, String sugars, String fat, String saturates, String fibre, String salt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USER", user);
        contentValues.put("NAME", name);
        contentValues.put("FOODID", foodID);
        contentValues.put("CALORIES", calories);
        contentValues.put("PROTEIN", protein);
        contentValues.put("CARBOHYDRATE", carbohydrate);
        contentValues.put("SUGARS", sugars);
        contentValues.put("FAT", fat);
        contentValues.put("SATURATES", saturates);
        contentValues.put("FIBRE", fibre);
        contentValues.put("SALT", salt);
        long result = db.insert(foodTableName, null, contentValues);
        return result != -1;
    }

    public boolean addItem(String user, String date, int food){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USER", user);
        contentValues.put("DATE", date);
        contentValues.put("FOOD", food);
        long result = db.insert (tableName, null, contentValues);
        return result != -1;
    }

    public Cursor getRecent(){
        String[] colNames = new String[]{KEY_ID, KEY_USER, KEY_NAME, KEY_CALORIES};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.query(true, foodTableName, colNames, null, null, null, null, KEY_ID + " Desc", "10");
        if (result != null){
            result.moveToFirst();
        }
        return result;
    }

    public void UpdateDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        db.execSQL("DROP TABLE IF EXISTS " + foodTableName);
        db.execSQL("create table " + tableName + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, USER TEXT, DATE DATE, FOOD INTEGER, FOREIGN KEY (FOOD) REFERENCES food_table(_id))");
        db.execSQL("create table " + foodTableName + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, USER TEXT, NAME TEXT, FOODID TEXT, CALORIES TEXT, PROTEIN TEXT, CARBOHYDRATE TEXT, SUGARS TEXT, FAT TEXT, SATURATES TEXT, FIBRE TEXT, SALT TEXT)");
    }

    public Cursor getDiary(String date, String email){
        String sql = "SELECT " + tableName +  "._id, NAME, CALORIES FROM " + tableName + " INNER JOIN " + foodTableName + " ON " + tableName + ".FOOD = " + foodTableName + "._id where DATE = '" + date + "' AND " + tableName + ".USER = '" + email + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(sql,null);
        //Cursor result = db.query(true, tableName, colNames, null, null, null, null, KEY_ID + " Desc", null);
        if (result != null){
            result.moveToFirst();
        }
        return result;
    }

    public int getID(String databaseNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        int foodID = 0;
        Cursor cursor =  db.rawQuery("SELECT _id from " + foodTableName + " WHERE FOODID = '" + databaseNumber + "'", null);
        while (cursor.moveToNext()) {
            foodID = cursor.getInt(0);
        }
        return foodID;
    }

    public float[] getTotals(String date, String user){
        float calTotal = 0;
        float proTotal = 0;
        float carTotal = 0;
        float sugTotal = 0;
        float fatTotal = 0;
        float satTotal = 0;
        float fibTotal = 0;
        float salTotal = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor total = db.rawQuery("SELECT CALORIES, PROTEIN, CARBOHYDRATE, SUGARS, FAT, SATURATES, FIBRE, SALT FROM " + tableName + " INNER JOIN " + foodTableName + " ON " + tableName + ".FOOD = " + foodTableName + "._id where DATE = '" + date + "' AND " + tableName + ".USER = '" + user + "'", null);
        if (total.moveToFirst()){
            do{
                calTotal = calTotal + Float.valueOf(total.getString(total.getColumnIndex("CALORIES")));
                proTotal = proTotal + Float.valueOf(total.getString(total.getColumnIndex("PROTEIN")));
                carTotal = carTotal + Float.valueOf(total.getString(total.getColumnIndex("CARBOHYDRATE")));
                sugTotal = sugTotal + Float.valueOf(total.getString(total.getColumnIndex("SUGARS")));
                fatTotal = fatTotal + Float.valueOf(total.getString(total.getColumnIndex("FAT")));
                satTotal = satTotal + Float.valueOf(total.getString(total.getColumnIndex("SATURATES")));
                fibTotal = fibTotal + Float.valueOf(total.getString(total.getColumnIndex("FIBRE")));
                salTotal = salTotal + Float.valueOf(total.getString(total.getColumnIndex("SALT")));
            }while(total.moveToNext());
        }
        total.close();

        float[] totalList = new float[]{calTotal, proTotal, carTotal, sugTotal, fatTotal, satTotal, fibTotal, salTotal};
        return totalList;
    }
}

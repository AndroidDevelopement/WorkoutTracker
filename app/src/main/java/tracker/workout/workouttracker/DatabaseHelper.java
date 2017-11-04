package tracker.workout.workouttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by krisfoster on 04/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "workout_tracker.db";
    public static final String EXERCISES_TABLE = "exercises";
    public static final String WORKOUT_TABLE = "workout";
    private Context context;
    public static String strSeparator = "__,__";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 21);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String[] resExercises = context.getResources().getStringArray(R.array.exercises);
        String[] resCategories = context.getResources().getStringArray(R.array.categories);

        for (String string : context.getResources().getStringArray(R.array.create_statements)) {
            db.execSQL(string);
        }

        for (int i = 0; i < resExercises.length; i++) {
            String[] string = resExercises[i].split(":");
            String categoryName = resCategories[Integer.parseInt(string[0])];
            String exerciseName = string[1];
            ContentValues cv = new ContentValues();
            cv.put("name", exerciseName);
            cv.put("category", categoryName);
            db.insert("exercises", null, cv);
        }



//        ContentValues cv = new ContentValues();
//        cv.put("name", name);
//        cv.put("exercises", convertArrayToString(arr));
//        long result = db.insert("workout", null, cv);

//        db.execSQL("CREATE TABLE "+ EXERCISE_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR (50) UNIQUE)");
//        db.execSQL("CREATE TABLE " + CATEGORY_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR (50) UNIQUE)");
//        db.execSQL("CREATE TABLE " + WORKOUT_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR (50), exercises VARCHAR (1000))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_TABLE);
        onCreate(db);
    }

    public boolean insertWorkout(String name, String[] exercises) {

        ArrayList<String> exercisesArr = new ArrayList();

        for (int i = 0; i < exercises.length; i++) {
            exercisesArr.add(exercises[i].toString());
        }

        String[] arr = exercisesArr.toArray(new String[exercisesArr.size()]);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("exercises", convertArrayToString(arr));
        long result = db.insert("workout", null, cv);

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
    public static String[] convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
    }

    public String[] getWorkoutNames() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select name from workout", null);
        List<String> e = new ArrayList<String>();

        if(c.getCount() > 0) {
            c.moveToFirst();
        }

        while(!c.isLast()) {
            e.add(c.getString(0));
            c.moveToNext();
        }

        e.add(c.getString(0));



        return e.toArray(new String[e.size()]);

    }

    public String[] getWorkoutExercises(String workoutName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select name from workouts Where name like '" + workoutName + "'", null);
        List<String> e = new ArrayList<String>();
        String[] ex = {};

        if(c.getCount() > 0) {
            c.moveToFirst();
            String exercisesString = c.getString(0);
            ex = (convertStringToArray(exercisesString));
        }

        return ex;
    }

    public String[] getExercisesForCategory(String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select name from exercises Where category like '" + category + "'", null);
        List<String> e = new ArrayList<String>();

        if(c.getCount() > 0) {
            c.moveToFirst();
        }

        while(!c.isLast()) {
            e.add(c.getString(0));
            c.moveToNext();
        }

        e.add(c.getString(0));

        return e.toArray(new String[e.size()]);

    }
}

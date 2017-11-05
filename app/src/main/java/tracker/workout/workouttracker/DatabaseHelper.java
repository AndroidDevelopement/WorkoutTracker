package tracker.workout.workouttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "workout_tracker.db";
    public static final String EXERCISES_TABLE = "exercises";
    public static final String WORKOUT_TABLE = "workouts";
    public static String strSeparator = "__,__";
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, context.getResources().getInteger(R.integer.database_version));
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_TABLE);
        onCreate(db);
    }

    // Adds a new workout to the Database
    public void insertWorkout(String name, String[] exercises) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        ArrayList<String> exercisesList = new ArrayList();
        String[] workoutExercises;

        for (int i = 0; i < exercises.length; i++) {
            exercisesList.add(exercises[i].toString());
        }

        workoutExercises = exercisesList.toArray(new String[exercisesList.size()]);
        cv.put("name", name);
        cv.put("exercises", convertArrayToString(workoutExercises));
        db.insert("workouts", null, cv);
    }

    // Returns a String array of all of the workout names
    public String[] getWorkoutNames() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select name from workouts", null);
        List<String> e = new ArrayList<String>();

        while (c.moveToNext()) {
            e.add(c.getString(0));
        }

        return e.toArray(new String[e.size()]);
    }

    // Returns a String array of all exercises associated with a workout
    public String[] getWorkoutExercises(String workoutName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select exercises from workouts Where name like '" + workoutName + "'", null);
        String[] ex = {};

        while (c.moveToNext()) {
            ex = (convertStringToArray(c.getString(0)));
        }

        return ex;
    }

    // Returns all exercises associated with a particular category
    public String[] getExercisesForCategory(String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select name from exercises Where category like '" + category + "'", null);
        List<String> e = new ArrayList<String>();

        while (c.moveToNext()) {
            e.add(c.getString(0));
        }

        return e.toArray(new String[e.size()]);
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
}

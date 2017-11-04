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

import tracker.workout.workouttracker.database.table.Category;
import tracker.workout.workouttracker.database.table.Exercise;


/**
 * Created by krisfoster on 04/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "workout_tracker.db";
    public static final String EXERCISE_TABLE = "exercise";
    public static final String CATEGORY_TABLE = "category";
    public static final String WORKOUT_TABLE = "workout";
    private Context context;
    private Category[] categories;

    private Map<Category, List<Exercise>> categoryExercises;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 13);
        this.context = context;
        categoryExercises = new HashMap<Category, List<Exercise>>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String string : context.getResources().getStringArray(R.array.create_statements)) {
            db.execSQL(string);
        }
//        db.execSQL("CREATE TABLE "+ EXERCISE_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR (50) UNIQUE)");
//        db.execSQL("CREATE TABLE " + CATEGORY_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR (50) UNIQUE)");
//        db.execSQL("CREATE TABLE " + WORKOUT_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR (50), exercises VARCHAR (1000))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_TABLE);
        onCreate(db);
    }

    public Category[] getCategories() {
        if (categories == null) {
            String[] resCategories = context.getResources().getStringArray(R.array.categories);

            Category[] categories = new Category[resCategories.length];

            for (int i = 0; i < resCategories.length; i++) {
                categories[i] = new Category(i, resCategories[i]);
            }

            this.categories = categories;
        }
        return categories;
    }

    public Exercise[] getExercises(Category category) {
        if (categoryExercises.isEmpty()) {
            String[] resExercises = context.getResources().getStringArray(R.array.exercises);

            Category[] categories = getCategories();

            for (int i = 0; i < resExercises.length; i++) {
                String[] string = resExercises[i].split(":");

                int categoryId = Integer.parseInt(string[0]);
                Category exerciseCategory = categories[categoryId];

                if (!categoryExercises.containsKey(exerciseCategory)) {
                    categoryExercises.put(exerciseCategory, new ArrayList<Exercise>());
                }

                categoryExercises.get(exerciseCategory).add(new Exercise(i, string[1]));
            }

        }
        List<Exercise> ce = categoryExercises.get(category);

        if (ce == null) {
            return null;
        }

        return ce.toArray(new Exercise[ce.size()]);
    }

    public boolean insertWorkout(String name, Exercise[] exercises) {

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





    public static String strSeparator = "__,__";
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
        Cursor c = db.rawQuery("Select exercises from workout Where name like '" + workoutName + "'", null);
        List<String> e = new ArrayList<String>();
        String[] ex = {};

        if(c.getCount() > 0) {
            c.moveToFirst();
            String exercisesString = c.getString(0);
            ex = (convertStringToArray(exercisesString));
        }

        return ex;
    }
}

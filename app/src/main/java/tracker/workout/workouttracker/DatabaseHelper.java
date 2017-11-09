package tracker.workout.workouttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "workout_tracker.db";
	public static final String EXERCISE_TABLE = "exercise";
	public static final String CATEGORY_TABLE = "category";
	public static final String CATEGORY_EXERCISE_TABLE = "category_exercise";
    public static final String WORKOUT_TABLE = "workout";
    public static final String WORKOUT_EXERCISE_TABLE = "workout_exercise";

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

        // Store categories in to db
        for (int i = 0; i < resCategories.length; i++) {
        	ContentValues cv = new ContentValues();
        	cv.put("name", resCategories[i]);
        	db.insert(CATEGORY_TABLE, null, cv);
		}

		// Store exercises + category_exercise
		for (int i = 0; i < resExercises.length; i++) {
        	String[] exercise = resExercises[i].split(":");
        	int categoryId = Integer.parseInt(exercise[0]);
        	ContentValues cv = new ContentValues();
        	cv.put("name", exercise[1]);
        	db.insert(EXERCISE_TABLE, null, cv);

        	cv.clear();

        	cv.put("categoryId", categoryId);
        	cv.put("exerciseId", i + 1); // AA starts from 1.
			db.insert(CATEGORY_EXERCISE_TABLE, null, cv);
		}

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		reset(db, oldVersion, newVersion);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		reset(db, oldVersion, newVersion);
	}

	private void reset(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_EXERCISE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_EXERCISE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
		onCreate(db);
	}

    public long getExerciseId(String name) {
		SQLiteDatabase db = getWritableDatabase();
		Cursor c = db.query(EXERCISE_TABLE, new String[]{"id"}, "name = '" + name + "'", null, null, null, null);
		if (c.getCount() > 0) {
			c.moveToNext();
			return c.getLong(0);
		}
		return -1;
	}

    // Adds a new workout to the Database
    public void insertWorkout(String name, String[] exercises) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Insert workout
        cv.put("name", name);
        long workoutId = db.insert(WORKOUT_TABLE, null, cv);

        // Insert exercises of the workout
		for (String exercise : exercises) {
			cv.clear();
			cv.put("workoutId", workoutId);
			cv.put("exerciseId", getExerciseId(exercise));
			db.insert(WORKOUT_EXERCISE_TABLE, null, cv);
		}
    }

    // Returns a String array of all of the workout names
    public String[] getWorkoutNames() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM " + WORKOUT_TABLE, null);
        String[] workouts = new String[c.getCount()];

        for (int i = 0; i < c.getCount(); i++) {
        	c.moveToNext();
        	workouts[i] = c.getString(0);
		}

        return workouts;
    }

    // Returns a String array of all exercises associated with a workout
    public String[] getWorkoutExercises(String workoutName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT e.name FROM " + WORKOUT_EXERCISE_TABLE + " we, " + WORKOUT_TABLE + " w, " + EXERCISE_TABLE + " e WHERE we.workoutId = w.id AND we.exerciseId = e.id AND w.name = '" + workoutName + "'", null);
        String[] exercises = new String[c.getCount()];

		for (int i = 0; i < c.getCount(); i++) {
			c.moveToNext();
			exercises[i] = c.getString(0);
			System.out.println(exercises[i]);
		}

        return exercises;
    }

    // Returns all exercises associated with a particular category
    public String[] getExercisesForCategory(String category) {
		SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT e.name FROM " + CATEGORY_EXERCISE_TABLE + " ce, " + EXERCISE_TABLE + " e, " + CATEGORY_TABLE + " c WHERE ce.categoryId = c.id AND c.name = '" + category + "' AND ce.exerciseId = e.id", null);
        String[] exercises = new String[c.getCount()];

		for (int i = 0; i < c.getCount(); i++) {
			c.moveToNext();
			exercises[i] = c.getString(0);
		}

        return exercises;
    }

}
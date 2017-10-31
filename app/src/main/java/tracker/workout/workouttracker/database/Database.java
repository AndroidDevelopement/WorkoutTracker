package tracker.workout.workouttracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tracker.workout.workouttracker.R;
import tracker.workout.workouttracker.database.table.Category;
import tracker.workout.workouttracker.database.table.Exercise;

public class Database {

	private Context context;
	private SQLiteOpenHelper sqLiteOpenHelper;
	private SQLiteDatabase sqLiteDatabase;
	private Category[] categories;
	private Map<Category, List<Exercise>> categoryExercises;

	private class OpenDatabaseTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... voids) {
			sqLiteOpenHelper = new SQLiteOpenHelper(context, context.getString(R.string.database_name), null, context.getResources().getInteger(R.integer.database_version)) {
				@Override
				public void onCreate(SQLiteDatabase db) {
					// Executed when the database is not found or when the method is called from onUpgrade.
					for (String string : context.getResources().getStringArray(R.array.create_statements)) {
						db.execSQL(string);
					}

					ContentValues cv = new ContentValues();

					for (String string : context.getResources().getStringArray(R.array.categories)) {
						cv.put("name", string);
					}

					db.insert("category", null, cv);

					cv = new ContentValues();

					for (String string : context.getResources().getStringArray(R.array.exercises)) {
						cv.put("name", string.split(":")[1]);
					}

					db.insert("exercise", null, cv);
				}

				@Override
				public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
					reset(db);
				}

				@Override
				public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
					reset(db);
				}

				private void reset(SQLiteDatabase db) {
					String[] resWipe = context.getResources().getStringArray(R.array.wipe_database);

					try {
						for (int i = 0; i < resWipe.length; i++) {
							if (i % 2 == 0) {
								db.delete(resWipe[i], null, null); // DELETE ALL ROWS
								continue;
							}
							db.execSQL(resWipe[i]); // DROP table
						}
					} catch (SQLiteException e) {
						System.out.println("Database is already deleted.");
					}
					onCreate(db);
				}
			};

			sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
			return null;
		}
	}

	public Database(final Context context) {
		this.context = context;
		categoryExercises = new HashMap<Category, List<Exercise>>();
	}

	public void open() throws DatabaseAlreadyOpenException {
		if (sqLiteDatabase == null) {
			new OpenDatabaseTask().execute();
		} else {
			throw new DatabaseAlreadyOpenException("DATABASE IS ALREADY OPEN");
		}
	}

	public boolean close() throws DatabaseAlreadyClosedException {
		if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
			sqLiteDatabase.close();
			sqLiteDatabase = null;
			return true;
		}
		throw new DatabaseAlreadyClosedException("DATABASE IS ALREADY CLOSED");
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

}
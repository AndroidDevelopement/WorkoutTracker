package tracker.workout.workouttracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {

	private Context context;
	private SQLiteOpenHelper sqLiteOpenHelper;
	private SQLiteDatabase sqLiteDatabase;

	public Database(final Context context) {
		this.context = context;

		sqLiteOpenHelper = new SQLiteOpenHelper(context, context.getString(R.string.database_name), null, context.getResources().getInteger(R.integer.database_version)) {
			@Override
			public void onCreate(SQLiteDatabase db) {
				// Executed when the database is not found on when the method is called from onUpgrade.
				for (String string : context.getResources().getStringArray(R.array.create_statements)) {
					db.execSQL(string);
				}
			}

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				// If not closed, restart of the application is needed.
				if (db.isOpen()) {
					db.close();
				}

				context.deleteDatabase(context.getString(R.string.database_name));
				onCreate(db);
			}
		};
	}

	public boolean open() throws DatabaseAlreadyOpenException {
		if (sqLiteDatabase == null) {
			sqLiteDatabase = sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
			return sqLiteDatabase.isOpen();
		}
		throw new DatabaseAlreadyOpenException("DATABASE IS ALREADY OPEN");
	}

	public boolean close() throws DatabaseAlreadyClosedException {
		if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
			sqLiteDatabase.close();
			return true;
		}
		throw new DatabaseAlreadyClosedException("DATABASE IS ALREADY CLOSED");
	}

	// W.I.P Create helper methods (get data from table, insert workout, etc...)

}
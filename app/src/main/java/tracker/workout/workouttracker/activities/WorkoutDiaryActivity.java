package tracker.workout.workouttracker.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import tracker.workout.workouttracker.DatabaseHelper;
import tracker.workout.workouttracker.R;
import tracker.workout.workouttracker.dataContainers.Log;

public class WorkoutDiaryActivity extends AppCompatActivity {

    private ListView gridView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_diary);
        gridView = (ListView) findViewById(R.id.workoutDiaryListView);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        new GetLoggedWorkoutsTask().execute();
    }

    private class GetLoggedWorkoutsTask extends AsyncTask<Void, Void, Log[]> {
		@Override
		protected Log[] doInBackground(Void... voids) {
			return databaseHelper.getLoggedWorkouts();
		}

		@Override
		protected void onPostExecute(Log[] logs) {

			final ArrayAdapter<Log> adapter = new ArrayAdapter<Log>(getApplicationContext(), R.layout.list_item, logs);

			// Can't access view hierarchy on a different thread so run on UI thread
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					gridView.setAdapter(adapter);
				}
			});
		}
	}

}
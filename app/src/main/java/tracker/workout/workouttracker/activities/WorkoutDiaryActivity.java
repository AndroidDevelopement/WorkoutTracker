package tracker.workout.workouttracker.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import tracker.workout.workouttracker.DatabaseHelper;
import tracker.workout.workouttracker.R;
import tracker.workout.workouttracker.dataContainers.Log;
import tracker.workout.workouttracker.dataContainers.Workout;
import tracker.workout.workouttracker.dataContainers.WorkoutExercise;

public class WorkoutDiaryActivity extends AppCompatActivity {

	private ListView gridView;
	private Button emptyButton;
	private TextView emptyText;
	private DatabaseHelper databaseHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		databaseHelper = new DatabaseHelper(getApplicationContext());

		setContentView(R.layout.activity_workout_diary);
		gridView = (ListView) findViewById(R.id.workoutDiaryListView);

		new PopulateGridViewTask().execute();

		emptyButton = (Button) findViewById(R.id.emptyLogWorkoutButton);
		gridView.setEmptyView(emptyButton);
		emptyText = (TextView) findViewById(R.id.emptyText);
		gridView.setEmptyView(emptyText);

		emptyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent addActivity = new Intent(getApplicationContext(), LogWorkoutActivity.class);
				startActivity(addActivity);
			}
		});
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

	private class PopulateGridViewTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... voids) {
			final Log[] loggedWorkouts = databaseHelper.getLoggedWorkouts();
			final Workout[] workouts = databaseHelper.getWorkouts();

			// Can't access view hierarchy on a different thread so run on UI thread after querying db.
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					gridView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.list_item, loggedWorkouts));

					gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
							System.out.println("here");
							WorkoutExercise[] a = loggedWorkouts[position].getWorkout().getExercises();

							Intent intent = new Intent(getApplicationContext(), ThisWorkoutDiaryActivity.class);
							intent.putExtra("workout", loggedWorkouts[position].getWorkout());
							startActivity(intent);
						}
					});
				}
			});
			return null;
		}
	}

}
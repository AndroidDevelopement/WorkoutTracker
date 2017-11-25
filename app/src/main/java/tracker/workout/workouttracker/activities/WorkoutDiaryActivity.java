package tracker.workout.workouttracker.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import tracker.workout.workouttracker.CustomAdapter;
import tracker.workout.workouttracker.DatabaseHelper;
import tracker.workout.workouttracker.R;
import tracker.workout.workouttracker.dataContainers.Log;

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

	private class PopulateGridViewTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... voids) {
			final Log[] loggedWorkouts = databaseHelper.getLoggedWorkouts();

			// Can't access view hierarchy on a different thread so run on UI thread after querying db.
			runOnUiThread(new Runnable() {
				@Override
				public void run() {

					CustomAdapter adapt = new CustomAdapter(getApplicationContext(), R.layout.list_row, loggedWorkouts);
					gridView.setAdapter(adapt);

					gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
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
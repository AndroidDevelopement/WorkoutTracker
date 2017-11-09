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
import tracker.workout.workouttracker.dataContainers.Workout;


public class LogWorkoutActivity extends AppCompatActivity {

    private ListView gridView;
    private Button emptyButton;
    private TextView emptyText;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        setContentView(R.layout.activity_log_workout);
        gridView = (ListView) findViewById(R.id.workoutListView);

		new PopulateGridViewTask().execute();

        emptyButton = (Button) findViewById(R.id.emptyCreateWorkoutButton);
        gridView.setEmptyView(emptyButton);
        emptyText = (TextView) findViewById(R.id.emptyText);
        gridView.setEmptyView(emptyText);

        emptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addActivity = new Intent(getApplicationContext(), CreateWorkoutActivity.class);
                startActivity(addActivity);
            }
        });
    }

	private class LogThisWorkoutTask extends AsyncTask<String, Void, Workout> {
		@Override
		protected Workout doInBackground(String... strings) {
			return databaseHelper.getWorkout(strings[0]);
		}

		@Override
		protected void onPostExecute(Workout workout) {
			Intent intent = new Intent(getApplicationContext(), LogThisWorkoutActivity.class);
			intent.putExtra("workout", workout);
			startActivity(intent);
		}
	}

	private class PopulateGridViewTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... voids) {
			final Workout[] workouts = databaseHelper.getWorkouts();

			// Can't access view hierarchy on a different thread so run on UI thread after querying db.
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					gridView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.list_item, workouts));

					gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
							new LogThisWorkoutTask().execute(parent.getItemAtPosition(position).toString());
						}
					});
				}
			});
			return null;
		}
	}

}
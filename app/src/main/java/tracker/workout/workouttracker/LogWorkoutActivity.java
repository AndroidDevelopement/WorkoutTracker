package tracker.workout.workouttracker;

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

	private class LogThisWorkoutTask extends AsyncTask<String, Void, String[]> {
		@Override
		protected String[] doInBackground(String... strings) {
			return databaseHelper.getWorkoutExercises(strings[0]);
		}

		@Override
		protected void onPostExecute(String[] exercises) {
			Intent intent = new Intent(getApplicationContext(), LogThisWorkoutActivity.class);
			intent.putExtra("exercises", exercises);
			startActivity(intent);
		}
	}

	private class PopulateGridViewTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... voids) {
			final String[] workoutNames = databaseHelper.getWorkoutNames();

			// Can't access view hierarchy on a different thread so run on UI thread after querying db.
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					gridView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.list_item, workoutNames));

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
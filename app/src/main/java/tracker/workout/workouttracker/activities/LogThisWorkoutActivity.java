package tracker.workout.workouttracker.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;

import tracker.workout.workouttracker.DatabaseHelper;
import tracker.workout.workouttracker.R;
import tracker.workout.workouttracker.dataContainers.Workout;
import tracker.workout.workouttracker.dataContainers.WorkoutExercise;

public class LogThisWorkoutActivity extends AppCompatActivity {

	private ListView gridView;
	private DatabaseHelper databaseHelper;
	private Button logThisWorkoutButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_this_workout);
		gridView = (ListView) findViewById(R.id.logThisWorkoutListView);
		logThisWorkoutButton = (Button) findViewById(R.id.logThisWorkoutButton);
		final Bundle extras = getIntent().getExtras();
		databaseHelper = new DatabaseHelper(getApplicationContext());

		final Workout workout;

		if (extras != null) {
			workout = (Workout) extras.getSerializable("workout");

			ArrayAdapter<WorkoutExercise> adapter = new ArrayAdapter(this, R.layout.list_item, workout.getExercises());
			gridView.setAdapter(adapter);

			gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// Item that fired the onItemClick event
					Boolean allowContinue = false;
					final WorkoutExercise workoutExercise = (WorkoutExercise) gridView.getAdapter().getItem(position);

					AlertDialog.Builder setBuilder = new AlertDialog.Builder(LogThisWorkoutActivity.this);
					setBuilder.setTitle("Sets");
					final EditText inputField = new EditText(LogThisWorkoutActivity.this);
					inputField.setInputType(InputType.TYPE_CLASS_NUMBER);
					setBuilder.setView(inputField);

					// Setting up buttons for sets
					setBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							int sets = Integer.parseInt(inputField.getText().toString());
							workoutExercise.setSets(sets);
						}
					});

					setBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});

					setBuilder.show();

					AlertDialog.Builder repBuilder = new AlertDialog.Builder(LogThisWorkoutActivity.this);
					repBuilder.setTitle("Reps");
					final EditText repInput = new EditText(LogThisWorkoutActivity.this);
					repInput.setInputType(InputType.TYPE_CLASS_NUMBER);
					repBuilder.setView(repInput);


					// Setting up buttons for reps
					repBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							int reps = Integer.parseInt(repInput.getText().toString());
							workoutExercise.setReps(reps);
						}
					});

					repBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});

					repBuilder.show();
				}
			});

			// TODO: Ensure each workout has a value for sets and reps before allowing user to log this workout
			// Default value for sets and reps from workout_exercises table can be used ^^^^^^^^^^^^^^^^^
			logThisWorkoutButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					logThisWorkoutButton.setEnabled(false);
					new InsertLogExerciseTask().execute(workout);
				}
			});
		}

	}

	private class InsertLogExerciseTask extends AsyncTask<Workout, Void, Void> {
		@Override
		protected Void doInBackground(Workout... workouts) {
			// TODO: Ask for date? Default - Todays date.
			String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

			long logId = databaseHelper.createLog(date);

			for (WorkoutExercise workoutExercise : workouts[0].getExercises()) {
				databaseHelper.insertLogWorkoutExercise(logId, workoutExercise);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			startActivity(new Intent(LogThisWorkoutActivity.this, MainActivity.class));
		}
	}


}
/*
 *  CreateWorkoutActivity - This Activity is where a workout is
 *  created. User adds chosen exercises into workout and names it.
 */

package tracker.workout.workouttracker.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tracker.workout.workouttracker.DatabaseHelper;
import tracker.workout.workouttracker.R;

public class CreateWorkoutActivity extends AppCompatActivity {

    public Button addExerciseButton;
    public Button saveExerciseButton;
    public ArrayList<String> workoutExercises = new ArrayList<String>();
    private String inputText = "";
    private DatabaseHelper databaseHelper;
    private ListView workoutExercisesList;

    /*
     *	onCreate - Sets up a list view that will display exercises already
     *  added to the workout. Also sets up some buttons.
    */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_workout);
		workoutExercisesList = (ListView) findViewById(R.id.listWorkoutExercises);
		final Bundle extras = getIntent().getExtras();

		if (extras != null) {
			// FIXME CHANGE TO WorkoutExercise arraylist
			workoutExercises = (ArrayList<String>) extras.get("workoutExercises");
			ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.list_item, workoutExercises);
			workoutExercisesList.setAdapter(adapter);
		}

		TextView empty = (TextView) findViewById(R.id.empty);
		workoutExercisesList.setEmptyView(empty);
		databaseHelper = new DatabaseHelper(getApplicationContext());
		init();
	}

    /*
     *	init - Adds click listeners to buttons which may be used to start new
     *  activities or add exercises.
    */
    public void init() {
        addExerciseButton = (Button) findViewById(R.id.addExercise);
        saveExerciseButton = (Button) findViewById(R.id.logThisWorkoutButton);

        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addActivity = new Intent(CreateWorkoutActivity.this, CategoriesActivity.class);
                addActivity.putExtra("workoutExercises", workoutExercises);
                startActivity(addActivity);
            }
        });

        saveExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateWorkoutActivity.this);
                builder.setTitle("Workout Name");
                final EditText input = new EditText(CreateWorkoutActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Setting up buttons
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        inputText = input.getText().toString();
                        new CreateWorkoutTask().execute();
                        Toast.makeText(CreateWorkoutActivity.this, "Workout Created", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

	private class CreateWorkoutTask extends AsyncTask<Void, Void, Void> {
        /*
         *	doInBackground - Inserts workout into the database once create workout
         *  button has been pressed and then starts the main activity again.
        */
		@Override
		protected Void doInBackground(Void... voids) {
			String[] arr = workoutExercises.toArray(new String[workoutExercises.size()]);

			databaseHelper.createWorkout(inputText);
			for (String exercise : arr) {
				// FIXME Instead of passing 0 ask to enter sets and reps.
				databaseHelper.insertWorkout(inputText, exercise, 0, 0);
			}

			startActivity(new Intent(CreateWorkoutActivity.this, MainActivity.class));
			return null;
		}
	}
}

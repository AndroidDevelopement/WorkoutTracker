package tracker.workout.workouttracker.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import tracker.workout.workouttracker.DatabaseHelper;
import tracker.workout.workouttracker.R;
import tracker.workout.workouttracker.dataContainers.Workout;
import tracker.workout.workouttracker.dataContainers.WorkoutExercise;

public class LogThisWorkoutActivity extends AppCompatActivity {

    private ListView gridView;
    private DatabaseHelper databaseHelper;
    public Button logWorkoutButton;


    private int sets = 0;
    private int reps = 0;
    private int i = 0;
    private long logId = 0;
    private WorkoutExercise[] workout2 = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_this_workout);
        gridView = (ListView) findViewById(R.id.logThisWorkoutListView);
        final Bundle extras = getIntent().getExtras();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        String date = "15/11/2017";
        databaseHelper.createLog(date);
        Workout workout = null;
        WorkoutExercise[] workoutExercises = {};

        if (extras != null) {
            workout = (Workout) extras.getSerializable("workout");
            workout2 = workout.getExercises();

            workoutExercises = workout.getExercises();
		}

        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.list_item, workoutExercises);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int idInt = (int) id;
                logWorkoutButton = (Button) findViewById(R.id.save);

                // Need to add logged workout to Database here

                AlertDialog.Builder setBuilder = new AlertDialog.Builder(LogThisWorkoutActivity.this);
                setBuilder.setTitle("Sets");
                final EditText setInput = new EditText(LogThisWorkoutActivity.this);
                setInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                setBuilder.setView(setInput);

                // Setting up buttons for sets
                setBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sets = Integer.parseInt(setInput.getText().toString());
                        //Having issues with table string in cahngeSets and changeReps methods
                    //    databaseHelper.changeSets("workout_exercise", workout2[idInt].getId(), sets);
                        // new CreateWorkoutActivity.CreateWorkoutTask().execute();
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
                        reps = Integer.parseInt(repInput.getText().toString());
                       // databaseHelper.changeReps("workout_exercise", workout2[idInt].getId(), reps);
                        databaseHelper.insertLogWorkoutExercise(logId, workout2[idInt].getId(), sets, reps);

                        //Very basic using i, need to implement to check that sets and reps for each
                        //exercise is not 0 instead
                        i++;
                        if(i>=workout2.length){
                            logWorkoutButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d("save workout", "Log this workout clicked");
                                    logId++;
                                    new LogThisWorkoutTask().execute();

                                }
                            });
                        }
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
    }

    private class LogThisWorkoutTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            startActivity(new Intent(LogThisWorkoutActivity.this, MainActivity.class));
            return null;
        }
    }

}

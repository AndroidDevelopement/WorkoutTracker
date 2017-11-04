package tracker.workout.workouttracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

import tracker.workout.workouttracker.database.table.Exercise;

public class CreateWorkout extends AppCompatActivity {

    public Button addExerciseButton;
    public Button saveExerciseButton;
    public ArrayList<Exercise> workoutExercises = new ArrayList<Exercise>();
    public DatabaseHelper dbHelper;
    private String inputText = "";
    private ListView workoutExercisesList;



    public void init() {

        addExerciseButton = (Button)findViewById(R.id.addExercise);
        saveExerciseButton = (Button)findViewById(R.id.save);

        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addActivity = new Intent(CreateWorkout.this, AddExerciseActivity.class);
                addActivity.putExtra("workoutExercises", workoutExercises);
                startActivity(addActivity);
            }
        });

        saveExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateWorkout.this);
                builder.setTitle("Workout Name");
                final EditText input = new EditText(CreateWorkout.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        inputText = input.getText().toString();
                        Exercise[] arr = workoutExercises.toArray(new Exercise[workoutExercises.size()]);
                        System.out.println(arr);
                        boolean successful = dbHelper.insertWorkout(inputText, arr);
                        System.out.println(successful);
                        if(true) {
                            Intent mainActivity = new Intent(CreateWorkout.this, MainActivity.class);
                            startActivity(mainActivity);
                        }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);
        workoutExercisesList = (ListView) findViewById(R.id.listWorkoutExercises);
        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            workoutExercises = (ArrayList<Exercise>) extras.get("workoutExercises");
            ArrayAdapter<Exercise> adapter = new ArrayAdapter(this, R.layout.list_item, workoutExercises);
            workoutExercisesList.setAdapter(adapter);
        }

        dbHelper = new DatabaseHelper(this);
        init();
    }
}

/*
 *  MainActivity - This Activity is where the user selects what they would
 *  like to do. They can create / log a workout or view their diary
 */

package tracker.workout.workouttracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import tracker.workout.workouttracker.R;

public class MainActivity extends AppCompatActivity {

    public Button createWorkoutButton;
    public Button logWorkoutButton;
    public Button workoutDiaryButton;

    // init - Sets up buttons for the menu
    public void init() {
		createWorkoutButton = (Button)findViewById(R.id.createWorkoutButton);
        createWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createWorkout = new Intent(MainActivity.this, CreateWorkoutActivity.class);
                startActivity(createWorkout);
            }
        });

        logWorkoutButton = (Button)findViewById(R.id.logWorkoutButton);
        logWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logWorkout = new Intent(MainActivity.this, LogWorkoutActivity.class);
                startActivity(logWorkout);
            }
        });

        workoutDiaryButton = (Button)findViewById(R.id.workoutDiaryButton);
        workoutDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent workoutDiary = new Intent(MainActivity.this, WorkoutDiaryActivity.class);
                startActivity(workoutDiary);
            }
        });
    }

    //onCreate - Sets up content and calls init.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

}
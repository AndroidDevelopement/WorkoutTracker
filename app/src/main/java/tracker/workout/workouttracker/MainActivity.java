package tracker.workout.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public Button createWorkoutButton;
    public Button logWorkoutButton;
    public Button workoutDiaryButton;
    public DatabaseHelper dbHelper;

    public void init() {
		createWorkoutButton = (Button)findViewById(R.id.createWorkoutButton);
        createWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createWorkout = new Intent(MainActivity.this, CreateWorkout.class);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
        init();
    }

}
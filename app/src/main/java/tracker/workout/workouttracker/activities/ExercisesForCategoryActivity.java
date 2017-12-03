/*
 *  ExercisesForCategoryActivity - This activity displays all of the
 *  exercises for the selected category.
 */

package tracker.workout.workouttracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import tracker.workout.workouttracker.R;

public class ExercisesForCategoryActivity extends AppCompatActivity {

    private ListView exerciseList;

    /*
     *	onCreate - Sets up a list view that will display exercises that are in
     *  the specified category.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_for_category);
        exerciseList = (ListView) findViewById(R.id.listExercisesForCategory);
        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String[] exercises = (String[]) extras.get("exercises");
            ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.list_item, exercises);
            exerciseList.setAdapter(adapter);
        }

        exerciseList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Code for adding exercises to workout
                System.out.println(adapterView.getItemAtPosition(i));
                ArrayList<String> workoutExercises = (ArrayList<String>) extras.get("workoutExercises");
                workoutExercises.add((String) adapterView.getItemAtPosition(i));
                Intent intent = new Intent(ExercisesForCategoryActivity.this, CreateWorkoutActivity.class);
                intent.putExtra("workoutExercises", workoutExercises);
                startActivity(intent);
            }
        });
    }
}

package tracker.workout.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

import tracker.workout.workouttracker.database.DatabaseAlreadyClosedException;
import tracker.workout.workouttracker.database.DatabaseAlreadyOpenException;
import tracker.workout.workouttracker.database.table.Category;
import tracker.workout.workouttracker.database.table.Exercise;

public class AddExerciseActivity extends AppCompatActivity {

    private ListView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_exercise);
        gridView = (ListView) findViewById(R.id.categoryListView);



		final DatabaseHelper db = new DatabaseHelper(this);

        ArrayAdapter<Category> adapter = new ArrayAdapter(this, R.layout.list_item, db.getCategories());
        gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Category category = (Category) parent.getItemAtPosition(position);
				System.out.println("\n\n\n\n\n\n");
				System.out.println(category);
				Exercise[] exercises = db.getExercises(category);
				Intent intent = new Intent(AddExerciseActivity.this, ExercisesForCategory.class);
				intent.putExtra("exercises", exercises);
				final Bundle extras = getIntent().getExtras();
				ArrayList<String> workoutExercises = (ArrayList<String>) extras.get("workoutExercises");
				intent.putExtra("workoutExercises", workoutExercises);
				startActivity(intent);
			}
		});
    }



}
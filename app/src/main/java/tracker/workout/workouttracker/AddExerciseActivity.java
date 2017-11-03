package tracker.workout.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tracker.workout.workouttracker.database.Database;
import tracker.workout.workouttracker.database.DatabaseAlreadyClosedException;
import tracker.workout.workouttracker.database.DatabaseAlreadyOpenException;
import tracker.workout.workouttracker.database.table.Category;
import tracker.workout.workouttracker.database.table.Exercise;

public class AddExerciseActivity extends AppCompatActivity {

	private Database database;
    private ListView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_exercise);
        gridView = (ListView) findViewById(R.id.categoryListView);

		database = new Database(this);

		try {
			database.open();
		} catch (DatabaseAlreadyOpenException e) {
			e.printStackTrace();
		}

        ArrayAdapter<Category> adapter = new ArrayAdapter(this, R.layout.list_item, database.getCategories());
        gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Category category = (Category) parent.getItemAtPosition(position);
				Exercise[] exercises = database.getExercises(category);
				Intent intent = new Intent(AddExerciseActivity.this, ExercisesForCategory.class);
				intent.putExtra("exercises", exercises);

				final Bundle extras = getIntent().getExtras();
				ArrayList<Exercise> workoutExercises = (ArrayList<Exercise>) extras.get("workoutExercises");
				intent.putExtra("workoutExercises", workoutExercises);
				startActivity(intent);
			}
		});
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();

		try {
			database.close();
		} catch (DatabaseAlreadyClosedException e) {
			e.printStackTrace();
		}
	}

}
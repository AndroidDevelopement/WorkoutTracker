package tracker.workout.workouttracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    private ListView gridView;
	public DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
        gridView = (ListView) findViewById(R.id.categoryListView);
		databaseHelper = new DatabaseHelper(this);
		final String[] categories = this.getResources().getStringArray(R.array.categories);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.list_item, categories);
        gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String categoryName = parent.getItemAtPosition(position).toString();
				new CategoriesActivityTask().execute(categoryName);
			}
		});
    }

    private class CategoriesActivityTask extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... strings) {
			String[] categoryExercises = databaseHelper.getExercisesForCategory(strings[0]);
			Bundle extras = getIntent().getExtras();
			ArrayList<String> workoutExercises = (ArrayList<String>) extras.get("workoutExercises");
			Intent intent = new Intent(CategoriesActivity.this, ExercisesForCategoryActivity.class);
			intent.putExtra("exercises", categoryExercises);
			intent.putExtra("workoutExercises", workoutExercises);
			startActivity(intent);
			return null;
		}
	}

}

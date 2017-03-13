package com.lifelab.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.lifelab.R;

public class ExperimentGrid extends AppCompatActivity {
    //final String[] experimentList;
    final Integer[] experimentList = {
            R.drawable.experiment_1,
            R.drawable.experiment_2,
            R.drawable.experiment_3,
            R.drawable.experiment_4,
            R.drawable.experiment_5,
            R.drawable.experiment_6,
            R.drawable.experiment_7,
            R.drawable.experiment_8,
            R.drawable.experiment_9,
            R.drawable.experiment_10,
            R.drawable.experiment_11,
            R.drawable.experiment_12,
            R.drawable.experiment_13,
            R.drawable.experiment_14
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_grid);

        //  experimentList =getResources().getStringArray(R.array.experiment_names);

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this,experimentList));
      //  Log.i("re", "here");
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Log.d("Item clicked", " position : " + position);
                Intent intent = new Intent(ExperimentGrid.this, ExperimentResult.class);
                intent.putExtra("Position", position);
                // intent.putExtra("ExperimentName", experimentList);
                startActivity(intent);
            }
        });
    }
}


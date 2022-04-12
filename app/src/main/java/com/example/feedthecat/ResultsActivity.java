package com.example.feedthecat;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.feedthecat.db.Result;

import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        loadResults();
    }

    public void loadResults() {
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        List<Result> results = MainActivity.database.resultDao().getAll();
        for (Result result : results) {
            TableRow tableRow = new TableRow(this);
            tableRow.addView(createDateTextView(result.getDate()));
            tableRow.addView(createResultTextView(result.getRes()));
            tableLayout.addView(tableRow);
        }
    }

    public TextView createDateTextView(String date) {
        TableRow.LayoutParams dateLayoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                3.0f
        );
        TextView dateTextView = new TextView(this);
        dateTextView.setLayoutParams(dateLayoutParams);
        dateTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        dateTextView.setText(date);
        dateTextView.setTextSize(20);
        return dateTextView;
    }

    public TextView createResultTextView(int res) {
        TableRow.LayoutParams resLayoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                2.0f
        );
        TextView resTextView = new TextView(this);
        resTextView.setLayoutParams(resLayoutParams);
        resTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        resTextView.setTextSize(20);
        resTextView.setText(String.valueOf(res));
        return resTextView;
    }
}

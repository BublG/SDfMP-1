package com.example.feedthecat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.feedthecat.db.AppDatabase;
import com.example.feedthecat.db.Result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int ANIMATION_DURATION = 2000;
    private static final String ANIMATION_PROPERTY = "rotation";

    private static final int CLICKS_QUANTITY = 15;

    private static final String SHARING_MESSAGE_TEMPLATE = "I fed the cat for %d satiety!";
    private static final String SEND_INTENT_TYPE = "text/plain";

    private static final String DATE_FORMAT = "MM.dd HH:mm";

    private int satiety = 0;

    public static AppDatabase database;

    private TextView satietyTextView;
    private ImageView catImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();

        catImageView = findViewById(R.id.catImageView);

        satietyTextView = findViewById(R.id.satietyTextView);
        satietyTextView.setText(String.valueOf(satiety));
    }

    public void feed(View view) {
        satietyTextView.setText(String.valueOf(++satiety));
        if (satiety % CLICKS_QUANTITY == 0) {
            ObjectAnimator animation = ObjectAnimator.ofFloat(catImageView, ANIMATION_PROPERTY,
                    0f, 360f);
            animation.setDuration(ANIMATION_DURATION);
            animation.start();
        }
    }

    public void shareResult(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, String.format(Locale.getDefault(),
                SHARING_MESSAGE_TEMPLATE, satiety));
        sendIntent.setType(SEND_INTENT_TYPE);

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    public void goToAbout(View view) {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        MainActivity.this.startActivity(intent);
    }

    public void goToResults(View view) {
        Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
        MainActivity.this.startActivity(intent);
    }

    public void saveAndExit(View view) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        Result result = new Result(dateFormat.format(new Date()), satiety);
        database.resultDao().insert(result);
        finish();
        System.exit(0);
    }
}
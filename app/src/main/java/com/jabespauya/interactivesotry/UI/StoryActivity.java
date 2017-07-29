package com.jabespauya.interactivesotry.UI;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jabespauya.interactivesotry.Model.Page;
import com.jabespauya.interactivesotry.Model.Story;
import com.jabespauya.interactivesotry.R;

public class StoryActivity extends AppCompatActivity {

    private static final String TAG = StoryActivity.class.getSimpleName();

    private Story story;
    private String name;
    private ImageView storyImageView;
    private TextView textStoryView;
    private Button choice1;
    private Button choice2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        storyImageView = (ImageView) findViewById(R.id.storyImageView);
        textStoryView = (TextView)findViewById(R.id.storyTextView);
        choice1 = (Button)findViewById(R.id.choice1Button);
        choice2 = (Button)findViewById(R.id.choice2Button);

        Intent intent = getIntent();
        name = intent.getStringExtra(getString(R.string.key_name));

        if(name == null || name.isEmpty()){
            name = "Friend";
        }
        Log.d(TAG,name);



        story = new Story();
        loadPages(0);
    }



    private void loadPages(int PageNumber) {
        final Page page = story.getpage(PageNumber);

        Drawable image = ContextCompat.getDrawable(this, page.getImageId());
        storyImageView.setImageDrawable(image);

        String pageText = getString(page.getTextId());
        pageText = String.format(pageText, name);
        textStoryView.setText(pageText);

        if (page.isFinalPage()){

            choice1.setVisibility(View.INVISIBLE);
            choice2.setText(R.string.play_again);
            choice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadPages(0);
                }
            });
        }else {
            loadButtons(page);

        }


    }

    private void loadButtons(final Page page) {
        choice1.setText(page.getChoice1().getTextId());
        choice1.setVisibility(View.VISIBLE);
        choice1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int nextPage = page.getChoice1().getNextPage();
            loadPages(nextPage);
        }
    });


            choice2.setText(page.getChoice2().getTextId());
            choice2.setVisibility(View.VISIBLE);
            choice2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int nextPage = page.getChoice2().getNextPage();
            loadPages(nextPage);
        }
    });
    }
}

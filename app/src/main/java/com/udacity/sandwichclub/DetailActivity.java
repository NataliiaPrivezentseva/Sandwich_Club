package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView sandwichPictureIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        } else {
            int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
            if (position == DEFAULT_POSITION) {
                // EXTRA_POSITION not found in intent
                closeOnError();
                return;
            }

            String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
            String json = sandwiches[position];
            sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI();
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(sandwichPictureIv);

            setTitle(sandwich.getMainName());
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView alsoKnownAsLabel = findViewById(R.id.also_known_label);
        TextView placeOfOriginLabel = findViewById(R.id.origin_label);
        TextView descriptionLabel = findViewById(R.id.description_label);
        TextView ingredientsLabel = findViewById(R.id.ingredients_label);

        TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
        TextView placeOfOriginTv = findViewById(R.id.origin_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);

        if (sandwich.getAlsoKnownAs() != null) {
            StringBuilder alsoKnown = new StringBuilder();
            for (String s : sandwich.getAlsoKnownAs()) {
                if (!s.isEmpty()) {
                    alsoKnownAsLabel.setVisibility(View.VISIBLE);
                    alsoKnownAsTv.setVisibility(View.VISIBLE);
                    alsoKnown.append(s).append("\n");
                }
            }
            String alsoKnownString = alsoKnown.toString().trim();
            alsoKnownAsTv.setText(alsoKnownString);
        } else {
            alsoKnownAsLabel.setVisibility(View.GONE);
            alsoKnownAsTv.setVisibility(View.GONE);
        }

        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOriginLabel.setVisibility(View.VISIBLE);
            placeOfOriginTv.setVisibility(View.VISIBLE);
            placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        } else {
            placeOfOriginLabel.setVisibility(View.GONE);
            placeOfOriginTv.setVisibility(View.GONE);
        }

        if (sandwich.getDescription() != null && !sandwich.getDescription().isEmpty()) {
            descriptionLabel.setVisibility(View.VISIBLE);
            descriptionTv.setVisibility(View.VISIBLE);
            descriptionTv.setText(sandwich.getDescription());
        } else {
            descriptionLabel.setVisibility(View.GONE);
            descriptionTv.setVisibility(View.GONE);
        }

        if (sandwich.getIngredients() != null) {
            StringBuilder ingredients = new StringBuilder();

            for (String s : sandwich.getIngredients()) {
                if (!s.isEmpty()) {
                    ingredientsLabel.setVisibility(View.VISIBLE);
                    ingredientsTv.setVisibility(View.VISIBLE);
                    ingredients.append(s).append("\n");
                }
            }
            String ingredientsString = ingredients.toString().trim();
            ingredientsTv.setText(ingredientsString);
        } else {
            ingredientsLabel.setVisibility(View.GONE);
            ingredientsTv.setVisibility(View.GONE);
        }
    }
}

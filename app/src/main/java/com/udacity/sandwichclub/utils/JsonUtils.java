package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            sandwich = new Sandwich();

            JSONObject sandwichJson = new JSONObject(json);

            JSONObject name = sandwichJson.getJSONObject("name");
            String mainName = name.getString("mainName");
            if (mainName != null && !mainName.isEmpty()) {
                sandwich.setMainName(mainName);
            }
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            if (alsoKnownAsArray != null && alsoKnownAsArray.length() > 0) {
                ArrayList<String> alsoKnownAs = convertJsonArrayToArrayList(alsoKnownAsArray);
                if (!alsoKnownAs.isEmpty()) {
                    sandwich.setAlsoKnownAs(alsoKnownAs);
                }
            }

            String placeOfOrigin = sandwichJson.getString("placeOfOrigin");
            if (placeOfOrigin != null && !placeOfOrigin.isEmpty()) {
                sandwich.setPlaceOfOrigin(placeOfOrigin);
            }

            String description = sandwichJson.getString("description");
            if (description != null && !description.isEmpty()){
                sandwich.setDescription(description);
            }

            String image = sandwichJson.getString("image");
            if (image != null && !image.isEmpty()){
                sandwich.setImage(image);
            }

            JSONArray ingredientsArray = sandwichJson.getJSONArray("ingredients");
            if (ingredientsArray != null && ingredientsArray.length() > 0) {
                ArrayList<String> ingredients = convertJsonArrayToArrayList(ingredientsArray);
                if (!ingredients.isEmpty()) {
                    sandwich.setIngredients(ingredients);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }

    private static ArrayList<String> convertJsonArrayToArrayList(JSONArray jsonArray) throws JSONException {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            String element = jsonArray.getString(i);
            if (element != null && !element.isEmpty()) {
                arrayList.add(element);
            }
        }
        return arrayList;
    }
}

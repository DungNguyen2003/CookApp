package com.example.cookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FavouriteActivity extends AppCompatActivity {

    private RecipeAdapter recipeAdapter;
    private ArrayList<Recipe> recipes;
    private GridView listViewFood;
    private Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        init();
        setListeners();
        getListSQLite();
    }

    public void setListeners(){


        listViewFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(recipes.get(position).getEmail().equals(preferenceManager.getString(Constants.KEY_EMAIL))){
//                    Intent intent = new Intent(MainActivity.this, Activity_DetailMyRecipe.class);
//                    String name = recipes.get(position).getNameRecipe();
//                    String image = recipes.get(position).getImage();
//                    String cookingRecipe = recipes.get(position).getCookingRecipe();
//                    ID = recipes.get(position).getId();
//
//                    intent.putExtra(Constants.KEY_NAME_INTENT, name);
//                    intent.putExtra(Constants.KEY_IMAGE_INTENT, image);
//                    intent.putExtra(Constants.KEY_COOKING_INTENT, cookingRecipe);
//
//
//                    startActivity(intent);
//                }
                Intent intent = new Intent(FavouriteActivity.this, Activity_Recipe.class);

                String name = recipes.get(position).getNameRecipe();
                String image = recipes.get(position).getImage();
                String cookingRecipe = recipes.get(position).getCookingRecipe();

                intent.putExtra(Constants.KEY_NAME_INTENT, name);
                intent.putExtra(Constants.KEY_IMAGE_INTENT, image);
                intent.putExtra(Constants.KEY_COOKING_INTENT, cookingRecipe);

                startActivity(intent);

            }
        });
    }

    public void getListSQLite(){
        helper = new Helper(this, Constants.KEY_SQL_RECIPE, null, 1);
        helper.queryData("CREATE TABLE IF NOT EXISTS RecipeFavourite(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "EMAIL TEXT, NAME_RECIPE TEXT, COOK_RECIPE TEXT, IMAGE TEXT)");
        Cursor cursor = helper.getData("SELECT * FROM RecipeFavourite");
        recipes = new ArrayList<>();
        while (cursor.moveToNext()){
            Recipe recipe = new Recipe();
            recipe.setNameRecipe(cursor.getString(2));
            recipe.setEmail(cursor.getString(1));
            recipe.setImage(cursor.getString(4));
            recipe.setCookingRecipe(cursor.getString(3));
            recipe.setId(cursor.getInt(0));
            recipes.add(recipe);
        }

        recipeAdapter = new RecipeAdapter(recipes, FavouriteActivity.this);
        listViewFood.setAdapter(recipeAdapter);
    }

    public void init(){
        listViewFood = findViewById(R.id.listViewFood);

    }
}
package com.example.baking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baking.Recipe.Recipe;
import com.example.baking.Recipe.RecipeAdapter;
import com.example.baking.Recipe.RecipeService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    List<Recipe> recipeList;
    @BindView(R.id.recipe_list) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Baking Recipes");
        ButterKnife.bind(this);
        recyclerView.setHasFixedSize(true);

        if(getResources().getBoolean(R.bool.isTablet))
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        else
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


        fetchRecipe();

    }

    private void fetchRecipe() {
        final String base_url="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RecipeService service  = retrofit.create(RecipeService.class);
        service.get().enqueue(new retrofit2.Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipeList=response.body();
                updateUI();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("fitchFail", t.getMessage());
            }
        });


    }

    private void updateUI() {
        recyclerView.setAdapter(new RecipeAdapter(this,recipeList));
    }
}

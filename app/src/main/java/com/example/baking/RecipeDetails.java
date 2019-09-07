package com.example.baking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.baking.Fragments.IngredientFragment;
import com.example.baking.Fragments.StepFragment;
import com.example.baking.Fragments.VideoFragment;
import com.example.baking.Recipe.Recipe;
import com.example.baking.Widget.WidgetProvider;


import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetails extends AppCompatActivity implements StepFragment.OnStepClick {

    @BindView(R.id.F1) FrameLayout ingFrame;
    @BindView(R.id.F2) FrameLayout stepFrame;
    @BindView(R.id.F3) FrameLayout vidFrame;
    @BindView(R.id.leftLl) LinearLayout leftLayout;

    boolean isTablet;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ButterKnife.bind(this);

        recipe = (Recipe) getIntent().getSerializableExtra("Recipe");
        setTitle(recipe.getName());

        isTablet = getResources().getBoolean(R.bool.isTablet);

        initFragments();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        Intent intent = new Intent(getApplicationContext(), WidgetProvider.class);
        intent.putExtra("Recipe", recipe);
        sendBroadcast(intent);

        return super.onOptionsItemSelected(item);
    }

    private void initFragments(){

        initIngFragment();

        initStepFragment();

        initVidFragment(0);
        if(isTablet)
            vidFrame.setVisibility(View.VISIBLE);

    }

    private void initVidFragment(int position) {
        VideoFragment videoFragment = new VideoFragment();

        Bundle bundle = new Bundle();


        bundle.putSerializable("Recipe",recipe);
        bundle.putInt("position",position);

        videoFragment.setArguments(bundle);
        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.F3, videoFragment).commit();
    }

    private void initStepFragment() {
        StepFragment stepFragment = new StepFragment();

        stepFragment.setArguments(getIntent().getExtras());
        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.F2, stepFragment).commit();
    }

    private void initIngFragment() {
        IngredientFragment ingFrgment = new IngredientFragment();

        ingFrgment.setArguments(getIntent().getExtras());
        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.F1, ingFrgment).commit();

    }

    @Override
    public void onBackPressed() {
        if(leftLayout.getVisibility()==View.GONE){
            leftLayout.setVisibility(View.VISIBLE);
            vidFrame.setVisibility(View.GONE);
        }
        else{
            super.onBackPressed();
        }
    }
    @Override
    public void onStepListener(int position) {
        if(!isTablet){
            leftLayout.setVisibility(View.GONE);
            vidFrame.setVisibility(View.VISIBLE);
        }
        initVidFragment(position);
    }
}

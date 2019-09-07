package com.example.baking.Fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baking.Ingredient.Ingredient;
import com.example.baking.Ingredient.IngredientAdapter;
import com.example.baking.R;
import com.example.baking.Recipe.Recipe;

import java.util.List;

public class IngredientFragment extends Fragment {


    public static IngredientFragment newInstance() {
        return new IngredientFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredient_fragment, container, false);

        Recipe recipe = (Recipe) getArguments().getSerializable("Recipe");
        List<Ingredient> ingredientList = recipe.getIngredients();

        RecyclerView recyclerView = rootView.findViewById(R.id.ing_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        recyclerView.setAdapter(new IngredientAdapter(rootView.getContext(),ingredientList));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}

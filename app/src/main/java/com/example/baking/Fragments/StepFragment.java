package com.example.baking.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baking.R;
import com.example.baking.Recipe.Recipe;
import com.example.baking.Steps.Step;
import com.example.baking.Steps.StepAdapter;

import java.util.List;

public class StepFragment extends Fragment implements StepAdapter.OnStepClickListener {

    OnStepClick onStepClick;

    public static StepFragment newInstance() {
        return new StepFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_steps_fragment, container, false);
        Recipe recipe = (Recipe) getArguments().getSerializable("Recipe");
        List<Step> stepList = recipe.getSteps();

        RecyclerView recyclerView = rootView.findViewById(R.id.step_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        recyclerView.setAdapter(new StepAdapter(rootView.getContext(),stepList, this));

        return  rootView;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onStepClick = (OnStepClick) getContext();
    }


    @Override
    public void onStepSelected(int stepIndex) {
        onStepClick.onStepListener(stepIndex);
    }

    public interface OnStepClick{
        void onStepListener(int position);
    }


}

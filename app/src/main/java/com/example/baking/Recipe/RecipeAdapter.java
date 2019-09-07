package com.example.baking.Recipe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baking.R;
import com.example.baking.RecipeDetails;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{

    private List<Recipe> recipeList;
    private Context context;

    public RecipeAdapter(Context context,List<Recipe> recipeList) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        final Recipe recipe = recipeList.get(position);

        holder.recipeNameTV.setText(recipe.getName());

        holder.revipeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecipeDetails.class);
                intent.putExtra("Recipe",recipe);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeNameTV;
        CardView revipeCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeNameTV = itemView.findViewById(R.id.recipe_name);
            revipeCard = itemView.findViewById(R.id.recipe_card);
        }
    }
}

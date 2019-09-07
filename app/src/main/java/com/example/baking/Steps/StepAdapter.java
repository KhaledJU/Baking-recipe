package com.example.baking.Steps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baking.R;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private Context context;
    private List<Step> stepList;
    OnStepClickListener onStepClickListener;

    public StepAdapter(Context context, List<Step> stepList, OnStepClickListener onStepClickListener) {
        this.context = context;
        this.stepList = stepList;
        this.onStepClickListener = onStepClickListener;
    }

    @NonNull
    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_item, parent, false);
        return new StepAdapter.ViewHolder(view, onStepClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapter.ViewHolder holder, int position) {
        holder.stepNameTV.setText(stepList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stepNameTV;
        CardView stepCard;
        OnStepClickListener onStepClickListener;
        public ViewHolder(@NonNull View itemView, OnStepClickListener onStepClickListener) {
            super(itemView);
            stepCard = itemView.findViewById(R.id.step_card);
            stepNameTV = itemView.findViewById(R.id.step_name);
            this.onStepClickListener = onStepClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onStepClickListener.onStepSelected(getAdapterPosition());
        }
    }
    public interface OnStepClickListener {
        void onStepSelected(int stepIndex);
    }
}

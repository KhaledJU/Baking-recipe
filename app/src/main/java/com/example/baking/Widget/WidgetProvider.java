package com.example.baking.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;


import com.example.baking.Ingredient.Ingredient;
import com.example.baking.R;
import com.example.baking.Recipe.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetProvider extends AppWidgetProvider {

    static List<Ingredient> ingredients = new ArrayList<>();
    private static String text;



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.empty_view, text);

        Intent intent = new Intent(context, WidgetService.class);
        views.setRemoteAdapter(R.id.list_view, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.hasExtra("Recipe")) {
            Recipe recipe = (Recipe) intent.getSerializableExtra("Recipe");
            ingredients = recipe.getIngredients();
            Log.d("widget","size "+ingredients.size());
            text = recipe.getName();
        } else {
            text = "Select a Recipe";
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());

        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), WidgetProvider.class);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
        if (appWidgetIds != null && appWidgetIds.length > 0) {
            onUpdate(context, appWidgetManager, appWidgetIds);
        }

        super.onReceive(context, intent);
    }
}


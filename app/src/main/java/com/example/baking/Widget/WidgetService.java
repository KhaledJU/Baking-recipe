package com.example.baking.Widget;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.example.baking.Ingredient.Ingredient;
import com.example.baking.R;

import java.util.List;

public class WidgetService extends RemoteViewsService {

    private List<Ingredient> ingredientList;
    public WidgetService() {
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteListViewsFactory(getApplicationContext());
    }

    class RemoteListViewsFactory implements WidgetService.RemoteViewsFactory {

        final Context mContext;

        RemoteListViewsFactory(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            ingredientList = WidgetProvider.ingredients;
            Log.d("widget","ing "+ingredientList.size());
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (ingredientList == null) return 0;
            return ingredientList.size();
        }

        @Override
        public RemoteViews getViewAt(int index) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
            Ingredient ingredient = ingredientList.get(index);
            String widgetItem = ingredient.toString();
            Log.d("widget",widgetItem);
            views.setTextViewText(R.id.item, widgetItem);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}

package com.prolificinteractive.library.calendarwidget.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import java.util.Arrays;
import java.util.List;

/**
 * Routing Activity for other samples
 */
public class MainActivity extends ActionBarActivity {

    private static final List<Route> ROUTES = Arrays.asList(
        new Route("Basic Example", BasicActivity.class),
        new Route("Selection Range Example", RangeActivity.class),
        new Route("XML Customization Example", CustomizationActivity.class)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new RoutesAdapter(this));
    }

    private void onRouteClicked(Route route) {
        startActivity(new Intent(this, route.routeTo));
    }

    private class RoutesAdapter extends RecyclerView.Adapter<RouteViewHolder> {

        private final LayoutInflater inflater;

        private RoutesAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public RouteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.item_route, viewGroup, false);
            return new RouteViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RouteViewHolder viewHolder, int i) {
            Route item = ROUTES.get(i);
            viewHolder.textView.setText(item.label);
        }

        @Override
        public int getItemCount() {
            return ROUTES.size();
        }

    }

    private static class Route {

        public final String label;
        public final Class<? extends Activity> routeTo;

        public Route(String label, Class<? extends Activity> routeTo) {
            this.label = label;
            this.routeTo = routeTo;
        }
    }

    private class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView textView;

        public RouteViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(android.R.id.text1);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRouteClicked(ROUTES.get(getPosition()));
        }
    }
}

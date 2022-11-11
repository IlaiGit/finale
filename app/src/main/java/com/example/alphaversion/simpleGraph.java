package com.example.alphaversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class simpleGraph extends AppCompatActivity {

    LineGraphSeries<DataPoint> series1;
    LineGraphSeries<DataPoint> series2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_graph);

        double x1,y1,x2,y2;
        x1 = -5.0;
        x2 = -10.0;

        GraphView graphView = (GraphView) findViewById(R.id.graph);

        series2 = new LineGraphSeries<DataPoint>();
        for (int i = 0; i<500; i=i+2){
            x2 = x2+0.1;
            y2 = -5*x2 + 100;
            series2.appendData(new DataPoint(x2,y2), true, 500);
        }

        series1 = new LineGraphSeries<DataPoint>();
        for(int i = 0; i<500; i++){
            x1 = x1+0.1;
            y1 = 3*x1 + 5;
            series1.appendData(new DataPoint(x1, y1), true, 500);
        }

        graphView.addSeries(series1);
        graphView.addSeries(series2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu;
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        return true;
    }

    public void color(MenuItem item) {
        startActivity(new Intent(simpleGraph.this, chnage_by_color.class));
    }

    public void notification(MenuItem item) {
        startActivity(new Intent(simpleGraph.this, not.class));

    }

    public void timeToast(MenuItem item) {
        startActivity(new Intent(simpleGraph.this, anotherNOT.class));
    }

    public void auth(MenuItem item) {
        startActivity(new Intent(simpleGraph.this, MainActivity.class));
    }

    public void storage(MenuItem item) {
        startActivity(new Intent(simpleGraph.this, ast_for_storage.class));
    }

    public void Graph(MenuItem item) {
        startActivity(new Intent(simpleGraph.this, simpleGraph.class));

    }
}
package com.niamh.notesapplication.FeaturePaint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.niamh.notesapplication.R;

public class PaintActivity extends AppCompatActivity {

    private PaintView paintView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        paintView = findViewById(R.id.paint_view);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
    }

    //options menu for paint activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //creating the menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //switch statement for when each menu iem is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //when the back button is pressed it brings us back to the main activity
            case R.id.back:
                onBackPressed();
             //when the white pen is pressed it makes it white drawable
            case R.id.whitePen :
                paintView.whitePen();
                Toast.makeText(this, "White Pen Active!", Toast.LENGTH_SHORT).show();
                return true;
//when the red pen is pressed it makes it red drawable
            case R.id.redPen :
                paintView.redPen();
                Toast.makeText(this, "Red Pen Active!", Toast.LENGTH_SHORT).show();
                return true;
//when the blue pen is pressed it makes it blue drawable
            case R.id.bluePen :
                paintView.bluePen();
                Toast.makeText(this, "Blue Pen Active!", Toast.LENGTH_SHORT).show();
                return true;
//when the yellow pen is pressed it makes it yellow drawable
            case R.id.yellowPen :
                paintView.yellowPen();
                Toast.makeText(this, "Yellow Pen Active!", Toast.LENGTH_SHORT).show();
                return true;
//when the eraser is pressed it makes it black drawable which works because it has a balck background
            case R.id.eraser :
                paintView.eraser();
                Toast.makeText(this, "Eraser Active!", Toast.LENGTH_SHORT).show();
                return true;
//clear removes all the drawn features
            case R.id.clear :
                paintView.clear();
                Toast.makeText(this, "Canvas Empty!", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
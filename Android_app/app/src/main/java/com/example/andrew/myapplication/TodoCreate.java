package com.example.andrew.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class TodoCreate extends AppCompatActivity {

    private ListView projectsList;
    private EditText value;
    private RelativeLayout interceptor;
    static CheckedTextView checkedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_create);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        interceptor =  findViewById(R.id.content_todo_create);
        value= findViewById(R.id.EditText);

//        interceptor.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                    value.setFocusable(false);
//                }
//                return v.performClick();
//            }
//        });
        value.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setFocusableInTouchMode(true);
                }
                return v.performClick();
            }
        });

        Bundle b = this.getIntent().getExtras();
        ArrayList<String> projectsArray = b.getStringArrayList("projectsArray");

        projectsList =  findViewById(R.id.projectsList);
        ProjectAdapter adapter = new ProjectAdapter(this, projectsArray);

        projectsList.setAdapter(adapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.save:

                value = findViewById(R.id.EditText);

                if (value.getText().length() > 3 && checkedTextView != null) {

                    JsonObject param = new JsonObject();
                    param.addProperty("text", value.getText().toString());
                    param.addProperty("project_id", checkedTextView.getId());
                    JsonObject params = new JsonObject();
                    params.add("todo", param);
                    Ion.with(getApplicationContext())
                            .load("POST", "https://cryptic-thicket-73335.herokuapp.com/todos")
                            .setJsonObjectBody(params)
                            .asJsonObject();
                    finish();
                }
                else Toast.makeText(this, "Введите название задачи", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
    //Inject into Context
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}

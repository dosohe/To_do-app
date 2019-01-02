package com.example.andrew.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    private ListView_Adapter mAdapter;
    private ListView listView;
    Bundle b;

    static class Project {
        @SerializedName("title")
        String title;
        @SerializedName("todos")
        List<Todo> todo;
    }

    static class Todo {
        @SerializedName("id")
        int todo_id;
        @SerializedName("text")
        String text;
        @SerializedName("isCompleted")
        boolean isCompleted;
    }

    List<Project> projects;
    static List<Todo> todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();

        setContentView(R.layout.activity_main);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TodoCreate.class);
                intent.putExtras(b);
                TodoCreate.checkedTextView = null;
                startActivity(intent);
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Ion.with(this)

                .load(getString(R.string.kIndexRequest))

                .asJsonArray()

                .setCallback(new FutureCallback<JsonArray>() {

                    @Override

                    public void onCompleted(Exception e, JsonArray result) {

                        if (result != null) {

                            projects = new ArrayList<Project>();

                            for (final JsonElement projectJsonElement : result) {

                                projects.add(new Gson().fromJson(projectJsonElement, Project.class));

                            }

                            todos = new ArrayList<Todo>();

                            for (int i = 0; i < projects.size(); i++) {

                                Project project = projects.get(i);

                                for (int y = 0; y < project.todo.size(); y++) {
                                    Collections.sort(project.todo, new Comparator<Todo>() {
                                        public int compare(Todo o1, Todo o2) {
                                            return o1.todo_id - o2.todo_id;
                                        }
                                    });
                                    todos.add(project.todo.get(y));
                                }
                            }

                            b = new Bundle();
                            List<String> string = new ArrayList<>();

                            for (int i = 0; i < projects.size(); i++) {
                                Project project = projects.get(i);
                                string.add(project.title);
                            }
                            b.putStringArrayList("projectsArray", (ArrayList<String>) string);

                            listView =  findViewById(R.id.listView);

                            mAdapter = new ListView_Adapter(listView.getContext());

                            for (int i = 0; i < projects.size(); i++) {

                                Project project = projects.get(i);

                                mAdapter.addSectionHeaderItem(project.title);

                                for (int y = 0; y < project.todo.size(); y++) {

                                    Collections.sort(project.todo, new Comparator<Todo>() {
                                        public int compare(Todo o1, Todo o2) {
                                            return o1.todo_id - o2.todo_id;
                                        }
                                    });

                                    Todo todo = project.todo.get(y);

                                    mAdapter.addItem(todo.text);
                                }
                            }

                            listView.setAdapter(mAdapter);
                        }
                    }

                });
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }

    //Inject into Context
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    static void update(int todo_id) {
        String param = "https://cryptic-thicket-73335.herokuapp.com/todos/" + String.valueOf(todo_id);
        Ion.with(getAppContext())
                .load("PUT", param)
                .asString();
    }

}

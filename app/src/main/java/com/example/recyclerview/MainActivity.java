package com.example.recyclerview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    RecyclerView mrecyclerView;
    MyAdapter myAdapter;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mrecyclerView = findViewById(R.id.recyclerView);
        preferences = this.getSharedPreferences("My_Pref", MODE_PRIVATE);
        getMyList();
    }

    private void getMyList() {
        ArrayList<Model> models = new ArrayList<>();
        Model m = new Model();
        m.setTitle("News Feed");
        m.setDescription("This is newsfeed for example recyclerview and textview for example");
        m.setImg(R.drawable.newsfeed);
        models.add(m);

        m = new Model();
        m.setTitle("Business");
        m.setDescription("This is Business");
        m.setImg(R.drawable.business);
        models.add(m);

        m = new Model();
        m.setTitle("People");
        m.setDescription("This is People");
        m.setImg(R.drawable.people);
        models.add(m);

        m = new Model();
        m.setTitle("Notes");
        m.setDescription("This is Notes");
        m.setImg(R.drawable.notes);
        models.add(m);

        m = new Model();
        m.setTitle("Feedback");
        m.setDescription("This is Feedback");
        m.setImg(R.drawable.feedback);
        models.add(m);

        String mSortSetting = preferences.getString("Sort", "ascending");
        if (mSortSetting.equals("ascending")) {
            Collections.sort(models, Model.By_TITLE_ASCENDING);
        } else if (mSortSetting.equals("descending")) {
            Collections.sort(models, Model.By_TITLE_DESCENDING);
        }

        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(this, models);
        mrecyclerView.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                myAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sorting) {
            sortDialogue();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sortDialogue() {
        String[] options = {"Ascending", "Descending"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("sort by");
        builder.setIcon(R.drawable.ic_action_sort);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (which == 0) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Sort", "ascending");
                    editor.apply();
                    getMyList();
                }

                if (which == 1) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Sort", "descending");
                    editor.apply();
                    getMyList();
                } }
        });

                builder.create().show();
    }

}

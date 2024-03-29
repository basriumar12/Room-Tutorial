package co.twoh.roomtutorial;

import android.app.Activity;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;

import co.twoh.roomtutorial.adapter.AdapterBarangRecyclerView;
import co.twoh.roomtutorial.data.factory.AppDatabase;
import co.twoh.roomtutorial.model.Barang;

/**
 * Created by Herdi_WORK on 21.01.18.
 */

public class RoomReadActivity extends AppCompatActivity {

    private AppDatabase db;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Barang> daftarBarang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        /**
         * Initialize layout dan sebagainya
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout) ;

        /**
         * Initialize ArrayList untuk data barang
         */
        daftarBarang = new ArrayList<>();

        /**
         * Initialize database
         * allow main thread queries
         */
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "barangdb").allowMainThreadQueries().build();

        /**
         * Initialize recyclerview dan layout manager
         */
        rvView = findViewById(R.id.rv_main);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);

        addData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addData();
            }
        });

    }

    void addData (){

        /**
         * Add all data to arraylist
         *
         *
         */
        daftarBarang.clear();
        daftarBarang.addAll(Arrays.asList(db.barangDAO().selectAllBarangs()));


        /**
         * Set all data ke adapter, dan menampilkannya
         */
        adapter = new AdapterBarangRecyclerView(daftarBarang, this);
        rvView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false); ;
    }


}

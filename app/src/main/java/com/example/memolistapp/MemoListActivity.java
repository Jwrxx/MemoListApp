package com.example.memolistapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemoListActivity extends AppCompatActivity {
    ArrayList<Memo> memos;

    private RecyclerView recyclerView;
    private ArrayList<Memo> memoList;

    private MemoAdapter memoAdapter;
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            int memoId = memos.get(position).getMemoID();

            //MemoID to main
            Intent intent = new Intent(MemoListActivity.this, MainActivity.class);
            intent.putExtra("memoID", memoId);
            startActivity(intent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        initListButton();
        initSettingsButton();
        initMemoButton();
        initSwipeToDelete();

        //setContentView(R.layout.activity_list);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//            });
        String sortBy = getSharedPreferences("MyMemoListListPreferences",
                Context.MODE_PRIVATE).getString("sortfield", "subject");
        String sortOrder = getSharedPreferences("MyMemoListPreferences",
                Context.MODE_PRIVATE).getString("sortorder", "ASC");

        //get data
        MemoDataSource ds = new MemoDataSource(this);

        try {
            ds.open();
            memos = ds.getMemos(sortBy, sortOrder); //object
            ds.close();

            //recycler
            RecyclerView memoList = findViewById(R.id.rvMemoList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            memoList.setLayoutManager(layoutManager);


            memoAdapter = new MemoAdapter(memos, this);
            memoAdapter.setOnItemClickListener(onItemClickListener);
            memoList.setAdapter(memoAdapter);

        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();
        }

        //initDeleteSwitch();
        //initDeleteContactButton();
    }

    private void initListButton() {
        ImageButton listButton = findViewById(R.id.imageButtonList);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoListActivity.this, MemoListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        ImageButton settingsButton = findViewById(R.id.imageButtonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoListActivity.this, MemoSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initMemoButton() {
        Button newMemo = findViewById(R.id.newMemoButton);
        newMemo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MemoListActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSwipeToDelete(){
        RecyclerView recyclerView = findViewById(R.id.rvMemoList);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                memoList.remove(position);
                memoAdapter.notifyItemRemoved(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}

package com.example.boondamonitoring;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.boondamonitoring.model.Adapter;
import com.example.boondamonitoring.model.AdapterDate;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class third extends Fragment implements View.OnClickListener {
    Adapter adapter;
    AdapterDate adapterDate;
    RecyclerView noteList;
    RecyclerView dateList;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_activity, container, false);

//        Toolbar toolbar = view.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        drawerLayout = view.findViewById(R.id.drawer);
//        navigationView = view.findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this.on);
//        toggle = new ActionBarDrawerToggle(this.getActivity(), drawerLayout, toolbar, R.string.open, R.string.close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.setDrawerIndicatorEnabled(true);
//        toggle.syncState();

        noteList = view.findViewById(R.id.noteList);
        dateList = view.findViewById(R.id.dateList);
        noteList.setOnClickListener(this::onClick);
        dateList.setOnClickListener(this::onClick);

        List<String> titles = new ArrayList<>();
        List<String> content = new ArrayList<>();
        List<String> time = new ArrayList<>();
        List<String> day = new ArrayList<>();
        List<String> date = new ArrayList<>();


        titles.add("first");
        time.add("10:00 - 12:00");
        content.add("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");

        titles.add("second");
        time.add("10:00 - 12:00");
        content.add("noted 2");

        titles.add("first");
        time.add("10:00 - 12:00");
        content.add("noted1");

        titles.add("second");
        time.add("10:00 - 12:00");
        content.add("noted 2");

        titles.add("first");
        time.add("10:00 - 12:00");
        content.add("noted1");

        titles.add("second");
        time.add("10:00 - 12:00");
        content.add("noted 2");

        day.add("Fri");
        date.add("02");

        day.add("Fri");
        date.add("03");

        day.add("Fri");
        date.add("04");

        day.add("Fri");
        date.add("05");

        day.add("Fri");
        date.add("06");

        day.add("Fri");
        date.add("07");

        day.add("Fri");
        date.add("08");

        day.add("Fri");
        date.add("09");

        day.add("Fri");
        date.add("10");

        day.add("Fri");
        date.add("11");


        adapter = new Adapter(titles, time, content);
        adapterDate = new AdapterDate(day, date);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        noteList.setLayoutManager(linearLayoutManager);
        noteList.setAdapter(adapter);
        dateList.setLayoutManager(linearLayoutManager1);
        dateList.setAdapter(adapterDate);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.noteList:
            case R.id.dateList:
        }
    }
}
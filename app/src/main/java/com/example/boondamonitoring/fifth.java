package com.example.boondamonitoring;

import android.app.Activity;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class fifth extends Fragment implements View.OnClickListener {

    FirebaseAuth auth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    MainActivity mainActivity = new MainActivity();
    Activity a;
String takdela;
    public static final String SHARED_PREFS = "shared_prefs";

    public static final String NAME_KEY = "name_key";

    public static final String ID_KEY = "pid_key";

    SharedPreferences sharedpreferences;
    String name,id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fifth, container, false);
        auth = FirebaseAuth.getInstance();

        view.findViewById(R.id.bt_logout).setOnClickListener(this);
        sharedpreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        name = sharedpreferences.getString(NAME_KEY,null);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_logout:
                AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                        if (currentAccessToken==null){
                            LoginManager.getInstance().logOut();
                        }
                    }
                };
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(NAME_KEY,null);
                editor.apply();
                try {
                    FirebaseAuth.getInstance().signOut();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

                Intent zamzam = new Intent(getContext(), MainActivity.class);
                ((MainActivity)getActivity()).reload();
                startActivity(zamzam);
        }

    }
}
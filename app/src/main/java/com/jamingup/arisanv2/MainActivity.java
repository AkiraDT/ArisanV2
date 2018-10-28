package com.jamingup.arisanv2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private FragmentManager fragmentManager;
    private PesertaFragment pesertaFrag;
    private Dialog dialog;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent i;
            switch (item.getItemId()) {
                case R.id.navigation_peserta:
                    fragmentManager.beginTransaction()
                            .replace(R.id.mainContent, pesertaFrag)
                            .commit();
                    setTitle("Peserta Arisan");
                    return true;
                /*case R.id.navigation_kelompok:
                    fragmentManager.beginTransaction()
                            .replace(R.id.content, kelompokArisanFrag)
                            .commit();
                    setTitle("Kelompok Arisan");
                    return true;
                case R.id.navigation_kocok:
                    fragmentManager.beginTransaction()
                            .replace(R.id.content, pilihKelompokKocokFrag)
                            .commit();
                    setTitle("Kocok Arisan");
                    return true;
                case R.id.navigation_pemenang:
                    fragmentManager.beginTransaction()
                            .replace(R.id.content, pilihKelompokPemenangFrag)
                            .commit();
                    setTitle("Pemenang Arisan");
                    return true;
                    */
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new Dialog(this);
        fragmentManager = getSupportFragmentManager();
        pesertaFrag = new PesertaFragment();

        fragmentManager.beginTransaction()
                .add(R.id.mainContent , pesertaFrag)
                .commit();
        toolbarSetting();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState != null)
            navigation.setSelectedItemId(savedInstanceState.getInt("idNav"));
    }

    void toolbarSetting(){
        android.support.v7.widget.Toolbar toolbar =  (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView toolbar_text = (TextView) findViewById(R.id.toolbar_text);
        if(toolbar_text != null && toolbar != null){
            toolbar_text.setText(getTitle());
            setSupportActionBar(toolbar);
        }
    }

    public void showBantuan(View view) {
        Toast.makeText(this, "Masih dalam pengembangan", Toast.LENGTH_SHORT).show();
    }

    public void showAboutJamming(View view) {
        View viewPop = LayoutInflater.from(this).inflate(R.layout.about_jamming, null, false);

        dialog.setContentView(viewPop);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void closeDialog(View view) {
        dialog.dismiss();
    }
}

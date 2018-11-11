package com.jamingup.arisanv2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private FragmentManager fragmentManager;
    private PesertaFragment pesertaFrag;
    private Dialog dialog;
    private Typeface TextMeOneStyle;
    private TextView toolbarText;

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

        TextMeOneStyle = Typeface.createFromAsset(getAssets(), "fonts/TextMeOne-Regular.ttf");
        toolbarText = (TextView) findViewById(R.id.toolbar_text);

        dialog = new Dialog(this);
        fragmentManager = getSupportFragmentManager();
        pesertaFrag = new PesertaFragment();

        fragmentManager.beginTransaction()
                .add(R.id.mainContent , pesertaFrag)
                .commit();
        toolbarSetting();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            if(i == 1 || i == 4){
                layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, displayMetrics);
                layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, displayMetrics);
            }else if(i == 2){
                layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 26, displayMetrics);
                layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 26, displayMetrics);
            }else if(i == 3){
                layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 38, displayMetrics);
                layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 38, displayMetrics);
            }else {
                layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
                layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
            }

            iconView.setLayoutParams(layoutParams);
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState != null)
            navigation.setSelectedItemId(savedInstanceState.getInt("idNav"));
    }

    void toolbarSetting(){
        android.support.v7.widget.Toolbar toolbar =  (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView toolbar_text = (TextView) findViewById(R.id.toolbar_text);
        toolbar_text.setTypeface(TextMeOneStyle);
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
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void closeDialog(View view) {
        dialog.dismiss();
    }
}

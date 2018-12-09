package com.jamingup.arisanv2;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class KelompokViewActivity extends AppCompatActivity {
    private Typeface TextMeOneStyle;
    private TextView toolbarText;

    private TextView labelNama;
    private TextView labelNominalHadiah;
    private TextView labelJumlahAnggota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelompok_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextMeOneStyle = Typeface.createFromAsset(getAssets(), "fonts/TextMeOne-Regular.ttf");
        toolbarText = (TextView) findViewById(R.id.toolbar_text);
        labelNama = (TextView) findViewById(R.id.label_name);
        labelJumlahAnggota = (TextView) findViewById(R.id.label_jumlah_anggota);
        labelNominalHadiah = (TextView) findViewById(R.id.label_nominal);

        toolbarText.setTypeface(TextMeOneStyle);
        labelNama.setTypeface(TextMeOneStyle);
        labelJumlahAnggota.setTypeface(TextMeOneStyle);
        labelNominalHadiah.setTypeface(TextMeOneStyle);

        toolbarText.setText("Anggota Arisan");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}

package com.jamingup.arisanv2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class KelompokViewActivity extends AppCompatActivity {
    private Typeface TextMeOneStyle;
    private TextView toolbarText;

    private TextView labelNama;
    private TextView labelNominalHadiah;
    private TextView labelJumlahAnggota;
    private ImageView imgKelompok;

    private KelompokViewModel kelompokViewModel;

    private FragmentManager fragmentManager;
    private AnggotaFragment anggotaFragment;
//    private PilihAnggotaFragment pilihAnggotaFragment;


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
        imgKelompok = (ImageView) findViewById(R.id.img_profile_kelompok);

        toolbarText.setTypeface(TextMeOneStyle);
        labelNama.setTypeface(TextMeOneStyle);
        labelJumlahAnggota.setTypeface(TextMeOneStyle);
        labelNominalHadiah.setTypeface(TextMeOneStyle);

        toolbarText.setText("Anggota Arisan");

        final Bitmap img = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("img"), 0, getIntent().getByteArrayExtra("img").length);
        labelNama.setText(getIntent().getStringExtra("nama"));
        double hadiah = Double.parseDouble(String.valueOf(getIntent().getIntExtra("hadiah", 0)));
        labelNominalHadiah.setText("Rp. " + String.format("%,.2f", hadiah));
        labelJumlahAnggota.setText(String.valueOf(getIntent().getIntExtra("jumlah", 0)) + " Anggota");
        imgKelompok.setImageBitmap(img);

        anggotaFragment = new AnggotaFragment();
//        pilihAnggotaFragment = new PilihAnggotaFragment();
        fragmentManager = getSupportFragmentManager();

        Bundle b = new Bundle();
        b.putString("namaKelompok",getIntent().getStringExtra("nama"));

        anggotaFragment.setArguments(b);
//        pilihAnggotaFragment.setArguments(b);

        fragmentManager.beginTransaction()
                .add(R.id.anggotaFrame, anggotaFragment)
                .commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}

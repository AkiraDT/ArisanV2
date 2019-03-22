package com.jamingup.arisanv2.Tagihan;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jamingup.arisanv2.ItemClickListener;
import com.jamingup.arisanv2.Model.Peserta;
import com.jamingup.arisanv2.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterTagihanAnggota extends ListAdapter<Peserta, AdapterTagihanAnggota.ViewHolder> {
    private static final String TAG = "AdapterTagihanAnggota";
    private Context context;
    private Typeface TextMeOneStyle;

    public AdapterTagihanAnggota(Typeface typeface, Context context) {
        super(DIFF_CALLBACK);
        TextMeOneStyle = typeface;
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<Peserta> DIFF_CALLBACK = new DiffUtil.ItemCallback<Peserta>() {
        @Override
        public boolean areItemsTheSame(@NonNull Peserta anggotaData, @NonNull Peserta newAnggotaData) {
            return anggotaData.getId() == newAnggotaData.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Peserta anggotaData, @NonNull Peserta newAnggotaData) {
            return anggotaData.getNama().equals(newAnggotaData.getNama());
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView textViewNama;
        private final TextView textViewStatusBayar;
        private final CircleImageView imageViewPeserta;
        private final Switch mSwitch;

        private ItemClickListener itemClickListener;
        public ViewHolder(View v, Typeface typeface) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);

            textViewNama = (TextView) v.findViewById(R.id.textView_peserta_list_content);
            textViewNama.setTypeface(typeface);

            textViewStatusBayar = (TextView) v.findViewById(R.id.textView_statusbayar_list_content);
            textViewStatusBayar.setTypeface(typeface);

            imageViewPeserta = (CircleImageView) v.findViewById(R.id.img_profile);

            mSwitch = (Switch) v.findViewById(R.id.switch_anggota);
        }

        public Switch getmSwitch(){return mSwitch;}

        public TextView getTextViewStatusBayar(){return textViewStatusBayar;}

        public TextView getTextViewNama() {
            return textViewNama;
        }

        public CircleImageView getImageViewPeserta() {
            return imageViewPeserta;
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tagihan_peserta_list_content, viewGroup, false);

        return new ViewHolder(v, TextMeOneStyle);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        //add keanggotaan here later
//        String nama = getItem(position).getNama();

//        if(nama.contains(" ")){
//            String[] namaSplit = nama.split(" ");
//            viewHolder.getTextViewNama().setText(namaSplit[0] + " " + namaSplit[1].toUpperCase().charAt(0) + ".");
//        }else {
//            viewHolder.getTextViewNama().setText(nama);
//        }

        viewHolder.getTextViewNama().setText(getItem(position).getNama());
        Bitmap img = BitmapFactory.decodeByteArray(getItem(position).getImg(), 0, getItem(position).getImg().length);
        viewHolder.getImageViewPeserta().setImageBitmap(img);

        final ColorStateList color = viewHolder.getTextViewStatusBayar().getTextColors();

        viewHolder.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    viewHolder.getTextViewStatusBayar().setText("Lunas");
                    viewHolder.getTextViewStatusBayar().setTextColor(Color.BLACK);
                }else {
                    viewHolder.getTextViewStatusBayar().setText("Belum Lunas");
                    viewHolder.getTextViewStatusBayar().setTextColor(color);
                }
            }
        });


        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Toast.makeText(context, "Long click "+ viewHolder.getTextViewNama().getText().toString(), Toast.LENGTH_SHORT).show();
                }else{
//                    previewKelompok(position);
                    Toast.makeText(context, "click "+ "Bisa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Peserta getAnggotaAt(int pos){
        return getItem(pos);
    }

}
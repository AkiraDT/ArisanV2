package com.jamingup.arisanv2.Kelompok;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jamingup.arisanv2.ItemClickListener;
import com.jamingup.arisanv2.Model.Peserta;
import com.jamingup.arisanv2.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterAnggota extends ListAdapter<Peserta, AdapterAnggota.ViewHolder> {
    private static final String TAG = "AdapterAnggota";
    private Context context;
    private Typeface GloriaFont;

    public AdapterAnggota(Typeface typeface, Context context) {
        super(DIFF_CALLBACK);
        GloriaFont = typeface;
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
        private final CircleImageView imageViewPeserta;

        private ItemClickListener itemClickListener;
        public ViewHolder(View v, Typeface typeface) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);

            textViewNama = (TextView) v.findViewById(R.id.textView_anggota_list_content);
            textViewNama.setTypeface(typeface);

            imageViewPeserta = (CircleImageView) v.findViewById(R.id.img_profile);
        }

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
                .inflate(R.layout.anggota_list_content, viewGroup, false);

        return new ViewHolder(v, GloriaFont);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        //add keanggotaan here later
        String nama = getItem(position).getNama();

        if(nama.contains(" ")){
            String[] namaSplit = nama.split(" ");
            viewHolder.getTextViewNama().setText(namaSplit[0] + " " + namaSplit[1].toUpperCase().charAt(0) + ".");
        }else {
            viewHolder.getTextViewNama().setText(nama);
        }
        Bitmap img = BitmapFactory.decodeByteArray(getItem(position).getImg(), 0, getItem(position).getImg().length);
        viewHolder.getImageViewPeserta().setImageBitmap(img);
        //Bitmap img = BitmapFactory.decodeByteArray(getItem(position).getImg(), 0, getItem(position).getImg().length);
        //viewHolder.getImageViewPeserta().setImageBitmap(img);
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
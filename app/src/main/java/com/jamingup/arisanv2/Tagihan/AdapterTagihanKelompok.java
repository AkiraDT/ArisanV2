package com.jamingup.arisanv2.Tagihan;

import android.content.Context;
import android.content.Intent;
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
import com.jamingup.arisanv2.Model.Kelompok;
import com.jamingup.arisanv2.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterTagihanKelompok extends ListAdapter<Kelompok, AdapterTagihanKelompok.ViewHolder> {
    private static final String TAG = "AdapterKelompok";
    private Context context;
    private Typeface GloriaFont;

    public AdapterTagihanKelompok(Typeface typeface, Context context) {
        super(DIFF_CALLBACK);
        GloriaFont = typeface;
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<Kelompok> DIFF_CALLBACK = new DiffUtil.ItemCallback<Kelompok>() {
        @Override
        public boolean areItemsTheSame(@NonNull Kelompok kelompokData, @NonNull Kelompok newKelompokData) {
            return kelompokData.getId() == newKelompokData.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Kelompok kelompokData, @NonNull Kelompok newKelompokData) {
            return kelompokData.getNama().equals(newKelompokData.getNama());
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView textViewNama;
        private final TextView textViewBelumBayar;
        private final CircleImageView kelompokImg;

        private ItemClickListener itemClickListener;
        public ViewHolder(View v, Typeface typeface) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);

            textViewNama = (TextView) v.findViewById(R.id.textView_kelompok_list_content);
            textViewNama.setTypeface(typeface);

            textViewBelumBayar = (TextView) v.findViewById(R.id.textView_belumbayar_list_content);
            textViewBelumBayar.setTypeface(typeface);

            kelompokImg = (CircleImageView) v.findViewById(R.id.img_profile_kelompok);
        }

        public TextView getTextViewNama() {
            return textViewNama;
        }

        public TextView getTextViewBelumBayar() {
            return textViewBelumBayar;
        }

        public CircleImageView getProfileKelompok(){ return kelompokImg;}

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
                .inflate(R.layout.tagihan_kelompok_list_content, viewGroup, false);

        return new ViewHolder(v, GloriaFont);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        //add keanggotaan here later
        viewHolder.getTextViewNama().setText(getItem(position).getNama());
        String nominal = String.format("%,.2f", Double.parseDouble(String.valueOf(getItem(position).getNominalHadiah())));
        viewHolder.getTextViewBelumBayar().setText("Anggota Belum Bayar " + String.valueOf(getItem(position).getJumlahAnggota()));
        final Bitmap img = BitmapFactory.decodeByteArray(getItem(position).getImg(),0, getItem(position).getImg().length);
        viewHolder.getProfileKelompok().setImageBitmap(img);

        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
//                    Toast.makeText(context, "Long click "+ viewHolder.getTextViewNama().getText().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Long click "+ getItem(position).getId(), Toast.LENGTH_SHORT).show();
                }else{
//                    previewKelompok(position);
//                    Toast.makeText(context, "click "+ "Bisa", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, TagihanAnggotaActivity.class);
                    intent.putExtra("nama", getItem(position).getNama());
                    intent.putExtra("jumlah", getItem(position).getJumlahAnggota());
                    intent.putExtra("hadiah", getItem(position).getNominalHadiah());
                    intent.putExtra("img", getItem(position).getImg());
                    intent.putExtra("idKelompok", getItem(position).getId());
                    intent.putExtra("interval", getItem(position).getInterval());
                    context.startActivity(intent);
                }
            }
        });
    }

    public Kelompok getKelompokAt(int pos){
        return getItem(pos);
    }

}
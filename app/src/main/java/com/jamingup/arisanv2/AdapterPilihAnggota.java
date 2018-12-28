package com.jamingup.arisanv2;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPilihAnggota extends ListAdapter<Peserta, AdapterPilihAnggota.ViewHolder> {
    private static final String TAG = "AdapterPilihAnggota";
    private Context context;
    private Typeface TextMeOneStyle;
    private Boolean allChecked;
    private List<Integer> checkedItemList;

    public AdapterPilihAnggota(Typeface typeface, Context context) {
        super(DIFF_CALLBACK);
        TextMeOneStyle = typeface;
        this.context = context;
        checkedItemList = new ArrayList<>();
    }

    private static final DiffUtil.ItemCallback<Peserta> DIFF_CALLBACK = new DiffUtil.ItemCallback<Peserta>() {
        @Override
        public boolean areItemsTheSame(@NonNull Peserta pesertaData, @NonNull Peserta newPesertaData) {
            return pesertaData.getId() == newPesertaData.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Peserta pesertaData, @NonNull Peserta newPesertaData) {
            return pesertaData.getNama().equals(newPesertaData.getNama());
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView textViewNama;
        private final TextView textViewKelompok;
        private final CircleImageView imageViewPeserta;
        private final CheckBox checkBox;
        private boolean checkStatus = false;

        private ItemClickListener itemClickListener;
        public ViewHolder(View v, Typeface typeface) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);

            textViewNama = (TextView) v.findViewById(R.id.textView_peserta_list_content);
            textViewNama.setTypeface(typeface);

            textViewKelompok = (TextView) v.findViewById(R.id.textView_keanggotaan_list_content);
            textViewKelompok.setTypeface(typeface);

            imageViewPeserta = (CircleImageView) v.findViewById(R.id.img_profile);

            checkBox = (CheckBox) v.findViewById(R.id.checkAnggota);
            checkBox.setClickable(false);
        }

        public TextView getTextViewNama() {
            return textViewNama;
        }

        public TextView getTextViewKelompok() {
            return textViewKelompok;
        }

        public CircleImageView getImageViewPeserta() {
            return imageViewPeserta;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public boolean isCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(boolean checkStatus) {
            this.checkStatus = checkStatus;
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
                .inflate(R.layout.pilih_anggota_list_content, viewGroup, false);

        return new ViewHolder(v, TextMeOneStyle);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        //add keanggotaan here later
        viewHolder.getTextViewNama().setText(getItem(position).getNama());
        Bitmap img = BitmapFactory.decodeByteArray(getItem(position).getImg(), 0, getItem(position).getImg().length);
        viewHolder.getImageViewPeserta().setImageBitmap(img);
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Toast.makeText(context, "Long click "+ viewHolder.getTextViewNama().getText().toString(), Toast.LENGTH_SHORT).show();
                }else{
//                    Toast.makeText(context, "click "+ "Bisa", Toast.LENGTH_SHORT).show();
                    viewHolder.setCheckStatus(!viewHolder.isCheckStatus());
                    viewHolder.getCheckBox().setChecked(viewHolder.isCheckStatus());
                    if(viewHolder.isCheckStatus()){
                        if(!checkedItemList.contains(position)){
                            checkedItemList.add(position);
                        }
                    }else {
                        if(checkedItemList.contains(position)){
                            checkedItemList.remove((Object)position);
                        }
                    }
                }
            }
        });
    }

    public Peserta getPesertaAt(int pos){
        return getItem(pos);
    }

    public List<Integer> getCheckedItemList() {
        return checkedItemList;
    }
}
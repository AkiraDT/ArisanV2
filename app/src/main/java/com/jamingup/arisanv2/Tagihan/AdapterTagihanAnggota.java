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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.jamingup.arisanv2.ItemClickListener;
import com.jamingup.arisanv2.Model.AnggotaTagihan;
import com.jamingup.arisanv2.R;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterTagihanAnggota extends ListAdapter<AnggotaTagihan, AdapterTagihanAnggota.ViewHolder> {
    private static final String TAG = "AdapterTagihanAnggota";
    private Context context;
    private Typeface TMOFont;
    private boolean isEdited = false;
    private List<AnggotaTagihan> anggotaList;

    public AdapterTagihanAnggota(Typeface typeface, Context context) {
        super(DIFF_CALLBACK);
        TMOFont = typeface;
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<AnggotaTagihan> DIFF_CALLBACK = new DiffUtil.ItemCallback<AnggotaTagihan>() {
        @Override
        public boolean areItemsTheSame(@NonNull AnggotaTagihan anggotaData, @NonNull AnggotaTagihan newAnggotaData) {
            return anggotaData.getNama().equalsIgnoreCase(newAnggotaData.getNama());
        }

        @Override
        public boolean areContentsTheSame(@NonNull AnggotaTagihan anggotaData, @NonNull AnggotaTagihan newAnggotaData) {
            return anggotaData.getNama().equalsIgnoreCase(newAnggotaData.getNama());
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView textViewNama;
        private final TextView textViewStatusBayar;
        private final CircleImageView imageViewPeserta;
        private final Switch mSwitch;
        final ColorStateList color;


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

            color = textViewStatusBayar.getTextColors();
        }

        public Switch getmSwitch(){return mSwitch;}

        public TextView getTextViewStatusBayar(){return textViewStatusBayar;}

        public TextView getTextViewNama() {
            return textViewNama;
        }

        public CircleImageView getImageViewPeserta() {
            return imageViewPeserta;
        }

        public ColorStateList getColor(){
            return color;
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

        return new ViewHolder(v, TMOFont);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.getTextViewNama().setText(getItem(position).getNama());
        Bitmap img = BitmapFactory.decodeByteArray(getItem(position).getImg(), 0, getItem(position).getImg().length);
        viewHolder.getImageViewPeserta().setImageBitmap(img);
        Log.d("Test", getItem(position).getNama() + ". : " +getItem(position).getStatusBayar());


        if(getItem(position).getStatusBayar()==0){
            viewHolder.getTextViewStatusBayar().setText("Belum Lunas");
            viewHolder.getTextViewStatusBayar().setTextColor(viewHolder.getColor());
            viewHolder.getmSwitch().setChecked(false);
            anggotaList.get(position).setStatusBayar(0);
        }else{
            viewHolder.getTextViewStatusBayar().setText("Lunas");
            viewHolder.getTextViewStatusBayar().setTextColor(Color.BLACK);
            viewHolder.getmSwitch().setChecked(true);
            anggotaList.get(position).setStatusBayar(1);
        }


        viewHolder.getmSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isEdited = true;
                if(isChecked){
                    viewHolder.getTextViewStatusBayar().setText("Lunas");
                    viewHolder.getTextViewStatusBayar().setTextColor(Color.BLACK);
                    anggotaList.get(position).setStatusBayar(1);
                }else {
                    viewHolder.getTextViewStatusBayar().setText("Belum Lunas");
                    viewHolder.getTextViewStatusBayar().setTextColor(viewHolder.getColor());
                    anggotaList.get(position).setStatusBayar(0);
                }

                Log.d("Test", getItem(position).getNama() + ". setelah : " +getItem(position).getStatusBayar());

            }
        });


        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
//                    Toast.makeText(context, "Long click "+ viewHolder.getTextViewNama().getText().toString(), Toast.LENGTH_SHORT).show();
                }else{
//                    previewKelompok(position);
//                    Toast.makeText(context, "click "+ "Bisa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public AnggotaTagihan getAnggotaAt(int pos){
        return getItem(pos);
    }

    public List<AnggotaTagihan> getAnggotaList(){
        return anggotaList;
    }

    public void setAnggotaList(List<AnggotaTagihan> anggotaList){
        this.anggotaList = anggotaList;
    }

    public boolean getEdited(){
        return isEdited;
    }

    public void setEdited(boolean bol){
        this.isEdited = bol;
    }

}
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
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterKelompok extends ListAdapter<Kelompok, AdapterKelompok.ViewHolder> {
    private static final String TAG = "AdapterKelompok";
    private Context context;
    private Typeface TextMeOneStyle;

    public AdapterKelompok(Typeface typeface, Context context) {
        super(DIFF_CALLBACK);
        TextMeOneStyle = typeface;
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
        private final TextView textViewNominal;
        private final TextView badge;
        private final CircleImageView kelompokImg;

        private ItemClickListener itemClickListener;
        public ViewHolder(View v, Typeface typeface) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);

            textViewNama = (TextView) v.findViewById(R.id.textView_kelompok_list_content);
            textViewNama.setTypeface(typeface);

            textViewNominal = (TextView) v.findViewById(R.id.textView_nominal_list_content);
            textViewNominal.setTypeface(typeface);

            badge = (TextView) v.findViewById(R.id.badge_kelompok);
            badge.setTypeface(typeface);

            kelompokImg = (CircleImageView) v.findViewById(R.id.img_profile_kelompok);
        }

        public TextView getTextViewNama() {
            return textViewNama;
        }

        public TextView getTextViewNominal() {
            return textViewNominal;
        }

        public TextView getBadge() {
            return badge;
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
                .inflate(R.layout.kelompok_list_content, viewGroup, false);

        return new ViewHolder(v, TextMeOneStyle);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        //add keanggotaan here later
        viewHolder.getTextViewNama().setText(getItem(position).getNama());
        viewHolder.getTextViewNominal().setText("Rp. " + String.valueOf(getItem(position).getNominalHadiah()));
        viewHolder.getBadge().setText( String.valueOf(getItem(position).getJumlahAnggota()));
        Bitmap img = BitmapFactory.decodeByteArray(getItem(position).getImg(),0, getItem(position).getImg().length);
        viewHolder.getProfileKelompok().setImageBitmap(img);

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

    public Kelompok getKelompokAt(int pos){
        return getItem(pos);
    }

//    private void previewKelompok(int pos){
//        View view = LayoutInflater.from(context).inflate(R.layout.preview_peserta, null, false);
//        final TextView namaPeserta = (TextView) view.findViewById(R.id.textview_preview_nama_peserta);
//        final TextView notelp = (TextView) view.findViewById(R.id.textview_preview_notelp);
//        final TextView alamat = (TextView) view.findViewById(R.id.textview_preview_screen_alamat);
//        CircleImageView button = (CircleImageView) view.findViewById(R.id.dismiss_preview_button);
//        CircleImageView profilePic = (CircleImageView) view.findViewById(R.id.img_profile);
//
//        namaPeserta.setTypeface(TextMeOneStyle);
//        notelp.setTypeface(TextMeOneStyle);
//        alamat.setTypeface(TextMeOneStyle);
//
//        namaPeserta.setText(getItem(pos).getNama());
//        notelp.setText(getItem(pos).getNoTelp());
//        alamat.setText(getItem(pos).getAlamat());
////        profilePic.setImageBitmap(getItem(pos).getImg());
//
//        final Dialog dialog = new Dialog(context);
//        dialog.setContentView(view);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//    }
}
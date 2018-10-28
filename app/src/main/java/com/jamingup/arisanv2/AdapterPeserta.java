package com.jamingup.arisanv2;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterPeserta extends RecyclerView.Adapter<AdapterPeserta.ViewHolder> {
    private static final String TAG = "AdapterPeserta";
    private String[] mDataSet;
    private Context context;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView textView;
        private ItemClickListener itemClickListener;
        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);

            textView = (TextView) v.findViewById(R.id.textView_peserta_list_content);
//            if(getAdapterPosition() % 2 != 0){
//                textView.setBackgroundColor(Color.parseColor("#47C7F4"));
//            }
        }

        public TextView getTextView() {
            return textView;
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
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public AdapterPeserta(String[] dataSet, Context context) {
        mDataSet = dataSet;
        this.context = context;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.peserta_list_content, viewGroup, false);

        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        if(position % 2 != 0){
           // viewHolder.getTextView().setBackground();
        }
        viewHolder.getTextView().setText(mDataSet[position]);
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Toast.makeText(context, "Long click "+ mDataSet[position], Toast.LENGTH_SHORT).show();
                }else{
                    previewKelompok(position);
                    //Toast.makeText(context, ""+ mDataSet[position], Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void previewKelompok(int pos){
        View view = LayoutInflater.from(context).inflate(R.layout.preview_peserta, null, false);
        final TextView namaPeserta = (TextView) view.findViewById(R.id.textview_preview_nama_peserta);
        final TextView notelp = (TextView) view.findViewById(R.id.textview_preview_notelp);
        final TextView alamat = (TextView) view.findViewById(R.id.textview_preview_screen_alamat);

        namaPeserta.setText(mDataSet[pos]);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context)
//                .setTitle("")
//                .setView(view);
//        builder.create().show();

        Dialog dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
package com.jamingup.arisanv2;

import android.Manifest;
import android.app.Dialog;
import android.app.VoiceInteractor;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class PesertaFragment extends Fragment {
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 2;
    public static final int IMAGE_GALERY_REQUEST_CODE = 10;
    public static final int CAMERA_REQUEST_CODE = 1;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private  String [] myDataset;
    private ImageButton bigAddButton;
    private FloatingActionButton fab;
    private Typeface comicSansFont;
    private CircleImageView imgThumbnail;
    private Bitmap bmpImage;
    String[] permissionRequest = {Manifest.permission.CAMERA};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comicSansFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/comic.ttf");
        setData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_peserta, container, false);

        bigAddButton = (ImageButton) view.findViewById(R.id.button_add_peserta_empty);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPeserta();
            }
        });

        bigAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPeserta();
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_peserta);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new AdapterPeserta(myDataset, getContext(), comicSansFont);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });

        dataCheck();

        return view;
    }

    void dataCheck(){
        if(myDataset == null){
            fab.hide();
            mRecyclerView.setVisibility(View.GONE);
            bigAddButton.setVisibility(View.VISIBLE);
        }else{
            fab.show();
            mRecyclerView.setVisibility(View.VISIBLE);
            bigAddButton.setVisibility(View.GONE);

        }
    }

    void setData(){
        myDataset = new String[50];
//        for (int i = 0; i < 50; i++) {
//            myDataset[i] = "Boler " + (i+1) + " Bin Pitak " + i;
//        }
        myDataset =new String[]{"Saya", "dia", "kamu", "Anata", "Watashi", "Boku", "Kare",
            "Saya", "dia", "kamu", "Anata", "Watashi", "Boku", "Kare",
                "Saya", "dia", "kamu", "Anata", "Watashi", "Boku", "Kare",
                "Saya", "dia", "kamu", "Anata", "Watashi", "Boku", "Kare"};
    }

    private void addPeserta(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.add_peserta_form, null, false);
        final TextView namaPeserta = (TextView) view.findViewById(R.id.label_name);
        final TextView notelp = (TextView) view.findViewById(R.id.label_telp);
        final TextView alamat = (TextView) view.findViewById(R.id.label_alamat);
        final TextView kode = (TextView) view.findViewById(R.id.kode_reg);
        ImageButton cancelButton = (ImageButton) view.findViewById(R.id.cancel_button);
        ImageButton acceptButton = (ImageButton) view.findViewById(R.id.accept_button);
        final EditText editNamaPeserta = (EditText) view.findViewById(R.id.add_nama);
        final EditText editNotelp = (EditText) view.findViewById(R.id.add_telp);
        final EditText editAlamat = (EditText) view.findViewById(R.id.add_alamat);
        imgThumbnail = (CircleImageView) view.findViewById(R.id.img_profile);

        namaPeserta.setTypeface(comicSansFont);
        notelp.setTypeface(comicSansFont);
        alamat.setTypeface(comicSansFont);
        kode.setTypeface(comicSansFont);
        editNamaPeserta.setTypeface(comicSansFont);
        editNotelp.setTypeface(comicSansFont);
        editAlamat.setTypeface(comicSansFont);
        bmpImage = null;

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        //Ketika membatalkan aksi menyimpan data
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mengecek apakah data sudah ada yang diisi
                if(editNamaPeserta.getText().length() != 0 || editNotelp.getText().length() != 0
                        || editAlamat.getText().length() != 0 || bmpImage != null){
                    discardConfirmation(dialog);
                }
                else{
                   dialog.dismiss();
                }
            }
        });

        //untuk menyimpan data
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Masih dalam pengembangan", Toast.LENGTH_SHORT).show();
            }
        });

        //Ketika gambar profile di klik akan menampilkan pilihan upload image
        imgThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    private void uploadImage() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.add_image_form, null, false);
        final TextView labelText = (TextView) view.findViewById(R.id.label_text);
        ImageButton btnTakePhoto = (ImageButton) view.findViewById(R.id.btn_take_photo);
        ImageButton btnGallery = (ImageButton) view.findViewById(R.id.btn_gallery);

        labelText.setTypeface(comicSansFont);

        final Dialog dialogImageUpload = new Dialog(getContext());
        dialogImageUpload.setContentView(view);
        dialogImageUpload.setCanceledOnTouchOutside(false);
        dialogImageUpload.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogImageUpload.show();

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTakePhotoClicked();
                dialogImageUpload.dismiss();
            }
        });
    }

    void discardConfirmation(final Dialog dialog){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Anda yakin ingin membatalkan aksi");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void displayImage(){
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openImage();
        }else {
            String[] permissionRequest = {Manifest.permission.READ_EXTERNAL_STORAGE};
//            ActivityCompat.requestPermissions(getActivity(), permissionRequest, IMAGE_GALERY_REQUEST_CODE);
        }
    }

    private void openImage() {
    }

    public void onTakePhotoClicked(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(getContext(),"Permission Already granted", Toast.LENGTH_SHORT).show();
            invokeCamera();
        }else {
            requestCameraPermission();
        }
    }

    private void requestCameraPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)){
            new AlertDialog.Builder(getContext())
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }else{
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                invokeCamera();
                //Toast.makeText(getContext(),"Permission Granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(),"Can't take photo without permission", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), "Gagal",Toast.LENGTH_SHORT).show();
        }
    }

    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        Toast.makeText(getContext(), "Laka Permission", Toast.LENGTH_SHORT).show();
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
////        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE){
////            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
////                invokeCamera();
////            }else {
////                Toast.makeText(getContext(),"Can't take photo without permission", Toast.LENGTH_SHORT).show();
////            }
////        }
//    }

    private void invokeCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_REQUEST_CODE){
                Object returnedObject = data.getExtras().get("data");

                if(returnedObject instanceof Bitmap){
                    bmpImage = (Bitmap) returnedObject;
                    imgThumbnail.setImageBitmap(bmpImage);
                }
            }
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

}

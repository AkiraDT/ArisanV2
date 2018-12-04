package com.jamingup.arisanv2;

import android.Manifest;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class KelompokFragment extends Fragment {
//    public static final int CAMERA_PERMISSION_REQUEST_CODE = 2;
//    public static final int IMAGE_GALERY_REQUEST_CODE = 10;
//    public static final int CAMERA_REQUEST_CODE = 1;
    private RecyclerView mRecyclerView;
    private AdapterKelompok mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FloatingActionButton fab;
    private Typeface TextMeOneStyle;
//    private CircleImageView imgThumbnail;
//    private Bitmap bmpImage;
//    String[] permissionRequest = {Manifest.permission.CAMERA};

    private KelompokViewModel kelompokViewModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kelompokViewModel = ViewModelProviders.of(this).get(KelompokViewModel.class);
        updateRecycler();

        TextMeOneStyle = Typeface.createFromAsset(getActivity().getAssets(), "fonts/TextMeOne-Regular.ttf");
    }

    private void updateRecycler() {
        kelompokViewModel.getAllKelompok().observe(this, new Observer<List<Kelompok>>() {
            @Override
            public void onChanged(@Nullable List<Kelompok> kelompoks) {
                //update RecyclerView
                mAdapter.submitList(kelompoks);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kelompok, container, false);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(),"Masih belum", Toast.LENGTH_SHORT).show();
                addKelompok();
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_kelompok);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());

        // specify an adapter (see also next example)
        mAdapter = new AdapterKelompok(TextMeOneStyle, getContext());
        mRecyclerView.setAdapter(mAdapter);

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
//                    fab.hide();
//                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
//                    fab.show();
//                }
//            }
//        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                kelompokViewModel.delete(mAdapter.getKelompokAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(),mAdapter.getKelompokAt(viewHolder.getAdapterPosition()).getNama() + " dihapus", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);

//        dataCheck();
        updateRecycler();
        return view;
    }

//    void dataCheck(){
//        if(pesertaViewModel.getAllPeserta() == null){
//            fab.hide();
//            mRecyclerView.setVisibility(View.GONE);
//            bigAddButton.setVisibility(View.VISIBLE);
//        }else{
//            fab.show();
//            mRecyclerView.setVisibility(View.VISIBLE);
//            bigAddButton.setVisibility(View.GONE);
//        }
//    }

    private void addKelompok(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.add_kelompok_form, null, false);
        final TextView namaKelompok = (TextView) view.findViewById(R.id.label_name);
        final TextView nominal = (TextView) view.findViewById(R.id.label_nominal);
        final TextView interval = (TextView) view.findViewById(R.id.label_interval);
        final TextView currency = (TextView) view.findViewById(R.id.currency);
        Button cancelButton = (Button) view.findViewById(R.id.cancel_button);
        Button acceptButton = (Button) view.findViewById(R.id.accept_button);
        final EditText editNamaKelompok = (EditText) view.findViewById(R.id.add_nama);
        final EditText editNominal = (EditText) view.findViewById(R.id.add_nominal);
        final EditText editInterval = (EditText) view.findViewById(R.id.add_interval);
//        imgThumbnail = (CircleImageView) view.findViewById(R.id.img_profile);

        namaKelompok.setTypeface(TextMeOneStyle);
        nominal.setTypeface(TextMeOneStyle);
        interval.setTypeface(TextMeOneStyle);
        currency.setTypeface(TextMeOneStyle);
        editNamaKelompok.setTypeface(TextMeOneStyle);
        editNominal.setTypeface(TextMeOneStyle);
        editInterval.setTypeface(TextMeOneStyle);
        cancelButton.setTypeface(TextMeOneStyle);
        acceptButton.setTypeface(TextMeOneStyle);
//        bmpImage = null;

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
                if(editNamaKelompok.getText().length() != 0 || editNominal.getText().length() != 0
                        || editInterval.getText().length() != 0){
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
                //Toast.makeText(getContext(), "Masih dalam pengembangan", Toast.LENGTH_SHORT).show();
                if(editNamaKelompok.getText().length() != 0 && editNominal.getText().length() != 0
                        && editInterval.getText().length() != 0/* && bmpImage != null*/){
                    saveDataKelompok(editNamaKelompok.getText().toString(), Integer.parseInt(editNominal.getText().toString()), Integer.parseInt(editInterval.getText().toString()));
                    dialog.dismiss();
                }else{
                    Toast.makeText(getContext(), "Data harus terisi Semua", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Ketika gambar profile di klik akan menampilkan pilihan upload image
//        imgThumbnail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadImage();
//            }
//        });
    }

//    private void uploadImage() {
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.add_image_form, null, false);
//        final TextView labelText = (TextView) view.findViewById(R.id.label_text);
//        ImageButton btnTakePhoto = (ImageButton) view.findViewById(R.id.btn_take_photo);
//        ImageButton btnGallery = (ImageButton) view.findViewById(R.id.btn_gallery);
//
//        labelText.setTypeface(TextMeOneStyle);
//
//        final Dialog dialogImageUpload = new Dialog(getContext());
//        dialogImageUpload.setContentView(view);
//        dialogImageUpload.setCanceledOnTouchOutside(false);
//        dialogImageUpload.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialogImageUpload.show();
//
//        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onTakePhotoClicked();
//                dialogImageUpload.dismiss();
//            }
//        });
//    }

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

//    public void displayImage(){
//        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
//            openImage();
//        }else {
//            String[] permissionRequest = {Manifest.permission.READ_EXTERNAL_STORAGE};
//        }
//    }

    private void openImage() {
    }

    public void onTakePhotoClicked(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(getContext(),"Permission Already granted", Toast.LENGTH_SHORT).show();
//            invokeCamera();
        }else {
//            requestCameraPermission();
        }
    }

//    private void requestCameraPermission(){
//        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)){
//            new AlertDialog.Builder(getContext())
//                    .setTitle("Permission needed")
//                    .setMessage("This permission is needed")
//                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
//                        }
//                    })
//                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .create().show();
//        }else{
//            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE){
//            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                invokeCamera();
//                //Toast.makeText(getContext(),"Permission Granted", Toast.LENGTH_SHORT).show();
//            }else {
//                Toast.makeText(getContext(),"Can't take photo without permission", Toast.LENGTH_SHORT).show();
//            }
//        }else{
//            Toast.makeText(getContext(), "Gagal",Toast.LENGTH_SHORT).show();
//        }
//    }

//    private void invokeCamera(){
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, CAMERA_REQUEST_CODE);
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == RESULT_OK){
//            if(requestCode == CAMERA_REQUEST_CODE){
//                Object returnedObject = data.getExtras().get("data");
//
//                if(returnedObject instanceof Bitmap){
//                    bmpImage = (Bitmap) returnedObject;
//                    imgThumbnail.setImageBitmap(bmpImage);
//                }
//            }
//        }
//    }

//    //Uuntuk menyimpan data peserta dan menambahakan ke list
    private void saveDataKelompok(String nama, int nominal, int interval){
        kelompokViewModel.insert(new Kelompok(nama, nominal, interval));
        updateRecycler();
        Toast.makeText(getContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
}
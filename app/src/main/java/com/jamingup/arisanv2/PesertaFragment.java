package com.jamingup.arisanv2;

import android.Manifest;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
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
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import static android.app.Activity.RESULT_OK;


public class PesertaFragment extends Fragment {

    public static final int CAMERA_PERMISSION_REQUEST_CODE = 2;
    public static final int GALERY_PERMISSION_REQUEST_CODE = 3;
    public static final int GALERY_REQUEST_CODE = 10;
    public static final int CAMERA_REQUEST_CODE = 1;
    private RecyclerView mRecyclerView;
    private AdapterPeserta mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fab;
    private Typeface TextMeOneStyle;
    private CircleImageView imgThumbnail;
    private Bitmap bmpImage;
    String[] permissionRequest = {Manifest.permission.CAMERA};

    private PesertaViewModel pesertaViewModel;

    private int girlIndex = 0, boyIndex = 0;
    private int girlPic [] = {R.drawable.girl_1_8, R.drawable.girl_2_8, R.drawable.girl_3_8, R.drawable.girl_4_8};
    private int boyPic [] = {R.drawable.boy_1_8, R.drawable.boy_2_8, R.drawable.boy_3_8, R.drawable.boy_4_8};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pesertaViewModel = ViewModelProviders.of(this).get(PesertaViewModel.class);
        updateRecycler();
        TextMeOneStyle = Typeface.createFromAsset(getActivity().getAssets(), "fonts/TextMeOne-Regular.ttf");
        if(savedInstanceState != null){
            girlIndex = savedInstanceState.getInt("girlIndex");
            boyIndex = savedInstanceState.getInt("boyIndex");
        }
    }

    private void updateRecycler() {
        pesertaViewModel.getAllPeserta().observe(this, new Observer<List<Peserta>>() {
            @Override
            public void onChanged(@Nullable List<Peserta> pesertas) {
                //update RecyclerView
                mAdapter.submitList(pesertas);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_peserta, container, false);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        // specify an adapter (see also next example)
        mAdapter = new AdapterPeserta(TextMeOneStyle, getContext());
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
                pesertaViewModel.delete(mAdapter.getPesertaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(),mAdapter.getPesertaAt(viewHolder.getAdapterPosition()).getNama() + " dihapus", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);
        updateRecycler();
        return view;
    }

    private void addPeserta(){
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.add_peserta_form, null, false);
        final TextView namaPeserta = (TextView) view.findViewById(R.id.label_name);
        final TextView notelp = (TextView) view.findViewById(R.id.label_telp);
        final TextView alamat = (TextView) view.findViewById(R.id.label_alamat);
        final TextView kode = (TextView) view.findViewById(R.id.kode_reg);
        Button cancelButton = (Button) view.findViewById(R.id.cancel_button);
        Button acceptButton = (Button) view.findViewById(R.id.accept_button);
        final EditText editNamaPeserta = (EditText) view.findViewById(R.id.add_nama);
        final EditText editNotelp = (EditText) view.findViewById(R.id.add_telp);
        final EditText editAlamat = (EditText) view.findViewById(R.id.add_alamat);
        imgThumbnail = (CircleImageView) view.findViewById(R.id.img_profile);
        final TextView jenisKelamin = (TextView) view.findViewById(R.id.label_jenisKelamin);
        final RadioGroup jenisKelaminGroup = (RadioGroup) view.findViewById(R.id.add_jenisKelamin);

        namaPeserta.setTypeface(TextMeOneStyle);
        notelp.setTypeface(TextMeOneStyle);
        alamat.setTypeface(TextMeOneStyle);
        kode.setTypeface(TextMeOneStyle);
        editNamaPeserta.setTypeface(TextMeOneStyle);
        editNotelp.setTypeface(TextMeOneStyle);
        editAlamat.setTypeface(TextMeOneStyle);
        cancelButton.setTypeface(TextMeOneStyle);
        acceptButton.setTypeface(TextMeOneStyle);
        jenisKelamin.setTypeface(TextMeOneStyle);
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
                //Toast.makeText(getContext(), "Masih dalam pengembangan", Toast.LENGTH_SHORT).show();
                if(editNamaPeserta.getText().length() != 0 && editNotelp.getText().length() != 0
                        && editAlamat.getText().length() != 0){
                    //data validation
                    List<Peserta> pesertaList = mAdapter.getListPeserta();
                    boolean ada = false;
                    for (int i = 0; i <pesertaList.size() ; i++) {
                        if(pesertaList.get(i).getNama().equals(editNamaPeserta.getText().toString()))
                            ada = true;
                    }

                    if(ada) {
                        Toast.makeText(getContext(),"Nama Sudah ada", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        RadioButton radioButton = view.findViewById(jenisKelaminGroup.getCheckedRadioButtonId());
                        saveDataPeserta(editNamaPeserta.getText().toString(), editNotelp.getText().toString(), editAlamat.getText().toString(), bmpImage, radioButton.getText().toString());
                        dialog.dismiss();
                    }
                }else{
                    Toast.makeText(getContext(), "Data harus terisi Semua", Toast.LENGTH_SHORT).show();
                }
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
        Button btnTakePhoto = (Button) view.findViewById(R.id.btn_take_photo);
        Button btnGallery = (Button) view.findViewById(R.id.btn_gallery);
        labelText.setTypeface(TextMeOneStyle);
        btnTakePhoto.setTypeface(TextMeOneStyle);
        btnGallery.setTypeface(TextMeOneStyle);
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

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "in Development", Toast.LENGTH_SHORT).show();
                onGaleryClicked();
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

    public void onTakePhotoClicked(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(getContext(),"Permission Already granted", Toast.LENGTH_SHORT).show();
            invokeCamera();
        }else {
            requestCameraPermission();
        }
    }

    public void onGaleryClicked(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(getContext(),"Permission Already granted", Toast.LENGTH_SHORT).show();
            openGalery();
        }else {
            requestGaleryPermission();
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

    private void requestGaleryPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(getContext())
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(permissionRequest, GALERY_PERMISSION_REQUEST_CODE);
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
            requestPermissions(permissionRequest, GALERY_PERMISSION_REQUEST_CODE);
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
        }else if(requestCode == GALERY_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGalery();
//                Toast.makeText(getContext(),"Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Can't open galery without permission", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), "Gagal",Toast.LENGTH_SHORT).show();
        }
    }

    private void invokeCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    private void openGalery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Select Image"), GALERY_REQUEST_CODE);
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
            }else if(requestCode == GALERY_REQUEST_CODE){
                if(data.getData() != null){
                    Uri selectedImageUri = data.getData();
                    imgThumbnail.setImageURI(selectedImageUri);
                    try {
                        bmpImage = getThumbnail(selectedImageUri, getContext());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Bitmap getThumbnail(Uri uri, Context context) throws FileNotFoundException, IOException{
        InputStream input = context.getContentResolver().openInputStream(uri);
        final int THUMBNAIL_SIZE = 160;

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();

        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1)) {
            return null;
        }

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > THUMBNAIL_SIZE) ? (originalSize / THUMBNAIL_SIZE) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true; //optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//
        input = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }

    //Uuntuk menyimpan data peserta dan menambahakan ke list
    private void saveDataPeserta(String nama, String noTelp, String alamat, Bitmap img, String jenisKelamin){
        if(girlIndex == 4){
            girlIndex = 0;
        }
        if(boyIndex == 4){
            boyIndex = 0;
        }

        if(img == null){
            if(jenisKelamin.equalsIgnoreCase("laki-laki")){
                img = BitmapFactory.decodeResource(getContext().getResources(), boyPic[boyIndex]);
                boyIndex++;
            }else{
                img = BitmapFactory.decodeResource(getContext().getResources(), girlPic[girlIndex]);
                girlIndex++;
            }
        }

        byte[] image = bitmapToByteArray(img);
        pesertaViewModel.insert(new Peserta(nama, noTelp, alamat, image, jenisKelamin));
        updateRecycler();
        Toast.makeText(getContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
    }

    private byte[] bitmapToByteArray(Bitmap img) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] image = byteArrayOutputStream.toByteArray();
        return image;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("girlIndex", girlIndex);
        savedInstanceState.putInt("boyIndex", boyIndex);
    }
}

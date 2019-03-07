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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

import static android.app.Activity.RESULT_OK;


public class KelompokFragment extends Fragment {
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 2;
    public static final int GALERY_PERMISSION_REQUEST_CODE = 3;
    public static final int GALERY_REQUEST_CODE = 10;
    public static final int CAMERA_REQUEST_CODE = 1;
    private RecyclerView mRecyclerView;
    private AdapterKelompok mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FloatingActionButton fab;
    private Typeface TextMeOneStyle;
    private CircleImageView imgThumbnail;
    private Bitmap bmpImage;
    String[] permissionRequest = {Manifest.permission.CAMERA};

    private KelompokViewModel kelompokViewModel;

    private int picIndex = 0;
    private int []kelompokPic = {R.drawable.kelompok_1};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kelompokViewModel = ViewModelProviders.of(this).get(KelompokViewModel.class);
        updateRecycler();

        TextMeOneStyle = Typeface.createFromAsset(getActivity().getAssets(), "fonts/TextMeOne-Regular.ttf");
        if(savedInstanceState != null){
            picIndex = savedInstanceState.getInt("picIndex");
        }
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


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                kelompokViewModel.deleteAnggotaInKelompok(mAdapter.getKelompokAt(viewHolder.getAdapterPosition()).getNama());
                kelompokViewModel.delete(mAdapter.getKelompokAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), mAdapter.getKelompokAt(viewHolder.getAdapterPosition()).getNama() + " dihapus", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);

        updateRecycler();
        return view;
    }

    private void addKelompok() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.add_kelompok_form, null, false);
        final TextView namaKelompok = (TextView) view.findViewById(R.id.label_name);
        final TextView nominal = (TextView) view.findViewById(R.id.label_nominal);
        final TextView interval = (TextView) view.findViewById(R.id.label_interval);
        final TextView currency = (TextView) view.findViewById(R.id.currency);
        Button cancelButton = (Button) view.findViewById(R.id.cancel_button);
        Button acceptButton = (Button) view.findViewById(R.id.accept_button);
        final EditText editNamaKelompok = (EditText) view.findViewById(R.id.add_nama);
        final EditText editNominal = (EditText) view.findViewById(R.id.add_nominal);
        final RadioGroup intervalGroup = (RadioGroup) view.findViewById(R.id.add_interval);
        imgThumbnail = (CircleImageView) view.findViewById(R.id.img_profile);

        namaKelompok.setTypeface(TextMeOneStyle);
        nominal.setTypeface(TextMeOneStyle);
        interval.setTypeface(TextMeOneStyle);
        currency.setTypeface(TextMeOneStyle);
        editNamaKelompok.setTypeface(TextMeOneStyle);
        editNominal.setTypeface(TextMeOneStyle);

        cancelButton.setTypeface(TextMeOneStyle);
        acceptButton.setTypeface(TextMeOneStyle);
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
                if (editNamaKelompok.getText().length() != 0 || editNominal.getText().length() != 0) {
                    discardConfirmation(dialog);
                } else {
                    dialog.dismiss();
                }
            }
        });

        //untuk menyimpan data
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Masih dalam pengembangan", Toast.LENGTH_SHORT).show();
                if (editNamaKelompok.getText().length() != 0 && editNominal.getText().length() != 0) {
                    double nominal = Double.parseDouble(editNominal.getText().toString());
                    if(nominal <= 100000000) {
                        RadioButton radioButton = view.findViewById(intervalGroup.getCheckedRadioButtonId());
                        saveDataKelompok(editNamaKelompok.getText().toString(), Integer.parseInt(editNominal.getText().toString()), radioButton.getText().toString(), bmpImage);
                        dialog.dismiss();
                    }else {
                        Toast.makeText(getContext(), "Maksimal Hadiah 100jt", Toast.LENGTH_SHORT).show();
                    }
                } else {
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

    //untuk dipanggil ke PilihAnggotaFragment
    public void updateJumlahAnggota(Kelompok kelompok){
        updateRecycler();
        if(kelompok.getId() != -1) {
            kelompokViewModel.update(kelompok);
        }
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
                onGaleryClicked();
                dialogImageUpload.dismiss();
            }
        });
    }

    void discardConfirmation(final Dialog dialog) {
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

    public void onTakePhotoClicked() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(getContext(),"Permission Already granted", Toast.LENGTH_SHORT).show();
            invokeCamera();
        } else {
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

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
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
        } else {
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
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                invokeCamera();
                //Toast.makeText(getContext(),"Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Can't take photo without permission", Toast.LENGTH_SHORT).show();
            }
        } else if(requestCode == GALERY_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGalery();
//                Toast.makeText(getContext(),"Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Can't open galery without permission", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();
        }
    }

    private void invokeCamera() {
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
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Object returnedObject = data.getExtras().get("data");

                if (returnedObject instanceof Bitmap) {
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

    public static Bitmap getThumbnail(Uri uri, Context context) throws FileNotFoundException, IOException {
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

    //    //Uuntuk menyimpan data peserta dan menambahakan ke list
    private void saveDataKelompok(String nama, int nominal, String interval, Bitmap img) {
        if(img == null){
            img = BitmapFactory.decodeResource(getContext().getResources(), kelompokPic[picIndex]);
        }
        byte[] image = bitmapToByteArray(img);
        kelompokViewModel.insert(new Kelompok(nama, nominal, interval, image));
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
        savedInstanceState.putInt("picIndex", picIndex);
    }
}

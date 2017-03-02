package com.dcastalia.android.job_portal.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dcastalia.android.job_portal.ContentProvider.MyFileContentProvider;
import com.dcastalia.android.job_portal.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by nusrat-pc on 12/15/16.
 */
public class Edit_profile_Frag_camera extends Fragment implements View.OnClickListener {
    Context context;

    SharedPreferences sp;

    private final int CAMERA_RESULT = 1;

    private static final int REQUEST_CODE = 1;
    private static final int SELECT_PHOTO = 100;
    private static final int CAMERA_REQUEST = 1;
    private static final int CHOOSE_IMAGE_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;


    //----------------------------------------------
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "MainActivity";
    final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

    int item;

    private final String Tag = getClass().getName();

    Button btn_pic_upload;
    Button btn_edit;
    Button btn_profile_update;

    private ImageView imageView;
    private Bitmap bitmap;

    String gender;


    private EditText inputProfession;
    private EditText inputBirthDate;
    private EditText inputPassport_Number;
    private EditText inputNID_Number;
    private EditText inputEmail;
    private EditText inputPhone_Number;
    private EditText inputAddress;

    //DatePickerDialog listener
    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            inputBirthDate.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                    + "-" + String.valueOf(year));
        }
    };
    private String Image_path;

    public Edit_profile_Frag_camera() {

    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.edit_profile_layout, container, false);

        inputProfession = (EditText) view.findViewById(R.id.profession);
        inputPassport_Number = (EditText) view.findViewById(R.id.passport_no);
        inputNID_Number = (EditText) view.findViewById(R.id.nid_no);
        inputEmail = (EditText) view.findViewById(R.id.input_email);
        inputPhone_Number = (EditText) view.findViewById(R.id.phone_no);
        inputAddress = (EditText) view.findViewById(R.id.input_address);
        //find the birth date id
        inputBirthDate = (EditText) view.findViewById(R.id.birth_date);
        imageView = (ImageView) view.findViewById(R.id.profile_pic_id);

        if (options[item].equals("Take Photo")){

            final Bitmap bmp = BitmapFactory.decodeFile("/data/data/com.dcastalia.android.job_portal/files/newImage.jpg");
            imageView = (ImageView) view.findViewById(R.id.profile_pic_id);
            imageView.setImageBitmap(bmp);

        }






        btn_pic_upload = (Button) view.findViewById(R.id.btn_pic_upload);
        btn_edit = (Button) view.findViewById(R.id.btn_edit);
        btn_profile_update = (Button) view.findViewById(R.id.btn_profile_update);


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "Update Now", Toast.LENGTH_LONG).show();

            }
        });

        btn_profile_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Your profile sucessfully updated", Toast.LENGTH_LONG).show();
            }
        });


        btn_pic_upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                selectImage();


//                PackageManager pm = getActivity().getPackageManager();
//
//                if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
//
//                    Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    i.putExtra(MediaStore.EXTRA_OUTPUT, MyFileContentProvider.CONTENT_URI);
//                    startActivityForResult(i, CAMERA_RESULT);
//
//                } else {
//
//                    Toast.makeText(getActivity().getBaseContext(), "Camera is not available", Toast.LENGTH_LONG).show();
//
//                }


            }


        });

 /*
    radio button on click event listener
     */
        view.findViewById(R.id.radio_male_bt).setOnClickListener(this);
        view.findViewById(R.id.radio_female_bt).setOnClickListener(this);


        return view;
    }
/*
Male and Female radio button listioner
 */

    public void radioClick(View v) {
        boolean checked = ((RadioButton) v).isChecked();

        switch (v.getId()) {
            case R.id.radio_male_bt:
                if (checked) {
                    gender = "male";
                }
                break;

            case R.id.radio_female_bt:
                if (checked) {
                    gender = "female";
                }
                break;
            default:
                break;
        }

    }

    /*
    radio button on click method
     */
    @Override
    public void onClick(View v) {
        radioClick(v);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /*
               call show date picker method and .when onclick show the dialogue
                */
                showDatePicker();
            }
        });
    }

    /*
    show date picker method show date picker dialogue
     */
    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }


    /*
DatePickerFragment is the inner class inside the edit profile fragment

 */
    public static class DatePickerFragment extends DialogFragment {
        DatePickerDialog.OnDateSetListener ondateSet;
        private int year, month, day;

        public DatePickerFragment() {


        }

        public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
            ondateSet = ondate;
        }

        @SuppressLint("NewApi")
        @Override
        public void setArguments(Bundle args) {
            super.setArguments(args);
            year = args.getInt("year");
            month = args.getInt("month");
            day = args.getInt("day");
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
        }
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == 1) {
//                File f = new File(Environment.getExternalStorageDirectory().toString());
//                for (File temp : f.listFiles()) {
//                    if (temp.getName().equals("temp.jpg")) {
//                        f = temp;
//                        break;
//                    }
//                }
//                try {
//                    Bitmap bitmap;
//                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//
//                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
//                            bitmapOptions);
//
//                    imageView.setImageBitmap(bitmap);
//
//                    String path = android.os.Environment
//                            .getExternalStorageDirectory()
//                            + File.separator
//                            + "Phoenix" + File.separator + "default";
//                    f.delete();
//                    OutputStream outFile = null;
//                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
//                    try {
//                        outFile = new FileOutputStream(file);
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
//                        outFile.flush();
//                        outFile.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            else if (requestCode == 2) {
//
//                Uri selectedImage = data.getData();
//                String[] filePath = { MediaStore.Images.Media.DATA };
//                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
//                c.moveToFirst();
//                int columnIndex = c.getColumnIndex(filePath[0]);
//                String picturePath = c.getString(columnIndex);
//                c.close();
//                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//                //Log.w("path of image from gallery......******************.........", picturePath+"");
//                imageView.setImageBitmap(thumbnail);
//            }
//        }
//    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Log.i(Tag, "Receive the camera result");
//
//        File out = null;
//        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_RESULT) {
//
//            out = new File(getActivity().getFilesDir(), "newImage.jpg");
//
//            Toast.makeText(getActivity().getBaseContext(),
//
//                    "Image captured and stored successfully", Toast.LENGTH_LONG)
//
//                    .show();
//
//
//            if (!out.exists()) {
//
//                Toast.makeText(getActivity().getBaseContext(),
//
//                        "Error while capturing image", Toast.LENGTH_LONG)
//
//                        .show();
//
//                return;
//
//            }
//
//            super.onActivityResult(requestCode, resultCode, data);
//
//
//            Bitmap mBitmap = BitmapFactory.decodeFile(out.getAbsolutePath());
//
//            imageView.setImageBitmap(mBitmap);
//
//        }
//
//
//        else {
//
//            try {
//                // We need to recyle unused bitmaps
//                if (bitmap != null) {
//                    bitmap.recycle();
//                }
//
//                InputStream stream = getContext().getContentResolver().openInputStream(
//                        data.getData());
//
//                super.onPause();
//
//
//                bitmap = BitmapFactory.decodeStream(stream);
//
//                stream.close();
//
//                imageView.setImageBitmap(bitmap);
//
//                OutputStream outp;
//                String root = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
//                File createDir = new File(root+"Folder Name"+File.separator);
//                if(!createDir.exists()) {
//                    createDir.mkdir();
//                }
//                File file = new File(root + "Folder Name" + File.separator +"Name of File");
//                file.createNewFile();
//                outp = new FileOutputStream(file);
//
////                outp.write(data);
////                outp.close();
//
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            super.onActivityResult(requestCode, resultCode, data);
//
//
//        }
//    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        Log.i(Tag, "Receive the camera result");

        File out = null;
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_RESULT) {

            out = new File(getActivity().getFilesDir(), "newImage.jpg");

            Toast.makeText(getActivity().getBaseContext(),

                    "Image captured and stored successfully", Toast.LENGTH_LONG)

                    .show();

            Fragment fragment = new Edit_profile_Frag();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.left_out);
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            if (!out.exists()) {

                Toast.makeText(getActivity().getBaseContext(),

                        "Error while capturing image", Toast.LENGTH_LONG)

                        .show();

                return;

            }

            super.onActivityResult(requestCode, resultCode, data);


            Bitmap mBitmap = BitmapFactory.decodeFile(out.getAbsolutePath());
            imageView.setImageBitmap(mBitmap);



        }

        else {

            if (resultCode == Activity.RESULT_OK) {

                Uri selectedImage = data.getData();

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                String picturePath = c.getString(columnIndex);

                c.close();

                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                saveToInternalStorage(thumbnail);

                Bitmap mBitmap = BitmapFactory.decodeFile(out.getAbsolutePath());
                imageView.setImageBitmap(mBitmap);

            }
        }

    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getContext());

        // path to /data/data/my_app/app_data/imageDir/profile.jpg
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


//    private void saveImage(Bitmap finalBitmap) {
//        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
//        //System.out.println(root +" Root value in saveImage Function");
//        File myDir = new File(root + "/job_portal");
//
//        if (!myDir.exists()) {
//            myDir.mkdirs();
//        }
//
//        Random generator = new Random();
//        int n = 10000;
//        n = generator.nextInt(n);
//        String iname = "Image-" + n + ".jpg";
//        File file = new File(myDir, iname);
//        if (file.exists())
//            file.delete();
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            out.flush();
//            out.close();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Tell the media scanner about the new file so that it is
//        // immediately available to the user.
//        MediaScannerConnection.scanFile(getContext(), new String[] { file.toString() }, null,
//                new MediaScannerConnection.OnScanCompletedListener() {
//                    public void onScanCompleted(String path, Uri uri) {
//                        Log.i("ExternalStorage", "Scanned " + path + ":");
//                        Log.i("ExternalStorage", "-> uri=" + uri);
//                    }
//                });
//
//        Image_path = Environment.getExternalStorageDirectory()+ "/Pictures/job_portal/"+iname;
//
//        File[] files = myDir.listFiles();
//      //  numberOfImages=files.length;
//        //System.out.println("Total images in Folder "+numberOfImages);
//    }


    public void onDestroy() {

        super.onDestroy();

        imageView = null;

    }


    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    PackageManager pm = getActivity().getPackageManager();

                    if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

                        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                        i.putExtra(MediaStore.EXTRA_OUTPUT, MyFileContentProvider.CONTENT_URI);
                        startActivityForResult(i, CAMERA_RESULT);


                    } else {

                        Toast.makeText(getActivity().getBaseContext(), "Camera is not available", Toast.LENGTH_LONG).show();

                    }

                } else if (options[item].equals("Choose from Gallery")) {



//                    Intent intent = new Intent(Intent.ACTION_PICK);
//                    intent.setType("image/*");
//                    startActivityForResult(intent, 1);


                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
//            try {
//                // We need to recyle unused bitmaps
//                if (bitmap != null) {
//                    bitmap.recycle();
//                }
//                InputStream stream = getContext().getContentResolver().openInputStream(
//                        data.getData());
//                bitmap = BitmapFactory.decodeStream(stream);
//                stream.close();
//                imageView.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//

    /**
     * open camera method
     */
//    public void callCamera() {
//        Intent cameraIntent = new Intent(
//                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, MyFileContentProvider.CONTENT_URI);
//        cameraIntent.putExtra("crop", "true");
//        cameraIntent.putExtra("aspectX", 0);
//        cameraIntent.putExtra("aspectY", 0);
//        cameraIntent.putExtra("outputX", 200);
//        cameraIntent.putExtra("outputY", 150);
//        startActivityForResult(cameraIntent, CAMERA_REQUEST);
//
//    }

//    /**
//     * open gallery method
//     */
//
//    public void callGallery() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 0);
//        intent.putExtra("aspectY", 0);
//        intent.putExtra("outputX", 200);
//        intent.putExtra("outputY", 150);
//        intent.putExtra("return-data", true);
//        startActivityForResult(intent,2);
//
//    }

    public int getCorrectCameraOrientation(Camera.CameraInfo info, Camera camera) {

        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch(rotation){
            case Surface.ROTATION_0:
                degrees = 0;
                break;

            case Surface.ROTATION_90:
                degrees = 90;
                break;

            case Surface.ROTATION_180:
                degrees = 180;
                break;

            case Surface.ROTATION_270:
                degrees = 270;
                break;

        }

        int result;
        if(info.facing== Camera.CameraInfo.CAMERA_FACING_FRONT){
            result = (info.orientation + degrees) % 360;
            result = (360-result) % 360;
        }else{
            result = (info.orientation-degrees + 360) % 360;
        }

        return result;
    }




}

package com.dcastalia.android.job_portal.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dcastalia.android.job_portal.ContentProvider.MyFileContentProvider;
import com.dcastalia.android.job_portal.Model.UserInfo;
import com.dcastalia.android.job_portal.R;
import com.dcastalia.android.job_portal.SqliteDatabase.SQLiteHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by imtiyaj-pc on 12/15/16.
 */
public class Edit_profile_Frag extends Fragment implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;
    private static final int SELECT_PHOTO = 100;
    private static final int CAMERA_REQUEST = 1;
    private static final int CHOOSE_IMAGE_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;

    //----------------------------------------------
    private static final int SELECT_PICTURE = 100;
    //private static final String TAG = "MainActivity";
    private final int CAMERA_RESULT = 1;
    //----------------------------------------------

   private final String Tag = getClass().getName();
    Context context;
    Button btn_pic_upload;
    Button btn_edit;
    Button btn_profile_update;
    String gender;
    private ImageView imageView;
    private Bitmap bitmap;
    private EditText inputProfession;
    private EditText inputBirthDate;


    //-------------------------------------

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    // Database Name
    public static final String DATABASE_NAME = "SampleDB";
    public static final String SampleDB = null;

    // Login table name
    private static final String TABLE_USER = "user";

    public SQLiteHandler db;

    private List<UserInfo> userinformation;


    //DatePickerDialog listener
    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            inputBirthDate.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                    + "-" + String.valueOf(year));
        }
    };
    private EditText inputPassport_Number;
    private EditText inputNID_Number;
    private EditText inputEmail;
    private EditText inputPhone_Number;
    private EditText inputAddress;
    private String Image_path;

    public Edit_profile_Frag() {

    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    // On create view method -------------------------------------------------
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


        final Bitmap bmp = BitmapFactory.decodeFile("/data/data/com.dcastalia.android.job_portal/files/newImage.jpg");
        imageView = (ImageView) view.findViewById(R.id.profile_pic_id);
        imageView.setImageBitmap(bmp);
        imageView.setEnabled(true);

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // Toast.makeText(getContext(), "Do you want to change your profile?", Toast.LENGTH_LONG).show();
            }
        });


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
                //call select image method  for opening gallery and camera
                selectImage();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        Log.i(Tag, "Receive the camera result");

        File out = null;
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_RESULT) {

            out = new File(getActivity().getFilesDir(), "newImage.jpg");

            Toast.makeText(getActivity().getBaseContext(),

                    "Image captured and stored successfully", Toast.LENGTH_LONG)

                    .show();

            if (!out.exists()) {

                Toast.makeText(getActivity().getBaseContext(), "Error while capturing image", Toast.LENGTH_LONG).show();

                return;

            }

            super.onActivityResult(requestCode, resultCode, data);

            Bitmap mBitmap = BitmapFactory.decodeFile("/data/data/com.dcastalia.android.job_portal/files/newImage.jpg");
            imageView.setImageBitmap(mBitmap);

        } else {

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

                Toast.makeText(getActivity().getBaseContext(),"Gallery image selected and saved successfully", Toast.LENGTH_LONG).show();

                Bitmap mBitmap = BitmapFactory.decodeFile("/data/data/com.dcastalia.android.job_portal/files/newImage.jpg");
                imageView.setImageBitmap(mBitmap);


            }
        }

    }

    //Save gallery image in app data directory
    private String saveToInternalStorage(Bitmap bitmapImage) {

        File mFile = new File(getContext().getFilesDir(), "newImage.jpg");


        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mFile);
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
        return mFile.getAbsolutePath();
    }


    public void onDestroy() {

        super.onDestroy();

        imageView = null;

    }

    /*
    Select image from gallery and camera
     */

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

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
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


/*
open gallery method
 */

    public void callGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                PICK_FROM_GALLERY);

    }




}

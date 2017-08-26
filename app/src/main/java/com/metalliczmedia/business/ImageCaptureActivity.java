package com.metalliczmedia.business;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class ImageCaptureActivity extends AppCompatActivity {

    ImageButton cameraButton, galleryButton;
    private Uri fileUri;
    String load;
    private int REQUEST_CAMERA = 0,SELECT_FILE = 1;

    final private int STORAGE_PERMISSION_CODE = 23;
    final int CAMERA_CODE = 1001;
    final int GALLERY_CODE = 1002;
    public Uri fileuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);
        galleryButton = (ImageButton) findViewById(R.id.selectButton);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load = "gallery";
                loadGallery(ImageCaptureActivity.this);

            }
        });
        cameraButton = (ImageButton) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load = "camera";
                loadGallery(ImageCaptureActivity.this);

            }
        });
    }

    public void loadGallery(Context context1) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED

                && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.CAMERA}, STORAGE_PERMISSION_CODE);

            return;
        } else {
            if (load.equals("gallery")) {
                takeFromGallery();
            } else if (load.equals("camera")) {
                captureImage();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takeFromGallery();
            } else if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void takeFromGallery() {
//        Intent localIntent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        localIntent.setType("image/*");
//        startActivityForResult(localIntent, GALLERY_CODE);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

    }

    private void captureImage() {


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);

//        Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
//        fileuri = getOutputMediaFileUri();
//        if (fileuri == null)
//            return;
//        localIntent.putExtra("output", fileuri);
//        startActivityForResult(localIntent, CAMERA_CODE);
    }


//    public Uri getOutputMediaFileUri() {
//        File localFile = getOutputMediaFile();
//        if (localFile != null)
//            return Uri.fromFile(localFile);
//        return null;
//    }

//    private File getOutputMediaFile() {
//        File localFile1 = new File(Environment.getExternalStorageDirectory() + File.separator + "Metallicz");
//        if ((!localFile1.exists()) && (!localFile1.mkdirs())) {
//            Toast.makeText(ImageCaptureActivity.this, "No SD card found", Toast.LENGTH_SHORT).show();
//            return null;
//        }
//        String str = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//        File localFile2 = new File(localFile1.getPath() + File.separator + "IMG_" + str + ".jpg");
//        localFile2.deleteOnExit();
//        return localFile2;
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            Bitmap localBitmap1=null;
            if (requestCode==REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                SharingFinal_Image.setOriginalBitmap(thumbnail);
                startActivity(new Intent(this, ImgeCropActivity.class));
                finish();

            }
            else if (requestCode == SELECT_FILE) {
                Bitmap bm;
                if (data != null) {
                    try {
                        bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                        SharingFinal_Image.setOriginalBitmap(bm);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }



                    startActivity(new Intent(this, ImgeCropActivity.class));
                finish();


               // bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

            }


//            if(requestCode==CAMERA_CODE){
//                try
//                {
//                    Bitmap localBitmap2 = BitmapScalingUtil.bitmapFromUri(this, fileuri);
//                    localBitmap1 = localBitmap2;
//                    SharingFinal_Image.setOriginalBitmap(localBitmap1);
//                    startActivity(new Intent(this, ImgeCropActivity.class));
//                    finish();
//
//                }catch (Exception e) {
//                    // TODO: handle exception
//                    e.printStackTrace();
//                }
 //           }
//            else if(requestCode==GALLERY_CODE){
//
//                try {
//                    SharingFinal_Image.setOriginalBitmap(BitmapScalingUtil.bitmapFromUri(this, data.getData()));
//                    startActivity(new Intent(this, ImgeCropActivity.class));
//                    finish();
//                } catch (FileNotFoundException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
//
//
//            }



        }
    }

}

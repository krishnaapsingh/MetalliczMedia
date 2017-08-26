package com.metalliczmedia.business;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Camera extends AppCompatActivity {
    ImageView iv, ivGallery ;
    Button b;
    String load;
    static EditText width, height;
    Intent i;
    final static int cameraData = 0;
    final static int galleryData = 2;
    final static int photoData = 1;
    int picdone = 0;
    Bitmap Bmp;
    Uri finaluri;
    Uri fileUri;
    String userChoosenTask;
    final private int STORAGE_PERMISSION_CODE = 23;
    final int CAMERA_CODE = 1001;
    final int GALLERY_CODE = 1002;

    public Uri fileuri;
    Uri imgURL,imgURLFinal;

    private float smallBrush, mediumBrush, largeBrush;

    private DrawingView drawView;
    Bitmap localBitmap2;

    private Bitmap bitmap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        b = (Button) findViewById(R.id.boardbutton);
        iv = (ImageView) findViewById(R.id.boardpic);
        width = (EditText) findViewById(R.id.boardwidth);
        height = (EditText) findViewById(R.id.boardheight);
        ivGallery = (ImageView) findViewById(R.id.gallerypic);

        drawView = (DrawingView) findViewById(R.id.drawing);

        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);


        drawView.setBrushSize(smallBrush);
        drawView.setColor("#FFFF0000");

        //  imgShow = (ImageView) findViewById(R.id.imageView);
        //crop_pic = (ImageView) findViewById(R.id.croppic);



        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load = "gallery";
drawView.startNew(null);
                loadGallery(Camera.this);
            }
        });

//        crop_pic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(Camera.this, ImgeCropActivity.class);
//                intent.putExtra("IMG_PRODUCT", "T_CAMERA");
//                startActivity(intent);
//
//finish();
//            }
//        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectImage();
                load = "camera";
                drawView.startNew(null);
                loadGallery(Camera.this);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (width.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please enter the Width", Toast.LENGTH_SHORT).show();
                } else if (height.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please enter the Height", Toast.LENGTH_SHORT).show();
                } else {
                    drawView.setDrawingCacheEnabled(true);
                    Toast.makeText(getApplicationContext(),
                            "Image Added", Toast.LENGTH_SHORT).show();
                    //Bitmap bitmap=drawView.getDrawingCache();
                    imgURLFinal= getImageUri(Camera.this,drawView.getDrawingCache());

                    MyAdapterClients.productdetails.add(new Products(imgURLFinal, width.getText().toString() + " x " + height.getText().toString(), Recee.producttype, Recee.productname));
                    Intent resultIntent=new Intent(getApplicationContext(),Recee.class);
                    startActivity(resultIntent);
                    drawView.destroyDrawingCache();
//                    Intent intent = new Intent(Camera.this, ImgeCropActivity.class);
//                    intent.putExtra("IMG_PRODUCT", "T_CAMERA");
//                    startActivity(intent);
                   finish();
                }
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//        if(resultCode==RESULT_OK )
//        {
//
//            if(requestCode==cameraData) {
//
//
////                Intent imageEditorIntent = new AdobeImageIntent.Builder(this)
////                        .setData(fileUri).withToolList(new ToolLoaderFactory.Tools[]{ToolLoaderFactory.Tools.DRAW})
////                        .withOutputSize(MegaPixels.Mp0)
////                        .withOutputQuality(30)
////                        .build();
////
////                startActivityForResult(imageEditorIntent, photoData);
//
//
//            }
//            else if(requestCode==galleryData) {
//                onSelectFromGalleryResult(data);
//            }
//            else if(requestCode==photoData)
//            {
//                Uri editedImageUri = data.getData();
//                iv.setBackground(null);
//                iv.setImageURI(editedImageUri);
//                finaluri=editedImageUri;
//                picdone=1;
//
//            }
//
//
//            if(requestCode==cameraData){
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
//            }else if(requestCode==galleryData){
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
//            }
//    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private static Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }


    private static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    private void onSelectFromGalleryResult(Intent data) {
//        if (data != null) {
//            try {
//                Intent imageEditorIntent = new AdobeImageIntent.Builder(this)
//                        .setData(data.getData()).withToolList(new ToolLoaderFactory.Tools[]{ToolLoaderFactory.Tools.DRAW})
//                        .withOutputSize(MegaPixels.Mp0)
//                        .withOutputQuality(30)
//                        .build();
//
//                startActivityForResult(imageEditorIntent, photoData);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

//    private void selectImage() {
//        final CharSequence[] items = {"Take Photo", "Choose from Library",
//                "Cancel"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(Camera.this);
//        builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (items[item].equals("Take Photo")) {
//                    userChoosenTask = "Take Photo";
//                    cameraIntent();
//                } else if (items[item].equals("Choose from Library")) {
//                    userChoosenTask = "Choose from Library";
//                    galleryIntent();
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }

//    private void cameraIntent() {
//        picdone = 0;
//        i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        fileUri = getOutputMediaFileUri();
//        i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//        startActivityForResult(i, cameraData);
//    }

//    private void galleryIntent() {
//        picdone = 0;
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(galleryIntent, galleryData);
//    }

    private void takeFromGallery() {
        Intent localIntent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        localIntent.setType("image/*");
        startActivityForResult(localIntent, GALLERY_CODE);
    }

    private void captureImage() {
//        Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
//        fileuri = getOutputMediaFileUri();
//        if (fileuri == null)
//            return;
//        localIntent.putExtra("output", fileuri);
//

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent ,CAMERA_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //Bitmap localBitmap1 = null;

            if (requestCode == CAMERA_CODE) {
               // try {
//                    onCaptureImageResult(data);
//                    drawView.setImageBitmap(bitmap);
//
//                     localBitmap2 = BitmapScalingUtil.bitmapFromUri(this, fileuri);
                  // imgURL = getImageUri(Camera.this, localBitmap2);
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                SharingFinal_Image.setOriginalBitmap(thumbnail);

//
                    drawView.startNew(thumbnail);



               // } catch (Exception e) {
                    // TODO: handle exception
                  //  e.printStackTrace();
                }
            else if (requestCode == GALLERY_CODE) {

                try {

                    localBitmap2 = BitmapScalingUtil.bitmapFromUri(this, data.getData());
                    SharingFinal_Image.setCam_originalBitmap(localBitmap2);
                    imgURL = getImageUri(Camera.this, localBitmap2);
                    drawView.startNew(localBitmap2);
                  //  imgShow.setImageBitmap(localBitmap2);
//                    SharingFinal_Image.setOriginalBitmap(BitmapScalingUtil.bitmapFromUri(this, data.getData()));
//
//                    imgShow.setImageBitmap(localBitmap2);
//                    startActivity(new Intent(this, ImgeCropActivity.class));
//                    finish();

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }
            else if(requestCode==21){

               // Toast.makeText(this, "After Crop"+data.getData(), Toast.LENGTH_LONG).show();

            }
        }
    }
   // }


    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
       // thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);


        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


       // localBitmap2 =getResizedBitmap(thumbnail,200,200);
       // drawView.startNew(bitmap);
       // drawView.startNew(localBitmap2);
        drawView.startNew(thumbnail);
       // drawView.setImageBitmap( localBitmap2);
       // drawView.startNew(bitmap);

        //encodedImage = getStringImage(bitmap);
       // userDetails.setImageProfile(encodedImage);
        //circularImageView.setImageBitmap(bitmap);


    }
    private Bitmap getResizedBitmap(Bitmap bitmap, int i, int i1) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) i)/width;
        float scaleHeight = ((float)i1)/height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return resizedBitmap;
    }


}
package com.metalliczmedia.business;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/*import com.adobe.creativesdk.aviary.AdobeImageIntent;
import com.adobe.creativesdk.aviary.internal.filters.ToolLoaderFactory;
import com.adobe.creativesdk.aviary.internal.headless.utils.MegaPixels;*/

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camera1 extends AppCompatActivity {
    ImageView iv;
    int position;
    Uri cardpic;
    String userChoosenTask;
    final static int photoData=1;
    final static int galleryData=2;
    final static int cameraData=0;

    Uri fileUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera1);

        iv= (ImageView) findViewById(R.id.installationpic);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        Intent in=getIntent();
        position=in.getIntExtra("position",0);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK )
        {

            if(requestCode==cameraData) {
//                Intent imageEditorIntent = new AdobeImageIntent.Builder(this)
//                        .setData(fileUri).withToolList(new ToolLoaderFactory.Tools[]{ToolLoaderFactory.Tools.DRAW})
//                        .withOutputSize(MegaPixels.Mp0)
//                        .withOutputQuality(30)
//                        .build();

            //    startActivityForResult(imageEditorIntent, photoData);
            }
            else if(requestCode==galleryData) {
                onSelectFromGalleryResult(data);
            }
            else if(requestCode==photoData){
                Uri editedImageUrinew = data.getData();
                cardpic=editedImageUrinew;
                Install.products.get(position).installpic=cardpic;
                iv.setBackground(null);
                iv.setImageURI(cardpic);
                Toast.makeText(getApplicationContext(), cardpic.toString(), Toast.LENGTH_LONG).show();
            }

        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                /*Intent imageEditorIntent = new AdobeImageIntent.Builder(this)
                        .setData(data.getData()).withToolList(new ToolLoaderFactory.Tools[]{})
                        .withOutputSize(MegaPixels.Mp0)
                        .withOutputQuality(30)
                        .build();

                startActivityForResult(imageEditorIntent, photoData);*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Camera1.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri();
        i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(i, cameraData);
    }

    private void galleryIntent()
    {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);//
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(Intent.createChooser(intent, "Select File"),galleryData);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, galleryData);
    }



    private static Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }


    private static File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");

        return mediaFile;
    }

}

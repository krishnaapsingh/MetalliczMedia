// "Therefore those skilled at the unorthodox
// are infinite as heaven and earth,
// inexhaustible as the great rivers.
// When they come to an end,
// they begin again,
// like the days and months;
// they die and are reborn,
// like the four seasons."
//
// - Sun Tsu,
// "The Art of War"

package com.metalliczmedia.business;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;


public final class CropResultActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * The image to show in the activity.
     */
    static Bitmap mImage;

    private ImageView imageView, imageView1, imageView2, imageView3, imageView4;
    public static Uri imgUri;
    // Bitmap final_bitmap;
    final private int STORAGE_PERMISSION_CODE = 23;
    File myDir;
    static File file;
    String filename, root;
    public static Uri contentUri;

    public static final String IMAGE_URI = "IMAGE_URI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crop_result);
        root = Environment.getExternalStorageDirectory().toString();
        myDir = new File(root + "/" + "Metallicz Media");
        myDir.mkdirs();

        imageView = ((ImageView) findViewById(R.id.resultImageView));
        // imageView.setBackgroundResource(R.drawable.backdrop);

        imageView1 = ((ImageView) findViewById(R.id.img1));
        imageView2 = ((ImageView) findViewById(R.id.img2));
        imageView3 = ((ImageView) findViewById(R.id.img3));
        imageView4 = ((ImageView) findViewById(R.id.img4));

        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);

        Intent intent = getIntent();
        if (mImage != null) {

            imgUri = getImageUri(this, mImage);
            DealerDetails.imgUri = imgUri;
            //  imageView.setImageBitmap(mImage);

            Glide.with(this).load(imgUri)
                    .into((imageView));

            Glide.with(this)
                    .load(imgUri)
                    .bitmapTransform(new SketchFilterTransformation(this))
                    .into(imageView1);

            Glide.with(this)
                    .load(imgUri)
                    .bitmapTransform(new GrayscaleTransformation(this))
                    .into(imageView2);

            Glide.with(this)
                    .load(imgUri).centerCrop()
                    .bitmapTransform(new SepiaFilterTransformation(this))
                    .into(imageView3);

            Glide.with(this).load(imgUri)
                    .into((imageView4));


            int sampleSize = intent.getIntExtra("SAMPLE_SIZE", 1);


            double ratio = ((int) (10 * mImage.getWidth() / (double) mImage.getHeight())) / 10d;
            int byteCount = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1) {
                byteCount = mImage.getByteCount() / 1024;
            }
            //String desc = "(" + mImage.getWidth() + ", " + mImage.getHeight() + "), Sample: " + sampleSize + ", Ratio: " + ratio + ", Bytes: " + byteCount + "K";
            // ((TextView) findViewById(R.id.resultImageText)).setText(desc);
        } else {
            Uri imageUri = intent.getParcelableExtra("URI");
            if (imageUri != null) {
                // imageView.setImageURI(imageUri);
                Glide.with(this).load(imageUri)

                        .into((imageView));

                Glide.with(this)
                        .load(imageUri)

                        .bitmapTransform(new SketchFilterTransformation(this))
                        .into(imageView1);

                Glide.with(this)
                        .load(imageUri)

                        .bitmapTransform(new GrayscaleTransformation(this))
                        .into(imageView2);
                Glide.with(this)
                        .load(imageUri)
                        .bitmapTransform(new SepiaFilterTransformation(this))
                        .into(imageView3);

                Glide.with(this).load(imageUri)

                        .into((imageView4));
            } else {
                Toast.makeText(this, "No image is set to show", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        releaseBitmap();
        super.onBackPressed();
    }

//    public void onImageViewClicked(View view) {
//        releaseBitmap();
//        finish();
//    }

    private void releaseBitmap() {
        if (mImage != null) {
            mImage.recycle();
            mImage = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseBitmap();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId() /*to get clicked view id**/) {
            case R.id.img1:
                Glide.with(this)
                        .load(imgUri)

                        .bitmapTransform(new SketchFilterTransformation(this))
                        .into(imageView);
                break;
            case R.id.img2:

                Glide.with(this)
                        .load(imgUri)

                        .bitmapTransform(new GrayscaleTransformation(this))
                        .into(imageView);

                break;
            case R.id.img3:

                Glide.with(this)
                        .load(imgUri)

                        .bitmapTransform(new SepiaFilterTransformation(this))
                        .into(imageView);

                break;
            case R.id.img4:

                Glide.with(this)
                        .load(imgUri)
                        .into(imageView);

                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_action_crop:
                Intent intent = new Intent();
                intent.putExtra(IMAGE_URI, imgUri);
                loadGallery(CropResultActivity.this);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void loadGallery(Context context1) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

            return;
        }
        saveImage(mImage);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Displaying a toast
                // Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_SHORT).show();
                loadGallery(CropResultActivity.this);
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Allow the permission for best result", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveImage(Bitmap finalBitmap) {


        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        filename = "Scanned File-" + n + ".jpg";
        file = new File(myDir, filename);

        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

                contentUri = Uri.fromFile(file); // out is your file you
                // saved/deleted/moved/copied
                mediaScanIntent.setData(contentUri);
                // Toast.makeText(this, "BItmap Uri"+String.valueOf(contentUri), Toast.LENGTH_SHORT).show();
                this.sendBroadcast(mediaScanIntent);


                getImageUri(this,finalBitmap);

            } else {
                contentUri = Uri.parse("file://" + Environment.getExternalStorageDirectory());
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            }
            out.flush();
            out.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
//        Intent intent = new Intent(this,CreatedItemsActivity.class);
//        startActivity(intent);

//        if(ImgeCropActivity.type!=null){
//
//            MyAdapterClients.productdetails.add(new Products(getImageUri(this,finalBitmap), Camera.width.getText().toString() + " x " + Camera.height.getText().toString(), Recee.producttype, Recee.productname));
//
//            Intent resultIntent=new Intent(getApplicationContext(),Recee.class);
//            startActivity(resultIntent);
//
//            finish();
//        }else{
            Dealer.storecard = contentUri;
            finish();
       // }

    }

}

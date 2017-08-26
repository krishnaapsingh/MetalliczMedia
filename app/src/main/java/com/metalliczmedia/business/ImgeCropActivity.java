package com.metalliczmedia.business;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ImgeCropActivity extends AppCompatActivity {
    private CropImageViewOptions mCropImageViewOptions = new CropImageViewOptions();
    private MainFragment mCurrentFragment;
    public  Bitmap bitmap;
    public static String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imge_crop);
//
//         type=getIntent().getStringExtra("IMG_PRODUCT");
//        if(type!=null){
//            bitmap = SharingFinal_Image.getCam_originalBitmap();
//        }else{
            bitmap = SharingFinal_Image.getOriginalBitmap();
       //}

       // Toast.makeText(this, String.valueOf(bitmap), Toast.LENGTH_SHORT).show();

        if (savedInstanceState == null) {
            setMainFragmentByPreset(CropDemoPreset.RECT ,bitmap);
        }

    }
    public void setCurrentFragment(MainFragment fragment) {
        mCurrentFragment = fragment;
    }
    public void setCurrentOptions(CropImageViewOptions options) {
        mCropImageViewOptions = options;
        //updateDrawerTogglesByOptions(options);
    }

    private void setMainFragmentByPreset(CropDemoPreset demoPreset, Bitmap bitmap ) {


//        Bundle bundle=new Bundle();
//        bundle.putString("BITMAP_DATA",String.valueOf(bitmap) );
//        MainFragment mainFrag=new MainFragment();
//        mainFrag.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(demoPreset , bitmap))
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mCurrentFragment != null && mCurrentFragment.onOptionsItemSelected(item)) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

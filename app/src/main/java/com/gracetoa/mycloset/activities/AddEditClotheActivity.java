package com.gracetoa.mycloset.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gracetoa.mycloset.R;
import com.gracetoa.mycloset.adapters.SubCategoryAdapter;
import com.gracetoa.mycloset.app.ColorUtils;
import com.gracetoa.mycloset.models.Category;
import com.gracetoa.mycloset.models.Clothe;
import com.gracetoa.mycloset.models.ColorName;
import com.gracetoa.mycloset.models.SubCategory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class AddEditClotheActivity extends AppCompatActivity  {

    private static final int REQUEST_TAKE_PHOTO = 100;
    private static final int REQUEST_GALLERY_PHOTO = 102;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private final String FILE_ROOT = "MyCloset/";
    private final String PATH_IMAGE = FILE_ROOT + "MyCloset";

    private Realm realm;
    private RealmResults<Category> categoryRealmList;
    private RealmList<SubCategory> subCategoryList;
    private SubCategory subCategory;
    private SubCategoryAdapter subCategoryAdapter;
    private ColorName colorName;
    private Spinner spinner;
    private FloatingActionButton fabAddPicture;
    private TextView textViewPixelColor;
    private Toolbar toolbar;
    private String path ;
    private Bitmap bitmap;
    private ImageView image;
    private int r,g,b;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_clothe);

        fabAddPicture = findViewById(R.id.fab_add_photo_clothe);
        image = findViewById(R.id.imageViewClothe);
        textViewPixelColor = findViewById(R.id.textViewPixelColor);
        toolbar = findViewById(R.id.toolbar);

        TextView customTitleToolbar = toolbar.findViewById(R.id.textTitleToolbar);
        setSupportActionBar(toolbar);
        customTitleToolbar.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        fabAddPicture.setEnabled(false);
        checkPermission();

        realm = Realm.getDefaultInstance();
        categoryRealmList = realm.where(Category.class).findAll();
        subCategoryList = new RealmList<SubCategory>();

        for (Category sc: categoryRealmList) {
            RealmList<SubCategory> realmResults = sc.getSubCategories();
            subCategoryList.addAll(realmResults.subList(0,realmResults.size()));
        }


        spinner = findViewById(R.id.spinner_categories);
        createSpinner(spinner);

        //image get Pixel Color
        colorName = null;
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == event.ACTION_DOWN || event.getAction() == event.ACTION_MOVE){
                    image.setDrawingCacheEnabled(true);
                    Bitmap bitmap = Bitmap.createBitmap(image.getDrawingCache());
                    image.setDrawingCacheEnabled(false);
                    int pixel = bitmap.getPixel((int) event.getX(),(int)event.getY());
                    r = Color.red(pixel);
                    g = Color.green(pixel);
                    b = Color.blue(pixel);
                    textViewPixelColor.setBackgroundColor(Color.rgb(r,g,b));
//                    textViewPixelColor.setText("R("+r+")"+"G("+g+")"+"B("+b+")");

                    ColorUtils colorUtils = new ColorUtils();
                    String nameColor = colorUtils.getColorNameFromRgb(r,g,b);
                    Toast.makeText(AddEditClotheActivity.this,nameColor,Toast.LENGTH_LONG).show();

                    colorName = new ColorName(nameColor,r,g,b);
                }
                return true;
            }
        });


    }


    //** REALM CRUD Actions **/
    private void createNewClothe( ColorName colorName, String imageClothe){
        Clothe clothe = new Clothe(colorName,imageClothe);
        realm.beginTransaction();
        realm.copyToRealm(clothe);
        subCategory.getClothes().add(clothe);
        realm.commitTransaction();
    }

    //** Menu Options *+/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                if(image.getDrawable().getConstantState().equals
                        (getResources().getDrawable(R.drawable.dresses).getConstantState()) && colorName == null){
                    Toast.makeText(this,"Please, Choose a Color and picture",Toast.LENGTH_LONG).show();
                }
                else if(colorName == null){
                    textViewPixelColor.setError("Enter a color");
                }else if(image.getDrawable().getConstantState().equals
                        (getResources().getDrawable(R.drawable.dresses).getConstantState())){
                    Toast.makeText(this,"Please, Choose a Color and picture",Toast.LENGTH_LONG).show();
                }
                else{
                    saveImageFromGalleyOrCameraToExternalStorage(bitmap);
                    createNewClothe(colorName,path);
                    clearClothes();
                    Toast.makeText(AddEditClotheActivity.this,"The Clothe have been added",Toast.LENGTH_LONG).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }


    private void createSpinner(Spinner spinner){
        subCategoryAdapter = new SubCategoryAdapter(subCategoryList,this);
        subCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(subCategoryAdapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                subCategory = (SubCategory)adapterView.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /** Actions Methods */

    public void onclick(View view) {
        chooseGalleryOrTakePhoto();
    }

    private void chooseGalleryOrTakePhoto(){
        final CharSequence [] options = {"Camera", "Gallery","Exit"};
        final AlertDialog.Builder alertOptions = new AlertDialog.Builder(AddEditClotheActivity.this);
        alertOptions.setTitle("Choose Option");
        alertOptions.setIcon(R.drawable.ic_action_clothes);
        alertOptions.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (options[i].equals("Camera") ){
                    startCamera();
                }
                if (options[i].equals("Gallery")){
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,REQUEST_GALLERY_PHOTO);
                }else {
                    dialog.dismiss();
                }
            }
        });
        alertOptions.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            switch (requestCode){
                case REQUEST_GALLERY_PHOTO:
                    Uri uri = data.getData();
                    image.setImageURI(uri);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                        image.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case REQUEST_TAKE_PHOTO:
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    bitmap = BitmapFactory.decodeFile(path,options);
                    image.setImageBitmap(bitmap);
                    break;
            }
        }else{
            Toast.makeText(this,"ERROR load Picture",Toast.LENGTH_LONG).show();
        }
    }


    private void startCamera(){
        //version 7 +
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        path = pathFileImage();

        File image = new File(path);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
        startActivityForResult(intent,REQUEST_TAKE_PHOTO);
    }

    private String pathFileImage(){
        File fileImagen=new File(Environment.getExternalStorageDirectory(),PATH_IMAGE);
        boolean exist=fileImagen.exists();
        String nameImage="";

        if(!exist){
            exist=fileImagen.mkdirs();
        }
        if(exist){
            nameImage=(System.currentTimeMillis()/1000)+".jpg";
        }
        String  pathImage=Environment.getExternalStorageDirectory()+
                File.separator+PATH_IMAGE+File.separator+nameImage;
        return pathImage;
    }


    private void saveImageFromGalleyOrCameraToExternalStorage(Bitmap finalBitmap){
        path = pathFileImage();
        File file = new File(path);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG,70,out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {
                Log.i("ExternalStorage","Scanned"+ path);
                Log.i("ExternalStorage","URI"+ uri);

            }
        });
    }

    private void clearClothes(){
        image.setImageResource(R.drawable.dresses);
        textViewPixelColor.setBackgroundColor(0);
        textViewPixelColor.setError(null);
    }



    /** PERMISSIONS MANIFEST*/

    private void checkPermission() {

        //version < 23
        if( Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
//            Toast.makeText(this, "This version is not Android 6 or later" + Build.VERSION.SDK_INT,Toast.LENGTH_LONG).show();
            fabAddPicture.setEnabled(true);
        }else {
            int hasWriteCameraPermission = checkSelfPermission((CAMERA));
            int hasWriteStoragePermission = checkSelfPermission(WRITE_EXTERNAL_STORAGE);

            if(hasWriteCameraPermission == PackageManager.PERMISSION_GRANTED && hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "The permissions are already granted" + Build.VERSION.SDK_INT,Toast.LENGTH_LONG).show();
                fabAddPicture.setEnabled(true);
            }
            else if ((shouldShowRequestPermissionRationale(CAMERA))|| (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
                Toast.makeText(this, "Requesting permissions Version: " + " "+ Build.VERSION.SDK_INT,Toast.LENGTH_LONG).show();
                loadDialogsPermissions();
            }else {
                requestPermissions(new String[]{CAMERA,WRITE_EXTERNAL_STORAGE},REQUEST_CODE_ASK_PERMISSIONS);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==REQUEST_CODE_ASK_PERMISSIONS){

            if (grantResults.length == 2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Ok Permissions granted¡",Toast.LENGTH_LONG).show();
                fabAddPicture.setEnabled(true);
            }else{
                Toast.makeText(this, "Permissions are not  granted",Toast.LENGTH_LONG).show();
                checkPermission();
            }
        }
        else{
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    private void loadDialogsPermissions() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(AddEditClotheActivity.this);
        dialog.setTitle("Unable Permissions");
        dialog.setMessage("You must accept permission for the ok working app");

        dialog.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},REQUEST_CODE_ASK_PERMISSIONS);
            }
        });
        dialog.show();
    }



}

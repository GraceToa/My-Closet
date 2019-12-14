package com.gracetoa.mycloset.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gracetoa.mycloset.R;
import com.gracetoa.mycloset.adapters.SubCategoryAdapter;
import com.gracetoa.mycloset.models.Category;
import com.gracetoa.mycloset.models.SubCategory;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


public class AddEditClotheActivity extends AppCompatActivity  {

    private Realm realm;
    private RealmResults<Category> categoryRealmList;
    private RealmList<SubCategory> subCategoryList;
    private SubCategoryAdapter subCategoryAdapter;
    private Spinner spinner;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_clothe);

        realm = Realm.getDefaultInstance();
        categoryRealmList = realm.where(Category.class).findAll();
        subCategoryList = new RealmList<SubCategory>();

        for (Category sc: categoryRealmList) {
            RealmList<SubCategory> realmResults = sc.getSubCategories();
            subCategoryList.addAll(realmResults.subList(0,realmResults.size()));
        }
        spinner = findViewById(R.id.spinner_categories);
        createSpinner(spinner);

        floatingActionButton = findViewById(R.id.fab_add_photo_clothe);


    }

    private void createSpinner(Spinner spinner){
        subCategoryAdapter = new SubCategoryAdapter(subCategoryList,this);
        subCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(subCategoryAdapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if(item != null){
//                    Toast.makeText(AddEditClotheActivity.this,item.toString(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
//                    takePhoto();
                    Toast.makeText(AddEditClotheActivity.this,"CAMERA",Toast.LENGTH_LONG).show();

                }
                if (options[i].equals("Gallery")){
                    //load image from Gallery
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent,REQUEST_GALLERY_PHOTO);
                    Toast.makeText(AddEditClotheActivity.this,"GALLERY",Toast.LENGTH_LONG).show();

                }else {
                    dialog.dismiss();

                }
            }
        });
        alertOptions.show();
    }


    public void onclick(View view) {

        chooseGalleryOrTakePhoto();
    }
}

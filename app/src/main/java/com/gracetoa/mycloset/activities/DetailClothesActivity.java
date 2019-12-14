package com.gracetoa.mycloset.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.gracetoa.mycloset.R;
import com.gracetoa.mycloset.models.Clothe;

public class DetailClothesActivity extends AppCompatActivity {


    private Clothe clothe;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_clothes);

        textView = findViewById(R.id.textClote);

//        clothe = (Clothe) getIntent().getSerializableExtra("clothe");

//        textView.setText((CharSequence) clothe.getColorName());

        String name = getIntent().getStringExtra("clotheID");
        textView.setText(name);

    }
}

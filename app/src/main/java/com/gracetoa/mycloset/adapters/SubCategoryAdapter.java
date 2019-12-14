package com.gracetoa.mycloset.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gracetoa.mycloset.R;
import com.gracetoa.mycloset.models.SubCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Project My Closet.
 * Created by gracetoa on 2019-12-02.
 */
public class SubCategoryAdapter extends ArrayAdapter<SubCategory> {

    public SubCategoryAdapter(List<SubCategory> subCategories,Context context){
        super(context,0,subCategories);

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    return initView(position,convertView,parent);

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_row, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name_subcateg);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.icon_subcateg);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SubCategory subCategory = getItem(position);
        viewHolder.name.setText(subCategory.getName());
        viewHolder.imageView.setImageResource(subCategory.getIcon());
//        Picasso.get().load(subCategory.getIcon()).fit().into(viewHolder.imageView);


        return convertView;

    }

    public class ViewHolder{
        TextView name;
        ImageView imageView;
    }
}

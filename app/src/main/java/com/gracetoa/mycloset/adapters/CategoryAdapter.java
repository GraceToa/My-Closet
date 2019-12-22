package com.gracetoa.mycloset.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gracetoa.mycloset.R;
import com.gracetoa.mycloset.models.Category;
import com.gracetoa.mycloset.models.Clothe;
import com.gracetoa.mycloset.models.SubCategory;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Project My Closet.
 * Created by gracetoa on 2019-12-22.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<SubCategory> subCategories;
    private int layout;
    private CategoryAdapter.OnItemClickListener itemClickListener;
    private Context context;

    public CategoryAdapter (List<SubCategory>subCategories, int layout, OnItemClickListener itemClickListener){
        this.subCategories = subCategories;
        this.layout = layout;
        this.itemClickListener = itemClickListener;

    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        context = parent.getContext();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.bind(subCategories.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView subcategoyName;
        public TextView categoryName;
        public TextView colorClothe;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subcategoyName = itemView.findViewById(R.id.subcateg_title);
            categoryName = itemView.findViewById(R.id.subcateg_categ);
            colorClothe = itemView.findViewById(R.id.subcteg_clothe_color);
            imageView = itemView.findViewById(R.id.subcat_photo_clothe);
        }

        public void bind (final SubCategory subCategory, final CategoryAdapter.OnItemClickListener onItemClickListener){



            List<Clothe>clothes = subCategory.getClothes();

            subcategoyName.setText(subCategory.getName());



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(subCategory,getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(SubCategory subCategory, int position);
    }

}

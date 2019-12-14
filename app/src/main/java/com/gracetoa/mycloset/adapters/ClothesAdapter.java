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
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Project My Closet.
 * Created by gracetoa on 2019-12-09.
 */
public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.ViewHolder> {

    private List<Clothe>clothesList;
    private List<Category>categories;
    private RealmList<SubCategory>subCategories;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;
    private int count;


    public ClothesAdapter(List<Clothe>clothesList, int layout, OnItemClickListener itemClickListener){
        this.clothesList = clothesList;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
        count = 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        context = parent.getContext();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClothesAdapter.ViewHolder holder, int position) {
        holder.bind(clothesList.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return clothesList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView subcategoyName;
        public TextView categoryName;
        public TextView colorClothe;
        public ImageView imageView;
        public ImageView iconview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subcategoyName = itemView.findViewById(R.id.clothes_subcateg);
            categoryName = itemView.findViewById(R.id.clothes_categ);
            colorClothe = itemView.findViewById(R.id.clothe_color);
            imageView = itemView.findViewById(R.id.clothes_photo);
        }

        public void bind (final Clothe clothes, final ClothesAdapter.OnItemClickListener onItemClickListener){

            RealmResults<Category>categoriesParent = null;
            RealmResults<SubCategory>subCategParent = null;

            subCategParent = clothes.getSubCategoryParent();

            for (SubCategory sb: subCategParent
            ) {
                subcategoyName.setText(sb.getName());
                sb.getCategoriesParent();
                for (Category cat: sb.getCategoriesParent()
                ) {
                    categoryName.setText(cat.getName());
                }
            }


            colorClothe.setText(String.valueOf(clothes.getId()));
            colorClothe.setTextColor(Color.rgb(clothes.getColorName().getR(),clothes.getColorName().getG(),clothes.getColorName().getB()));

            File imgFile = new File(clothes.getImageClothe());
            if(imgFile.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(bitmap);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(clothes,getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Clothe clothe, int position);
    }
}

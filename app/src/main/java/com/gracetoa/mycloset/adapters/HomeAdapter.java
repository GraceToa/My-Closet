package com.gracetoa.mycloset.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gracetoa.mycloset.R;
import com.gracetoa.mycloset.models.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Project My Closet.
 * Created by gracetoa on 2019-11-16.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<Category> categories;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;

    public HomeAdapter(List<Category> categories,int layout,OnItemClickListener itemClickListener ){
        this.categories = categories;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(categories.get(position),itemClickListener);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textVieTitle;
        public ImageView imageViewPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textVieTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            imageViewPoster = (ImageView)itemView.findViewById(R.id.imageViewPoster);
        }

        public void bind (final Category category, final  OnItemClickListener onItemClickListener){
            textVieTitle.setText(category.getName());
            Picasso.get().load(category.getImage()).fit().into(imageViewPoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(category, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Category category, int position);
    }


}

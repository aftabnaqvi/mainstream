package com.syed.mainstream.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.syed.mainstream.Models.Image;
import com.syed.mainstream.R;

import java.util.List;

/**
 * Created by snaqvi on 9/28/15.
 */
// Provide the underlying view for an individual list item.
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    private Context mContext;
    private List<Image> mImages;

    public ImagesAdapter(Activity context, List<Image> images) {
        mContext = context;
        if (images == null) {
            throw new IllegalArgumentException("images must not be null");
        }
        mImages = images;
    }

    public void addItems(List<Image> newItems){
        mImages.addAll(newItems);
    }

    // Inflate the view based on the viewType provided.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(itemView, mContext);
    }

    // Display data at the specified position
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Image image = mImages.get(position);
        holder.rootView.setTag(image);
        holder.tvName.setText(image.getName());
        holder.tvDescription.setText(image.getDescription());
        Picasso.with(mContext).load(Image.getImageBaseUrl()+image.getFilename()).into(holder.ivThumbnail);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    // Provide a reference to the views for each image item
    public final static class ViewHolder extends RecyclerView.ViewHolder {
        private final View rootView;
        private final ImageView ivThumbnail;
        private final TextView tvName;
        private final TextView tvDescription;

//        private final View vPalette;
//        private final ProgressBar progressbar;

        public ViewHolder(View itemView, final Context context) {
            super(itemView);
            rootView = itemView;
            ivThumbnail = (ImageView)itemView.findViewById(R.id.ivThumbnail);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            tvDescription = (TextView)itemView.findViewById(R.id.tvDescription);

            // Navigate to Image details activity on click of card view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Image image = (Image)v.getTag();
                    if (image != null) {
                        // Fire an intent when an image is selected
//                        Intent intent = new Intent(context, DetailsActivity.class);
//                        intent.putExtra(DetailsActivity.EXTRA_IMAGE_INFO, image);
//
//                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}



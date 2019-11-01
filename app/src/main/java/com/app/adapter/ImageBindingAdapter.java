package com.app.adapter;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

/*
 * Created by Yash on 13/6/18.
 */

public class ImageBindingAdapter
{
    @BindingAdapter({"app:image_url"})
    public static void loadImage(ImageView imageView, String url)
    {
        if (!TextUtils.isEmpty(url))
        {
            Picasso.get()
                    .load(url.replace("HTTP","http"))
                    .into(imageView);
        }
    }
}

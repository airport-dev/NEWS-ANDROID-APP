package com.example.st11_original_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;;
import com.squareup.picasso.Picasso;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.util.List;

public class TestAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private  int layoutID;
    Context context;
    private final List<Articles> articlelist;

    static class ViewHolder{
        ImageView img;
        TextView title;
        TextView author;
        TextView url;
    }


    public TestAdapter(Context context, int itemLayoutID,List<Articles> articles) {
        inflater = LayoutInflater.from(context);
        layoutID = itemLayoutID;
        articlelist = articles;
        this.context = context;

    }

    @Override
    public View getView(int position , View convertView ,ViewGroup parent){

        ViewHolder  holder;

        if(convertView == null){
            convertView = inflater.inflate(layoutID, null);
            holder = new ViewHolder();
            holder.img = convertView.findViewById(R.id.thumbnail);
            holder.title = convertView.findViewById(R.id.title);
            holder.author = convertView.findViewById(R.id.author);
            holder.url = convertView.findViewById(R.id.url);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        final Articles a = articlelist.get(position);

        String imageUrl = a.getUrlToImage();
        String title = a.getTitle();
        String author = a.getAuthor();
        String url = a.getUrl();

        if(author == null){
            holder.author.setText("掲載元不明");
        }else{
            holder.author.setText(author);
        }


        Picasso.with(context).load(imageUrl).into(holder.img);
        holder.title.setText(title);
        holder.url.setText(url);
        holder.url.setVisibility(View.GONE);


        return convertView;
    }

    @Override
    public int getCount() {
        return articlelist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

}

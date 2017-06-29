package com.example.news_app;

/**
 * Created by Leonard on 6/29/2017.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.news_app.Model.NewsItem;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder>{

    private ArrayList<NewsItem> data;
    ItemClickListener listen;

    public NewsAdapter(ArrayList<NewsItem> data,ItemClickListener listen){
        this.data = data;
        this.listen =listen;
    }
    public interface ItemClickListener{
        void onItemClick(int clickedItemIndex);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewTYpe){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.list_item,parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder,int position){
        holder.bind(position);
    }
    @Override
    public int getItemCount(){
        return data.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView description;
        TextView publish;

        ItemHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            description = (TextView)view.findViewById(R.id.description);
            publish = (TextView)view.findViewById(R.id.publish);

            view.setOnClickListener(this);
        }

        public void bind (int pos){
            NewsItem goodNews = data.get(pos);
            title.setText(goodNews.getTitle());
            description.setText(goodNews.getDescription());
            publish.setText(goodNews.getPublishDate());
        }

        @Override
        public void onClick(View v){
            int pos = getAdapterPosition();
            listen.onItemClick(pos);
        }
    }
}

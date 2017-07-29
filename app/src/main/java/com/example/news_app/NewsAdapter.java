package com.example.news_app;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.news_app.data.Contract;
import com.squareup.picasso.Picasso;

/**
 * Created by Leonard on 7/28/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder>{
    //The adapter that will get the data from the array and insert to the recycler view's views.

    private Cursor curse;
    private ItemClickListener listener;
    private Context context;
    public static final String TAG = "News Adapter";

    public NewsAdapter(Cursor curse, ItemClickListener listener){
        Log.d(TAG, "Setting the constructor");
        this.curse = curse;
        this.listener = listener;
    }

    public interface ItemClickListener {
        //what to do when the item is clicked on
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //getting the context from the main activity and inflating the layout for inserting the views to display
        Log.d(TAG, "made it to onCreate View");
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news,parent,shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);
        Log.d(TAG, "Returning the holder");
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        //send the position to the get binded to the items that will be displayed in the main activity
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        //return the count
        return curse.getCount();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private TextView description;
        private TextView publishedAt;
        private ImageView img;

        ItemHolder(View view){
            //find and set the views from the news layout.
            super(view);

            Log.d(TAG, "finding the views by id to bind");
            title = (TextView)view.findViewById(R.id.title);
            description = (TextView)view.findViewById(R.id.description);
            publishedAt = (TextView)view.findViewById(R.id.publishedAt);
            img=(ImageView)view.findViewById(R.id.img);
            view.setOnClickListener(this);
        }

        //binds the database info into the views to see it on the screen
        //also calls the url to retrieve the image and insert it into the imaage view
        public void bind(int pos){
            Log.d(TAG, "The binding process");
            curse.moveToPosition(pos);
            title.setText(curse.getString(curse.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE)));
            description.setText(curse.getString(curse.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION)));
            publishedAt.setText(curse.getString(curse.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_AT)));
            String url = curse.getString(curse.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_THUMBURL));

            //since all the articles have a picture url no need for the if statement
            Picasso.with(context)
                    .load(url)
                    .into(img);
        }

        @Override
        public void onClick(View v) {
            //send the position from the layout when it is clicked on.
            int pos = getAdapterPosition();
            listener.onItemClick(curse,pos);
        }
    }
}

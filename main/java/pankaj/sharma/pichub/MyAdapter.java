package pankaj.sharma.pichub;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.ALL;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    ArrayList<DataModels> arrayList = new ArrayList<>();
    DataModels models;

    public MyAdapter(Context context, ArrayList<DataModels> dataModelsArrayList) {
        super();
        this.context = context;
        this.arrayList = dataModelsArrayList;
        System.out.println("sdsds"+arrayList);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ViewHolder myViewHolder = (ViewHolder)holder;
        final DataModels models = arrayList.get(position);

        Glide.with(context).load(models.getPreviewURL())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .fitCenter()
                .diskCacheStrategy(ALL)
                .placeholder(R.drawable.ic_placeholder)
                .into(myViewHolder.imageView);
        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 =new Intent(context.getApplicationContext(),ImageDetailsActivity.class);
                intent2.putExtra("LARGEIMAGEURL",models.getLargeImageURL());
                intent2.putExtra("LIKES",models.getLikes());
                intent2.putExtra("VIEWS",models.getViews());
                intent2.putExtra("TAGS",models.getTags());
                intent2.putExtra("DOWNLOADS",models.getDownloads());
                context.startActivity(intent2);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
        }
    }
}

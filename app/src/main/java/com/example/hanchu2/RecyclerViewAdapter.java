package com.example.hanchu2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Boolean> mImages;
    private ArrayList<String> mNames;
    private ArrayList<String> mPaths;
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<Boolean> images, ArrayList<String> names,ArrayList<String> paths) {
        this.mImages = images;
        this.mNames = names;
        this.mPaths = paths;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "TEST: onBindViewHolder: called");
        if(mImages.get(position)==true){
            Glide.with(mContext)
                    .asBitmap()
                    .load(R.drawable.file_image)
                    .into(holder.fileImage);
        }else{
            Glide.with(mContext)
                    .asBitmap()
                    .load(R.drawable.folder_image)
                    .into(holder.fileImage);
        }
        holder.fileText.setText(mNames.get(position));

        holder.parentLayout.findViewById(R.id.share_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "TEST: onClick: clicked on share image at " + mNames.get(position));
                Toast.makeText(mContext, mNames.get(position),Toast.LENGTH_SHORT).show();
                MainShareDialog shareDialog = new MainShareDialog();
                FragmentManager manager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                shareDialog.show(manager, "Dialog Box");

            }
        });
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView fileImage;
        TextView fileText;
        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileImage = itemView.findViewById(R.id.file_image);
            fileText = itemView.findViewById(R.id.file_text);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}

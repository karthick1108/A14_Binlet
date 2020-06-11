package com.gloriousfour.binlet;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class UserListAdapter extends ArrayAdapter<User> {
    final Typeface mTypeFace = Typeface.createFromAsset(getContext().getAssets(), "regular.ttf");
    private static final String TAG = "UserListAdapter";
    private Context mContext;
    int mResource;

    public UserListAdapter(Context context, int resource, ArrayList<User> objects)
    {
        super(context,resource,objects);
        mContext = context;
        mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);
        int id= getItem(position).getId();
        String name=getItem(position).getName();
        String highScore=getItem(position).getScore();
        TextView userName = (TextView) convertView.findViewById(R.id.textView1);
        TextView score = (TextView) convertView.findViewById(R.id.textView2);
        TextView rankId = (TextView) convertView.findViewById(R.id.textView);
        userName.setText(name);
        score.setText(highScore+" "+"Points");
        rankId.setText(String.valueOf(id));
        userName.setTypeface(mTypeFace);
        score.setTypeface(mTypeFace);
        rankId.setTypeface(mTypeFace);
        rankId.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.lefttoright));
        userName.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.animate));
        score.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.righttoleft));
        return convertView;

    }
}

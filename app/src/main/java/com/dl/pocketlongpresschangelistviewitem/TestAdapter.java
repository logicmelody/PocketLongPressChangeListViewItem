package com.dl.pocketlongpresschangelistviewitem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by logicmelody on 15/4/1.
 */
public class TestAdapter extends ArrayAdapter<String> {

    static class ViewHolder {
        ViewGroup contentContainer;
        ViewGroup actionContainer;
        TextView title;
        Button showTitleButton;
        int position;
    }

    private Context mContext;
    private String[] mData;

    private View mPreviousLongClickItem;
    private int mPreviousLongClickItemPosition = -1;

    public TestAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        mContext = context;
        mData = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder viewHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        if (null != view && view.getTag() instanceof ViewHolder) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = layoutInflater.inflate(R.layout.test_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.contentContainer = (ViewGroup) view.findViewById(R.id.content_container);
            viewHolder.actionContainer = (ViewGroup) view.findViewById(R.id.action_container);
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.showTitleButton = (Button) view.findViewById(R.id.show_title_button);
            view.setTag(viewHolder);
        }
        viewHolder.position = position;

        setView(view);

        return view;
    }

    private void setView(final View view) {
        final ViewHolder currentViewHolder = (ViewHolder) view.getTag();
        currentViewHolder.title.setText(mData[currentViewHolder.position]);
        currentViewHolder.showTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mData[currentViewHolder.position], Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(View.VISIBLE == currentViewHolder.contentContainer.getVisibility()) {
                    currentViewHolder.actionContainer.setVisibility(View.VISIBLE);
                    Animation moveOutAnimation = AnimationUtils.loadAnimation(mContext, R.anim.move_out);
                    currentViewHolder.contentContainer.startAnimation(moveOutAnimation);
                    mPreviousLongClickItem = view;
                    mPreviousLongClickItemPosition = currentViewHolder.position;
                }
                return true;
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mPreviousLongClickItemPosition != -1 &&
                            mPreviousLongClickItemPosition != currentViewHolder.position) {
                            final ViewHolder previousViewHolder = (ViewHolder) mPreviousLongClickItem.getTag();
                            Animation moveInAnimation = AnimationUtils.loadAnimation(mContext, R.anim.move_in);
                            moveInAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    previousViewHolder.actionContainer.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            previousViewHolder.contentContainer.startAnimation(moveInAnimation);
                            mPreviousLongClickItemPosition = -1;
                        }
                        break;
                }
                return false;
            }
        });
    }

}

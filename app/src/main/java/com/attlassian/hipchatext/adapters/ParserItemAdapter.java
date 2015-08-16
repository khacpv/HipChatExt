package com.attlassian.hipchatext.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.attlassian.hipchatext.R;
import com.attlassian.hipchatext.models.ChatItem;
import com.attlassian.hipchatext.views.ChatItemView;

import java.util.List;

/**
 * Created by khacpham on 8/15/15.<br/>
 * Display ParserItem in cardView
 */
public class ParserItemAdapter extends RecyclerView.Adapter<ParserItemAdapter.ChatItemHolder> {

    private List<ChatItem> mParserItems;

    private boolean isAnimate = true;

    public ParserItemAdapter(List<ChatItem> items) {
        this.mParserItems = items;
    }

    @Override
    public ChatItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ChatItemView view = new ChatItemView(parent.getContext());
        return new ChatItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatItemHolder holder, int position) {

        holder.bind(mParserItems.get(position));

        if (isAnimate) {
            setAnimation(holder.itemView, position);
        }
    }

    @Override
    public int getItemCount() {
        return mParserItems.size();
    }

    private void setAnimation(final View viewToAnimate, int position) {
        if(position!=0){
            return;
        }
        Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.slide_in_down);
        viewToAnimate.startAnimation(animation);
    }

    public final void notifyDataSetChanged(boolean isAnimate) {

        this.isAnimate = isAnimate;
        notifyDataSetChanged();
    }

    /**
     * Chat item view with a ParserItemView inside.
     *
     * @see com.attlassian.hipchatext.views.ChatItemView
     */
    public static class ChatItemHolder extends RecyclerView.ViewHolder {

        ChatItemHolder(View itemView) {
            super(itemView);
        }

        /**
         * fetch ChatItem data to view
         *
         * @see com.attlassian.hipchatext.models.ChatItem
         */
        public void bind(ChatItem parserItem) {
            if (itemView instanceof ChatItemView) {
                ((ChatItemView) itemView).bindData(parserItem.getInput(), parserItem.getChatInfo());
            }
        }

    }
}

package com.attlassian.hipchatext.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.attlassian.hipchatext.R;
import com.attlassian.hipchatext.models.ChatInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by khacpham on 8/15/15.<br/>
 * The cardview display chat info data.
 */
public class ChatItemView extends LinearLayout{

    @Bind(R.id.tv_item_input)
    protected TextView mItemInput;

    @Bind(R.id.tv_item_result)
    protected TextView mItemResult;

    @Bind(R.id.prg_item_loading)
    protected ProgressBar mItemLoading;

    public ChatItemView(Context context) {
        super(context);
        initView();
    }

    public ChatItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ChatItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_parser_item,this,true);
        ButterKnife.bind(this,rootView);
    }

    public void bindData(String strInput,final ChatInfo chatData){
        mItemInput.setText(strInput);
        mItemResult.setText(chatData.toStringJson());
        mItemLoading.setVisibility(chatData.isLoading()?View.VISIBLE:View.GONE);
    }
}

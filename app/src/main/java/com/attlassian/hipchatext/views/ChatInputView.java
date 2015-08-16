package com.attlassian.hipchatext.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.attlassian.hipchatext.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by khacpham on 8/15/15.<br/>
 * form input chat text.
 */
public class ChatInputView extends LinearLayout{

    @Bind(R.id.tv_content)
    protected AutoCompleteTextView mTvContent;

    @Bind(R.id.button_send)
    protected Button mBtnSend;

    protected OnViewChatListener mViewChatListener;

    public ChatInputView(Context context) {
        super(context);
        initView();
    }

    public ChatInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ChatInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_chat,this,true);

        ButterKnife.bind(this, v);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.suggestion));
        mTvContent.setAdapter(adapter);

        setActionListener();
    }

    protected void setActionListener(){
        OnClickListener sendListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mTvContent.getText().toString();
                if(!TextUtils.isEmpty(content) && null != mViewChatListener){
                    mTvContent.setText("");
                    mViewChatListener.send(content);
                }
            }
        };

        mBtnSend.setOnClickListener(sendListener);
    }

    public void setOnViewChatListener(OnViewChatListener listener){
        this.mViewChatListener = listener;
    }

    public interface OnViewChatListener{
        void send(String text);
    }
}

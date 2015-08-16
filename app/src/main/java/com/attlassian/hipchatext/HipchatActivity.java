package com.attlassian.hipchatext;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.attlassian.hipchatext.adapters.ParserItemAdapter;
import com.attlassian.hipchatext.common.network.WebLoader;
import com.attlassian.hipchatext.common.parser.ChatParserMng;
import com.attlassian.hipchatext.models.ChatItem;
import com.attlassian.hipchatext.models.Link;
import com.attlassian.hipchatext.views.ChatInputView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * The start activity to display HipChat data.
 */
public class HipChatActivity extends AppCompatActivity {

    //================= CONSTANTS ======================

    /**
     * duration for dismiss delete notification.
     */
    private final int PERMANENT_DEL_DURATION = 1500;

    //================= VIEWS ======================

    /**
     * toolbar to display title app
     */
    protected Toolbar mToolbar;

    /**
     * to handle position of snackBar
     */
    @Bind(R.id.snack_position)
    protected View mSnackPositionLayout;

    /**
     * Main action button. Now it's only to clear all chat item.
     */
    @Bind(R.id.action_button)
    protected FloatingActionButton mActionButton;

    /**
     * display all chat items.
     */
    @Bind(R.id.content_layout)
    protected RecyclerView mContentLayout;

    /**
     * display an input chat.
     */
    @Bind(R.id.view_chat)
    protected ChatInputView mChatFormInput;

    //================= VARIABLES ======================

    /**
     * handle click 'unDelete' chats list.
     */
    private View.OnClickListener mUndoDeleteItems;

    /**
     * handle click clear chats list.
     */
    private View.OnClickListener mActionButtonListener;

    /**
     * handle send event.
     */
    private ChatInputView.OnViewChatListener mViewChatListener;

    /**
     * list chatItem for cache. User can unDelete.
     */
    private List<ChatItem> mCachedParserItems;

    /**
     * data set of ChatItem to display.
     */
    private List<ChatItem> mParserItems;

    /**
     * adapter to fetch ChatItem to ChatInput
     */
    private ParserItemAdapter mParserItemAdapter;

    /**
     * post a delete message to permanent clear data.
     */
    private Handler mHandler;

    /**
     * Runnable to permanent clean data after #PERMANENT_DEL_DURATION seconds.
     *
     * @see #PERMANENT_DEL_DURATION
     */
    private Runnable mPermanentCleanRunnable;

    /**
     * save title of string to cached.
     */
    private HashMap<String, String> mTitleUrlCached;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hipchat);

        setupActionBar();

        ButterKnife.bind(this);

        setupView();

        setupActionListener();

        mHandler = new Handler();

        mParserItems = new ArrayList<>();
        mCachedParserItems = new ArrayList<>();
        mParserItemAdapter = new ParserItemAdapter(mParserItems);
        mContentLayout.setAdapter(mParserItemAdapter);

        mTitleUrlCached = new HashMap<>();

        mActionButton.setOnClickListener(mActionButtonListener);
        mChatFormInput.setOnViewChatListener(mViewChatListener);
    }

    /**
     * init actionbar and disable default title
     */
    private void setupActionBar() {

        mToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * init views.
     */
    private void setupView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mContentLayout.setLayoutManager(linearLayoutManager);
    }

    /**
     * init listeners
     */
    private void setupActionListener() {

        mUndoDeleteItems = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mHandler.removeCallbacks(mPermanentCleanRunnable);

                mParserItems.clear();
                mParserItems.addAll(mCachedParserItems);
                mParserItemAdapter.notifyDataSetChanged(true);
            }
        };

        mActionButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCachedParserItems.clear();
                mCachedParserItems.addAll(mParserItems);

                mParserItems.clear();
                mParserItemAdapter.notifyDataSetChanged(false);

                Snackbar.make(mSnackPositionLayout, R.string.message_clean, Snackbar.LENGTH_LONG).setAction(R.string.message_undo, mUndoDeleteItems).setDuration(PERMANENT_DEL_DURATION).show();
                mHandler.removeCallbacks(mPermanentCleanRunnable);
                mHandler.postDelayed(mPermanentCleanRunnable, PERMANENT_DEL_DURATION << 1);
            }
        };

        mViewChatListener = new ChatInputView.OnViewChatListener() {
            @Override
            public void send(String text) {

                // display chat first.
                final ChatItem item = ChatParserMng.getInstance().parse(text);
                mParserItems.add(0, item);
                mParserItemAdapter.notifyDataSetChanged(true);

                // don't need to load title without link
                if (item.getChatInfo().getLinks().isEmpty()) {
                    return;
                }

                // load title from cache first, if cache miss load from internet.
                for (final Link link : item.getChatInfo().getLinks()) {

                    // from cache
                    String cachedTitle = mTitleUrlCached.get(link.getUrl());
                    if (null != cachedTitle) {
                        link.setLoaded(true);
                        link.setTitle(cachedTitle);
                        mParserItemAdapter.notifyDataSetChanged(false);
                        return;
                    }

                    // if cache miss, load via WebLoader.
                    new WebLoader(HipChatActivity.this).startLoad(link.getUrl())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<WebLoader.WebLoaderResult>() {
                            @Override
                            public void onCompleted() {
                                // update UI
                                link.setLoaded(true);
                                mParserItemAdapter.notifyDataSetChanged(false);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(WebLoader.WebLoaderResult webLoaderResult) {
                                // update cached first.
                                mTitleUrlCached.put(webLoaderResult.url, webLoaderResult.mWebView.getTitle());

                                // update link
                                String title = webLoaderResult.mWebView.getTitle();
                                link.setTitle(title);
                            }
                        });
                }
            }
        };

        mPermanentCleanRunnable = new Runnable() {
            @Override
            public void run() {
                mCachedParserItems.clear();
            }
        };
    }
}

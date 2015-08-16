package com.attlassian.hipchatext;

import android.support.design.widget.FloatingActionButton;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by khacpham on 8/15/15.
 */
public class HipChatActivityTest extends ActivityInstrumentationTestCase2<HipChatActivity> {

    HipChatActivity activity;

    public HipChatActivityTest(){
        super(HipChatActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    public void testActionButtonNotNull(){
        FloatingActionButton actionButton = (FloatingActionButton)activity.findViewById(R.id.action_button);
        assertNotNull(actionButton);
    }
}
package com.attlassian.hipchatext.common.parser;

import com.attlassian.hipchatext.models.ChatItem;

import junit.framework.TestCase;

/**
 * Created by khacpham on 8/15/15.
 */
public class ChatParserMngTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testChatParserHasUrl() {
        String input = "Olympics are starting soon; http://www.nbcolympics.com";
        ChatItem output = ChatParserMng.getInstance().parse(input);
        assertEquals(input,output.getInput());
        assertEquals("http://www.nbcolympics.com",output.getChatInfo().getLinks().get(0).getUrl());
    }

    public void testChatParserHasMention() {
        String input = "@chris you around?";
        ChatItem output = ChatParserMng.getInstance().parse(input);
        assertEquals(input,output.getInput());
        assertEquals("chris",output.getChatInfo().getMentions().get(0));
    }

    public void testChatParserHasEmoticon() {
        String input = "Good morning! (megusta) (coffee)";
        ChatItem output = ChatParserMng.getInstance().parse(input);
        assertEquals(input,output.getInput());
        assertEquals("megusta",output.getChatInfo().getEmoticons().get(0));
        assertEquals("coffee",output.getChatInfo().getEmoticons().get(1));
    }

    public void testChatParserHasParseSuccess(){
        String input = "@bob @john (success) such a cool feature; https://twitter.com/jdorfman/status/430511497475670016";
        ChatItem output = ChatParserMng.getInstance().parse(input);
        assertEquals(input,output.getInput());
        assertEquals("bob",output.getChatInfo().getMentions().get(0));
        assertEquals("john",output.getChatInfo().getMentions().get(1));

        assertEquals("success",output.getChatInfo().getEmoticons().get(0));

        assertEquals("https://twitter.com/jdorfman/status/430511497475670016",output.getChatInfo().getLinks().get(0).getUrl());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}

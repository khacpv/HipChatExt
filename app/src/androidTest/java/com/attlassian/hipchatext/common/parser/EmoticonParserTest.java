package com.attlassian.hipchatext.common.parser;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by khacpham on 8/15/15.
 */
public class EmoticonParserTest extends TestCase {

    EmoticonParser mEmoticonParser;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mEmoticonParser = new EmoticonParser();
    }

    public void testChatParserHasEmoticon() {
        String input = "Good morning! (megusta) (coffee)";
        List<String> output = mEmoticonParser.parse(input);

        assertEquals("megusta",output.get(0));
        assertEquals("coffee", output.get(1));
    }
}

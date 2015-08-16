package com.attlassian.hipchatext.common.parser;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by khacpham on 8/15/15.
 */
public class MentionParserTest extends TestCase {

    MentionParser mMentionParser;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMentionParser = new MentionParser();
    }

    public void testLinkParserNotNull(){
        // Given

        // When

        // Then
        assertNotNull(mMentionParser);
    }

    public void testMentionParserHasMention() {
        String input = "hello @myFriend, you are very handsome";
        List<String> output = mMentionParser.parse(input);

        assertEquals("myFriend",output.get(0));
    }

    public void testMentionParserDontParseMail() {
        String input = "my email is khacpv@attlassian.com";
        List<String> output = mMentionParser.parse(input);

        assertEquals(true, output.isEmpty());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mMentionParser = null;
    }
}

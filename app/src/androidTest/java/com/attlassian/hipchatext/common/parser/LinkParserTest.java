package com.attlassian.hipchatext.common.parser;

import com.attlassian.hipchatext.models.Link;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by khacpham on 8/15/15.
 */
public class LinkParserTest extends TestCase {

    LinkParser mLinkParser;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLinkParser = new LinkParser();
    }

    public void testLinkParserHasLink() {
        String input = "this is a link: http://google.com";
        List<Link> output = mLinkParser.parse(input);

        assertEquals("http://google.com",output.get(0).getUrl());
    }
}

package com.attlassian.hipchatext.common.parser;

import com.attlassian.hipchatext.models.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by khacpham on 8/15/15.<br/>
 * Utility for parse links.
 */
class LinkParser {

    /**
     * The standard pattern for URIs.
     * @see <a href="http://stackoverflow.com/questions/163360/regular-expression-to-match-urls-in-java">Regular expression to match URLs in Java</a>
     * */
    static final String STR_PATTERN = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    /**
     * get list of Link from input.
     * @return List of {@link com.attlassian.hipchatext.models.Link} object.
     * */
    public List<Link> parse(String input){
        Pattern pattern = Pattern.compile(STR_PATTERN);
        Matcher matcher = pattern.matcher(input);
        List<Link> result = new ArrayList<>();

        while(matcher.find()) {
            Link link = new Link();
            String url = matcher.group();
            link.setUrl(url);
            result.add(link);
        }
        return result;
    }
}

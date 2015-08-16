package com.attlassian.hipchatext.common.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by khacpham on 8/15/15.<br/>
 * Utility for parse emoticons.
 */
class EmoticonParser {

    /**
     * The pattern detect emotions between parenthesis (Ex: (happy) )
     * */
    static final String STR_PATTERN = "\\([a-zA-Z]+\\)";

    /**
     * get list of emoticons from input.
     * @return List emoticons
     * */
    public List<String> parse(String input){
        Pattern pattern = Pattern.compile(STR_PATTERN);
        Matcher matcher = pattern.matcher(input);
        List<String> result = new ArrayList<>();

        while(matcher.find()) {
            String emoticon = matcher.group().trim().replaceAll("\\(","").replaceAll("\\)","");
            result.add(emoticon);
        }
        return result;
    }
}

package com.attlassian.hipchatext.common.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by khacpham on 8/15/15.<br/>
 * Utility for parse mentions.
 */
class MentionParser {

    /**
     * The pattern start with @ at start of line or start of word (to avoid @ in an email case. Ex: abc@domain.com)
     * */
    static final String STR_PATTERN = "^@[a-zA-Z][a-zA-Z0-9]+|[\\s]@[a-zA-Z][a-zA-Z0-9]+";

    /**
     * get list of mentions from input.
     * @return List mentions
     * */
    public List<String> parse(String input){
        Pattern pattern = Pattern.compile(STR_PATTERN);
        Matcher matcher = pattern.matcher(input);
        List<String> result = new ArrayList<>();

        while(matcher.find()) {
            String mention = matcher.group().trim().replaceAll("@","");
            result.add(mention);
        }
        return result;
    }
}

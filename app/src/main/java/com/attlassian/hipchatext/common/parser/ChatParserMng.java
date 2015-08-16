package com.attlassian.hipchatext.common.parser;

import com.attlassian.hipchatext.models.ChatInfo;
import com.attlassian.hipchatext.models.ChatItem;

/**
 * Created by khacpham on 8/15/15.<br/>
 * Utility for parse string to {@link com.attlassian.hipchatext.models.ChatInfo} object.
 * @see MentionParser
 * @see EmoticonParser
 * @see LinkParser
 */
public class ChatParserMng {

    private static ChatParserMng mInstance;

    private EmoticonParser mEmoticonParser;
    private LinkParser mLinkParser;
    private MentionParser mMentionarser;

    private ChatParserMng(){
        mEmoticonParser = new EmoticonParser();
        mLinkParser = new LinkParser();
        mMentionarser = new MentionParser();
    }

    /**
     * @return an instance of ChatParser class.
     * @see #parse(String)
     * */
    public static ChatParserMng getInstance(){

        if(null == mInstance){
            mInstance = new ChatParserMng();
        }
        return mInstance;
    }

    /**
     * parse input of a string to {@link ChatItem} with mentions, emoticons, links.
     * @return ParserItem object
     * */
    public ChatItem parse(String input){

        ChatItem item = new ChatItem();
        item.setInput(input);

        ChatInfo chatData = new ChatInfo();
        chatData.setMentions(mMentionarser.parse(input));
        chatData.setEmoticons(mEmoticonParser.parse(input));
        chatData.setLinks(mLinkParser.parse(input));
        item.setChatInfo(chatData);

        return item;
    }
}

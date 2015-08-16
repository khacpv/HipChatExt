package com.attlassian.hipchatext.models;

/**
 * Created by khacpham on 8/15/15.<br/>
 * Holder input string and metadata of it.
 */
public class ChatItem {

    /**
     * text user input. This string should be parsed to ChatInfo object.
     * @see ChatInfo
     * */
    private String input;

    /**
     * object hold metadata of input string.
     * @see ChatInfo
     * */
    private ChatInfo chatInfo;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public ChatInfo getChatInfo() {
        return chatInfo;
    }

    public void setChatInfo(ChatInfo chatInfo) {
        this.chatInfo = chatInfo;
    }
}

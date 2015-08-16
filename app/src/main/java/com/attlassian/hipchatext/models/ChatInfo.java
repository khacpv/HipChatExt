package com.attlassian.hipchatext.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by khacpham on 8/15/15.<br/>
 * The class hold metadata of a chat item (mentions, emoticons, links).<br/>
 * To display data in pretty json format see {@link #toStringJson()}
 */
public class ChatInfo {

    /**
     * mentions that start with a '@' tag.
     * */
    private List<String> mentions;

    /**
     * emoticons between parenthesis.
     * */
    private List<String> emoticons;

    /**
     * links include url & title
     * @see com.attlassian.hipchatext.models.Link
     * */
    private List<Link> links;

    public List<String> getMentions() {
        return mentions;
    }

    public void setMentions(List<String> mentions) {
        this.mentions = mentions;
    }

    public List<String> getEmoticons() {
        return emoticons;
    }

    public void setEmoticons(List<String> emoticons) {
        this.emoticons = emoticons;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    /**
     * Make sure all of links are complete (loaded or error)
     * @return true: all link are loaded. <br/>false: has at less a link is loading
     * */
    public boolean isLoading(){
        if(null == links || links.isEmpty()){
            return false;
        }
        for(Link link: links){
            if(!link.isLoaded()){
                return true;
            }
        }
        return false;
    }

    /**
     * pretty print JSON output.
     * @return a string in pretty Json format.
     * */
    public String toStringJson(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}

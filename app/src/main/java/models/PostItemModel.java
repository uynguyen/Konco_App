package models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by LeeSan on 3/27/2016.
 */
public class PostItemModel implements Serializable {
    private String _title;
    private String _userName;
    private Date _postDate;
    private String _content;
    private ArrayList<String> _comments;
    private String _votes;
    private Bitmap _urlAvatar;
    public PostItemModel(){}
    public PostItemModel(String title, String username, Bitmap urlAvatar, Date postDate, String content,
                         ArrayList<String> comment, String vote){
        this.set_title(title);
        this.set_userName(username);
        this.set_urlAvatar(urlAvatar);
        this.set_postDate(postDate);
        this.set_content(content);
        this.set_comments(comment);
        this.set_votes(vote);
    }


    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_userName() {
        return _userName;
    }

    public void set_userName(String _userName) {
        this._userName = _userName;
    }

    public Date get_postDate() {
        return _postDate;
    }

    public void set_postDate(Date _postDate) {
        this._postDate = _postDate;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }

    public ArrayList<String> get_comments() {
        return _comments;
    }

    public void set_comments(ArrayList<String> _comments) {
        this._comments = _comments;
    }

    public String get_votes() {
        return _votes;
    }

    public void set_votes(String _votes) {
        this._votes = _votes;
    }

    public Bitmap get_urlAvatar() {
        return _urlAvatar;
    }

    public void set_urlAvatar(Bitmap _urlAvatar) {
        this._urlAvatar = _urlAvatar;
    }
}

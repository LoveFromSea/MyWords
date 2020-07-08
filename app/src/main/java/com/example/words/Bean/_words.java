package com.example.words.Bean;


import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class _words extends LitePalSupport {
    @Column(defaultValue = "Unknown")
    private String word;
    private String means;
    private String sentence;
    private String likewords;
    private String usphone;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeans() {
        return means;
    }

    public void setMeans(String means) {
        this.means = means;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getLikewords() {
        return likewords;
    }

    public void setLikewords(String likewords) {
        this.likewords = likewords;
    }

    public String getUsphone() {
        return usphone;
    }

    public void setUsphone(String usphone) {
        this.usphone = usphone;
    }
}

package com.example.words.Bean;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class _learned extends LitePalSupport {
    @Column(defaultValue = "Unknown")
    private String word;
    private String learned_day;

    public String getLearned_day() {
        return learned_day;
    }

    public void setLearned_day(String learned_day) {
        this.learned_day = learned_day;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}

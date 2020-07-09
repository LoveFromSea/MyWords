package com.example.words.Bean;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class _learned extends LitePalSupport {
    @Column(defaultValue = "Unknown")
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}

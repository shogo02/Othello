package com.example.othello.constants;

public enum EnumPlayer {
    RANDOM("Random", "ランダム"),

    USER("User", "ユーザー"),

    AI("AI", "AI"),
    ;

    private String english;
    private String japanese;

    EnumPlayer(String english, String japanese) {
        this.english = english;
        this.japanese = japanese;
    }

    public String getEnglish() {
        return english;
    }

    public String getJapanese() {
        return japanese;
    }
}

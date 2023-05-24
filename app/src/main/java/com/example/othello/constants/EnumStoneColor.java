package com.example.othello.constants;

public enum EnumStoneColor {
    BLACK("Black", "黒"),
    WHITE("White", "白"),
    ;

    private String english;

    private String japanese;

    EnumStoneColor(String english, String japanese) {
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

package com.app.response;

import java.util.List;

import lombok.Data;

@Data
public class DeepgramResponse {
    private Results results;

    @Data
    public static class Results {
        private List<Channel> channels;
    }

    @Data
    public static class Channel {
        private List<Alternative> alternatives;
    }

    @Data
    public static class Alternative {
        private String transcript;
        private Float confidence;
        private List<Word> words;
        private Paragraphs paragraphs;
    }

    @Data
    public static class Word {
        private String word;
        private Float start;
        private Float end;
        private Float confidence;
        private Integer speaker;
        private Float speakerConfidence;
        private String punctuatedWord;
    }

    @Data
    public static class Paragraphs {
        private String transcript;
        private List<Paragraph> paragraphs;
    }

    @Data
    public static class Paragraph {
        private Integer speaker;
        private Integer numWords;
        private Float start;
        private Float end;
        private List<Sentence> sentences;
    }

    @Data
    public static class Sentence {
        private String text;
        private Float start;
        private Float end;
    }

}

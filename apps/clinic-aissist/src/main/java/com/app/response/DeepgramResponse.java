package com.app.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.app.model.AudioChunk;
import com.app.model.AudioSession;

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

    public List<AudioChunk> toAudioChunks(AudioSession session, String audioUrl, int startingSequenceNumber) {
        List<AudioChunk> chunks = new ArrayList<>();
        int sequence = startingSequenceNumber;

        if (this.results != null && !this.results.getChannels().isEmpty()) {
            var alternatives = this.results.getChannels().get(0).getAlternatives();
            if (alternatives != null && !alternatives.isEmpty()) {
                var paragraphsWrapper = alternatives.get(0).getParagraphs();
                if (paragraphsWrapper != null && paragraphsWrapper.getParagraphs() != null) {
                    for (var paragraph : paragraphsWrapper.getParagraphs()) {
                        int speaker = paragraph.getSpeaker();
                        for (var sentence : paragraph.getSentences()) {
                            AudioChunk chunk = new AudioChunk();
                            chunk.setAudioSession(session);
                            chunk.setAudioUrl(audioUrl);
                            chunk.setSequenceNumber(sequence++);
                            chunk.setSpeakerLabel(String.valueOf(speaker));
                            chunk.setText(sentence.getText());
                            chunk.setStartTime(sentence.getStart());
                            chunk.setEndTime(sentence.getEnd());
                            chunk.setReceivedAt(LocalDateTime.now());
                            chunks.add(chunk);
                        }
                    }
                }
            }
        }
        return chunks;
    }

}

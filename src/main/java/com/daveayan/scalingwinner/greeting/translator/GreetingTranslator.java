package com.daveayan.scalingwinner.greeting.translator;

public class GreetingTranslator {
    String fromLanguage;
    String toLanguage;
    String incomingText;
    String translatedText;
    String status;

    public GreetingTranslator() {
    }

    public GreetingTranslator(String fromLanguage, String toLanguage, String incomingText, String translatedText, String status) {
        this.fromLanguage = fromLanguage;
        this.toLanguage = toLanguage;
        this.incomingText = incomingText;
        this.translatedText = translatedText;
        this.status = status;
    }

    public String getFromLanguage() {
        return this.fromLanguage;
    }

    public void setFromLanguage(String fromLanguage) {
        this.fromLanguage = fromLanguage;
    }

    public String getToLanguage() {
        return this.toLanguage;
    }

    public void setToLanguage(String toLanguage) {
        this.toLanguage = toLanguage;
    }

    public String getIncomingText() {
        return this.incomingText;
    }

    public void setIncomingText(String incomingText) {
        this.incomingText = incomingText;
    }

    public String getTranslatedText() {
        return this.translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return "|== " + fromLanguage + " == " + toLanguage + " == " + incomingText + " == " + translatedText + " == " + status + " ==|";
    }    
}

package model;

import java.util.Objects;

//todo Что этот класс делает в pages?
public class MessageData {//todo А накой ты имплементишь тут CharSequence????
    private String headline;
    private String text;
    private String author;

    public MessageData setHeadline(String headline) { //todo а почему with? Такие имена больше характерны для builder-а. У тебя не builder
        //поправил
        //todo setHeadline - это в лучшем случае getter
        //здесь должен быть set
        this.headline = headline;
        return this;
    }


    public MessageData setText(String text) {
        this.text = text;
        return this;
    }

    public MessageData setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public String getHeadline() {
        return headline;
    }

    public String getText() {
        return text;
    }


    @Override
    public String toString() {
        return "MessageData{" +
                "setHeadline='" + headline + '\'' +
                ", setText='" + text + '\'' +
                ", setAuthor='" + author + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageData that = (MessageData) o;
        return Objects.equals(headline, that.headline) &&
                Objects.equals(text, that.text) &&
                Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headline, text, author);
    }
}

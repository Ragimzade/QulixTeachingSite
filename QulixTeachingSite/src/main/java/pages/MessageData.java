package pages;

import java.util.Objects;
//todo Что этот класс делает в pages?
public class MessageData implements CharSequence {//todo А накой ты имплементишь тут CharSequence????
    private String headline;
    private String text;
    private String author;

    public MessageData headline(String headline) { //todo а почему with? Такие имена больше характерны для builder-а. У тебя не builder
        //поправил
        //todo headline - это в лучшем случае getter
        //здесь должен быть set
        this.headline = headline;
        return this;
    }

    public MessageData text(String text) {
        this.text = text;
        return this;
    }

    public MessageData author(String author) {
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
    public int length() { 
        return 0;
    }

    @Override
    public char charAt(int index) {
        return 0;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "headline='" + headline + '\'' +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public String findByXpath() {//todo createXpathForList - это корректнее
        //todo Не здесь в собственно в листе такой метод. Зачем метод поиска сообщения в списке засовывать в сообщение. 
        //Не забывай про логику
        return "[contains(.,'"+ headline + "')" + "and contains(.,'" + text + "')" + "and contains(.,'" + author + "')]";
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

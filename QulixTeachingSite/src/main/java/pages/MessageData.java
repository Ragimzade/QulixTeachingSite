package pages;

import java.util.Objects;

public class MessageData {
    private String name;
    private String text;
    private String author;

    public MessageData withName(String name) { //todo а почему with? Такие имена больше характерны для builder-а. У тебя не builder
        this.name = name;
        return this;
    }

    public MessageData withText(String text) {
        this.text = text;
        return this;
    }

    public MessageData withAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }


    @Override
    public String toString() {
        return "MessageData{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageData that = (MessageData) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(text, that.text) &&
                Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, text, author);
    }
}

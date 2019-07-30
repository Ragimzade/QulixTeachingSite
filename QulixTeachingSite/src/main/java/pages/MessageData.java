package pages;

import java.util.Objects;

public class MessageData {
    private String name;
    private String text;

    public MessageData withName(String name) {
        this.name = name;
        return this;
    }

    public MessageData withText(String text) {
        this.text = text;
        return this;
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageData that = (MessageData) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, text);
    }
}

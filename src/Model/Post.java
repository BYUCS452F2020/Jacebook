package Model;

import java.util.List;

public class Post {
    public Post(String alias, String content, String id, String name, List<String> urls, List<String> hashtags, List<String> mentions, String timestamp, String image, String video, String photo) {
        this.alias = alias;
        this.content = content;
        this.id = id;
        this.name = name;
        this.urls = urls;
        this.hashtags = hashtags;
        this.mentions = mentions;
        this.timestamp = timestamp;
        this.image = image;
        this.video = video;
        this.photo = photo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(name);
        sb.append(" @");
        sb.append(alias);
        sb.append(": ");
        sb.append(content);
        sb.append(" (id=");
        sb.append(id);
        sb.append(")");

        return sb.toString();
    }

    public String alias;
    public String content;
    public String photo;
    public String id;
    public String name;
    public List<String> urls;
    public List<String> hashtags;
    public List<String> mentions;
    public String timestamp;
    public String image;
    public String video;
}

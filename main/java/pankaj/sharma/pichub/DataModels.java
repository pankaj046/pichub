package pankaj.sharma.pichub;

public class DataModels {

     String largeImageURL;
     int likes;
     long id;
     long views;
     String webformatURL;
     String tags;
     long downloads;
     String previewURL;

    public DataModels(String largeImageURL, int likes, long id, long views, String webformatURL, String tags, long downloads, String previewURL) {
        this.largeImageURL = largeImageURL;
        this.likes = likes;
        this.id = id;
        this.views = views;
        this.webformatURL = webformatURL;
        this.tags = tags;
        this.downloads = downloads;
        this.previewURL = previewURL;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getDownloads() {
        return downloads;
    }

    public void setDownloads(long downloads) {
        this.downloads = downloads;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }
}

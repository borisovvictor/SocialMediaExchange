package entity;

public class Request {

    private double price;
    private int period;
    private String socialMedia;
    private String[] keyWords;

    public Request()
    {
    }

    public Request(double price, int period, String socialMedia, String[] keyWords)
    {
        this.price = price;
        this.period = period;
        this.socialMedia = socialMedia;
        this.keyWords = keyWords;
    }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public int getPeriod() { return period; }

    public void setPeriod(int period) { this.period = period; }

    public String getSocialMedia() { return socialMedia; }

    public void setSocialMedia(String socialMedia) { this.socialMedia = socialMedia; }

    public String[] getKeyWords() { return keyWords; }

    public void setKeyWords(String[] keyWords) { this.keyWords = keyWords; }

    //public boolean Satisfy
}

package com.kickstarter;

public class DataModel {

	private String id;
    private int sno;
    private int amt_pledge;
    private String num_backers;
    private String blurb,by,country,currency,end_time;
    private String location, percentage_funded;
    private String state, title;
    private String type, url;

    public DataModel(){

    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

	public int getSno() {
        return sno;
    }
    public void setSno(int sno) {
        this.sno = sno;
    }

    public int getAmt_pledge() {
        return amt_pledge;
    }
    public void setAmt_pledge(int amt_pledge) {
        this.amt_pledge = amt_pledge;
    }

    public String getBlurb() {
        return blurb;
    }
    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getBy() {
        return by;
    }
    public void setBy(String by) {
        this.by = by;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEnd_time() {
        return end_time;
    }
    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getPercentage_funded() {
        return percentage_funded;
    }
    public void setPercentage_funded(String percentage_funded) {
        this.percentage_funded = percentage_funded;
    }

    public String getNum_backers() {
        return num_backers;
    }
    public void setNum_backers(String num_backers) {
        this.num_backers = num_backers;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}

package com.example.edcpvg09;

public class Note {
    private String title;
    private String desp;
    private String name;
    private String imageurl;
    private String email;
    private String approvedBy;
    private int amount;

    public Note()
    {
        //empty
    }


    public Note(String title,String desp,String name,String imageurl,String email,String approvedBy,int amount) {

        this.title=title;
        this.amount=amount;
        this.approvedBy=approvedBy;
        this.imageurl=imageurl;
        this.desp=desp;
        this.email=email;
        this.name=name;

    }

    public String getTitle() {
        return title;
    }

    public String getDesp() {
        return desp;
    }

    public String getName() {
        return name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getEmail() {
        return email;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public int getAmount() {
        return amount;
    }
}

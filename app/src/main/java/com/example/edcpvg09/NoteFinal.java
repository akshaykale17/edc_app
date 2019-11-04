package com.example.edcpvg09;

public class NoteFinal {
    private String title;
    private String desp;
    private String fileName;
    private String imageurl;
    private int amount;
    private String approvedBy;

    public NoteFinal(String title, String desp, String fileName, String imageurl, int amount, String approvedBy) {
        this.title = title;
        this.desp = desp;
        this.fileName = fileName;
        this.imageurl = imageurl;
        this.amount = amount;
        this.approvedBy = approvedBy;
    }

    public String getTitle() {
        return title;
    }

    public String getDesp() {
        return desp;
    }

    public String getFileName() {
        return fileName;
    }

    public String getImageurl() {
        return imageurl;
    }

    public int getAmount() {
        return amount;
    }

    public String getApprovedBy() {
        return approvedBy;
    }
}

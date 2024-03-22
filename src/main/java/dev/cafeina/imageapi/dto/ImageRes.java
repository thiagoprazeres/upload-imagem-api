package dev.cafeina.imageapi.dto;

public class ImageRes {
    private String contentType;
    private String originalFilename;
    private long size;

    public String getContentType() {
        return this.contentType;
    }
    public String getOriginalFilename() {
        return this.originalFilename;
    }
    public long getSize() {
        return this.size;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }
    public void setSize(long size) {
        this.size = size;
    }
}

package org.example.models;

import java.util.Collections;
import java.util.List;

public class Pet {
    private long id;
    private String name;
    private List<String> photoUrls;
    private String status;

    public Pet() {}

    public Pet(long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.photoUrls = Collections.singletonList("https://example.com/photo.png");
        this.status = status;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getPhotoUrls() { return photoUrls; }
    public void setPhotoUrls(List<String> photoUrls) { this.photoUrls = photoUrls; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
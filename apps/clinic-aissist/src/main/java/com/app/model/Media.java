package com.app.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Media extends BaseModel {

    private String url;
    private Integer duration;
    private String name;
    private String type;
    private String size;
    private String hash;
    private String extension;
    private String mimeType;
    private String encoding;

}
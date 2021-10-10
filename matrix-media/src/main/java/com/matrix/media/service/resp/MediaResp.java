package com.matrix.media.service.resp;


import lombok.Data;

import java.util.Date;

@Data
public class MediaResp {
    private String mediaId;

    private String type;

    private Date createdAt;

    private String name;

    private String contentType;

    private Long size;

    private String thumbId;

    private String url;

    private String thumbUrl;
}

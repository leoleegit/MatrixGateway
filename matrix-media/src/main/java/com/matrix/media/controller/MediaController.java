package com.matrix.media.controller;

import com.matrix.core.controller.CommonCtrl;
import com.matrix.core.model.rest.Resp;
import com.matrix.core.model.security.AuthUserDetails;
import com.matrix.media.service.MediaService;
import com.matrix.media.service.resp.MediaResp;
import com.matrix.service.entity.Media;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping("/file")
public class MediaController extends CommonCtrl {

    @Autowired
    private MediaService mediaService;
    private static SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.getDefault());

    @PostMapping("/upload")
    public Resp<MediaResp> upload(@RequestParam("file") MultipartFile file, @Valid @RequestParam(name="type") String type) throws IOException {
        String user = null;
        AuthUserDetails authUserDetails = getCurrentUser();
        if(authUserDetails!=null)
            user = authUserDetails.getUsername();
        return mediaService.upload(user,type,file);
    }

    @PostMapping("profile/upload")
    public Resp<MediaResp> profileUpload(@RequestParam("file") MultipartFile file, @Valid @RequestParam(name="type") String type) throws IOException {
        String user = null;
        AuthUserDetails authUserDetails = getCurrentUser();
        if(authUserDetails!=null)
            user = authUserDetails.getUsername();
        return mediaService.upload(user,type,file,72,72);
    }

    @ResponseBody
    @GetMapping("/get")
    public ResponseEntity<?> get(@Valid @RequestParam(name="media_id") String id) throws IOException {
        Media media = mediaService.media(id);
        if(media==null)return ResponseEntity.status(404).body("Media file can't been found");
        InputStream  inputStream = mediaService.file(media.getBucket(),media.getMediaId());
        if(inputStream==null)return ResponseEntity.status(404).body("Media file can't been read");

        Date created_date    = media.getCreatedAt();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(media.getContentType()));
        headers.setContentLength(media.getSize());
        headers.setLastModified(created_date.getTime());
        headers.setContentDisposition(ContentDisposition.builder(mediaService.isMedia(media.getType())?"inline":"attachment").filename(media.getName()).build());
        return new ResponseEntity<byte[]>(IOUtils.toByteArray(inputStream),headers, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/bucket/{media_id}/{name}")
    public ResponseEntity<?> file(@PathVariable(name="media_id") String id,@Valid @RequestHeader(name="if-modified-since", required = false) String modified_since) throws IOException {
        Media media = mediaService.media(id);
        if(media==null)return ResponseEntity.status(404).body("Media file can't been found");

        String created_date  = sdf.format(media.getCreatedAt());
        if(modified_since!=null
               && modified_since.equals(created_date)
        ) {
            return ResponseEntity.status(304).build();
        }
        InputStream  inputStream = mediaService.file(media.getBucket(),media.getMediaId());
        if(inputStream==null)return ResponseEntity.status(404).body("Media file can't been read");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(media.getContentType()));
        headers.setContentLength(media.getSize());
        headers.set(HttpHeaders.LAST_MODIFIED,created_date);
        headers.setContentDisposition(ContentDisposition.builder(mediaService.isMedia(media.getType())?"inline":"attachment").filename(media.getName()).build());
        return new ResponseEntity<byte[]>(IOUtils.toByteArray(inputStream),headers, HttpStatus.OK);
    }
}

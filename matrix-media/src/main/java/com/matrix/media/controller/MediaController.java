package com.matrix.media.controller;

import com.matrix.core.controller.CommonCtrl;
import com.matrix.core.model.rest.Resp;
import com.matrix.core.model.security.AuthUserDetails;
import com.matrix.media.service.MediaService;
import com.matrix.media.service.resp.MediaResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class MediaController extends CommonCtrl {

    @Autowired
    private MediaService mediaService;

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

    @GetMapping("/get")
    public Resp<String> get(@Valid @RequestParam(name="media_id") String id, HttpServletResponse response) throws IOException {
        return mediaService.get(id,response);
    }

    @GetMapping("/bucket/{media_id}/{name}")
    public Resp<String> file(@PathVariable(name="media_id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return mediaService.file(id,request,response);
    }
}

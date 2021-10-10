package com.matrix.media.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.matrix.core.constants.CS;
import com.matrix.core.model.rest.Resp;
import com.matrix.media.MediaSwitch;
import com.matrix.media.service.resp.MediaResp;
import com.matrix.service.entity.Media;
import com.matrix.service.impl.MediaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
@Slf4j
public class MediaService {
    @Autowired
    private MediaServiceImpl mediaService;
    @Autowired
    private MediaSwitch mediaSwitch;

    public Resp<MediaResp> upload(String userid, String type, MultipartFile file) throws IOException {
        return upload(userid,type,file, CS.MediaLimit.thumbMaxWidth,CS.MediaLimit.thumbMaxHeight);
    }

    public Resp<MediaResp> upload(String createBy, String type, MultipartFile file, int thumbWidth, int thumbHeight) throws IOException {
        if(file.isEmpty()){
            Resp.fail("No media file found");
        }
        String contentType      = file.getContentType();
        InputStream inputStream = file.getInputStream();
        String name             = file.getOriginalFilename();
        long size               = file.getSize();

        String result = mediaSwitch.verify(type,contentType,size);
        if(result!=null)
            return Resp.fail(result);

        try {
            MediaResp mediaResp = new MediaResp();
            result = mediaSwitch.upload(inputStream,contentType,size);
            Media media = new Media();
            media.setMediaId(result);
            media.setContentType(contentType);
            media.setName(name);
            media.setCreatedAt(new Date(System.currentTimeMillis()));
            media.setSize(size);
            media.setCreatedBy(createBy);
            media.setType(type);
            media.setBucket(mediaSwitch.bucket());

            BeanUtils.copyProperties(media,mediaResp);
            boolean re = mediaService.save(media);

            String url = mediaSwitch.url(media);
            mediaResp.setUrl(url);

            if(CS.MediaType.image.equals(media.getType())){
                InputStream inputStream2 = mediaSwitch.get(media.getBucket(), media.getMediaId());
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                contentType              = "image/jpg";
                Thumbnails.of(inputStream2)
                        .size(thumbWidth,thumbHeight)
                        .outputFormat(CS.MediaLimit.thumb[0])
                        .toOutputStream(os);
                size                     = os.size();
                result = mediaSwitch.upload(StrUtil.format("{}-min",media.getMediaId()),new ByteArrayInputStream(os.toByteArray()),contentType,size);
                Media media2 = new Media();
                media2.setMediaId(result);
                media2.setContentType(contentType);
                media2.setName(name);
                media2.setCreatedAt(new Date(System.currentTimeMillis()));
                media2.setSize(size);
                media2.setCreatedBy(createBy);
                media2.setType(type);
                media2.setBucket(media.getBucket());
                mediaService.save(media2);

                String thumb_url = mediaSwitch.url(media2);
                mediaResp.setThumbUrl(thumb_url);
                media.setThumbId(result);
            }
            return re?Resp.success(mediaResp):Resp.fail("Upload fail, please try again later");
        }catch (Exception e){
            log.error("",e);
        }

        return Resp.fail("Upload fail, please try again later");
    }

    public Resp<String> get(String id, HttpServletResponse response){
        try {
            Media media = mediaService.getOne(new QueryWrapper<Media>().eq("media_id",id));
            if(media==null)
                return Resp.fail(StrUtil.format("Can't found media by {}",id));
            response.setHeader("Content-Disposition", StrUtil.format("attachment;filename={}",media.getName()));
            response.setContentType(media.getContentType());
            response.addHeader("Content-Length", String.valueOf(media.getSize()));

            InputStream inputStream = mediaSwitch.get(media.getBucket(),id);
            IOUtils.copy(inputStream,response.getOutputStream());

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            return Resp.success("ok");
        }catch (Exception e){
            log.error("",e);
        }
        return Resp.fail("Download fail, please try again later");
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.getDefault());
    public Resp<String> file(String id, HttpServletRequest request, HttpServletResponse response){
        try {
            Media media = mediaService.getOne(new QueryWrapper<Media>().eq("media_id",id));
            if(media==null)
                return Resp.fail(StrUtil.format("Can't found media by {}",id));
            response.setHeader("Content-Disposition", StrUtil.format("{};filename={}",(isMedia(media.getType()))?"inline":"attachment",media.getName()));            response.setContentType(media.getContentType());
            response.addHeader("Content-Length", String.valueOf(media.getSize()));

            Date created_date    = media.getCreatedAt();
            String last_modified = sdf.format(created_date);
            response.addHeader("last-modified", "" + last_modified);

            String modified_since = request.getHeader("if-modified-since");
            if (modified_since != null && modified_since.equals(last_modified)) {
                response.setStatus(304);
            }else{
                InputStream inputStream = mediaSwitch.get(media.getBucket(),id);
                IOUtils.copy(inputStream,response.getOutputStream());
            }
            return Resp.success("ok");
        }catch (Exception e){
            log.error("",e);
        }
        return Resp.fail("Download fail, please try again later");
    }

    private boolean isMedia(String type){
        if(CS.MediaType.image.equals(type))return true;
        if(CS.MediaType.video.equals(type))return true;
        if(CS.MediaType.voice.equals(type))return true;
        return false;
    }

}

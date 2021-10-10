package com.matrix.core.constants;

public class CS {

    public interface Sex {
        public static final byte UNKNOWN = 0;
        public static final byte MALE    = 1;
        public static final byte FEMALE  = 2;
    }

    public interface AuthType {
        public static final byte USER_NAME = 1;
        public static final byte PHONE     = 2;
        public static final byte EMAIL     = 3;
    }

    public interface CacheKey {
        public static final String Captcha = "Captcha";
        public static final String Token   = "Token";
    }

    public interface Role {
        public static final String SYSTEM_ADMIN = "sys_admin";
        public static final String USER_ADMIN   = "user_admin";
        public static final String USER         = "user";
    }

    public static final String DEFAULT_REQUEST_METHOD = "ALL" ;

    public interface PERMISSION_TREE {
        public static final int SYS      = 0;
        public static final int MENUS    = 1;
        public static final int FUNCTION = 2;
    }

    public static interface MediaType{
        public static final String image = "image";
        public static final String voice = "voice";
        public static final String video = "video";
        public static final String thumb = "thumb";
        public static final String attachment  = "attachment";
    }

    public static interface MediaLimit{
        public static final String[] image = {"PNG","JPEG","JPG","GIF"};
        public static final String[] voice = {"AMR","MP3"};
        public static final String[] thumb = {"JPG"};
        public static final String[] video = {"MP4"};
        public static final long video_size = 10 * 1024 * 1024;
        public static final long voice_size = 2 * 1024 * 1024;

        public static final int thumbMaxWidth  = 275;
        public static final int thumbMaxHeight = 312;
    }
}

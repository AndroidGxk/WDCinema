package com.bw.movie.core.utils;

/**
 * 常量
 */
public class Constant {
    
    // request参数
    public static final int REQ_QR_CODE = 11002; // // 打开扫描界面请求码
    public static final int REQ_PERM_CAMERA = 11003; // 打开摄像头
    public static final int REQ_PERM_POPHT = 11004; // 开启相册权限

    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";
    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wxb3852e6a6b7d9516";
    public static class ShowMsgActivity {
        public static final String STitle = "showmsg_title";
        public static final String SMessage = "showmsg_message";
        public static final String BAThumbData = "showmsg_thumb_data";
    }
}

package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.util.UploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UploadController {
    @RequestMapping("uploadImg")
    @ResponseBody
    public String uploadImg(MultipartFile pic) throws Exception {
        if (pic != null && pic.getSize() > 0) {
            String uploadAli = UploadUtil.uploadAli(pic);
            return uploadAli;
        }
        return null;
    }

    @RequestMapping("uploadImg_ck")
    @ResponseBody
    public Map<String ,Object> uploadImg_ck(MultipartFile upload) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        if (upload != null && upload.getSize() > 0) {

            String path = null;
            try {
                path = UploadUtil.uploadAli(upload);
                //如果上传成功  保存个1  和 路径
                map.put("uploaded",1);
                map.put("url",path);
            } catch (Exception e) {
                e.printStackTrace();
                //如果上传失败  保存个0  和  错误信息
                map.put("uploaded",0);
                HashMap<Object, Object> error = new HashMap<>();
                error.put("message",e.getMessage());
                map.put("error",error);
            }
        }
        return map;
    }
}

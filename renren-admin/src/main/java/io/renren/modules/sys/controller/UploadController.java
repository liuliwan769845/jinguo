package io.renren.modules.sys.controller;

import io.renren.common.utils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 上傳
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-01 09:18:22
 */
@RestController
@RequestMapping("sys/upload")
public class UploadController {
    @Value("${web.upload-path}")
    private  String path;
    private final String  imgUrl="http://127.0.0.1:8080/";
    @RequestMapping(value="/uploadImg",method= RequestMethod.POST)
    @ResponseBody
    public R upload(MultipartFile file, HttpServletRequest request)throws Exception{
        if (!file.isEmpty()) {
            String storePath= path+"/img";//存放我们上传的文件路径
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String fileName = new Date().getTime()+"."+suffix;
//            String fileName = new Date().getTime()+file.getOriginalFilename();
            File filepath = new File(storePath, fileName);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();//如果目录不存在，创建目录
            }
            try {
                file.transferTo(new File(storePath+File.separator+fileName));//把文件写入目标文件地址
            } catch (Exception e) {
                e.printStackTrace();
                return R.error();
            }
            return R.ok().put("fileName",imgUrl+"img/"+fileName).put("url",imgUrl+"img/"+fileName);
        }else {
            return R.error();
        }
    }

    @RequestMapping(value="/uploadVideo",method= RequestMethod.POST)
    @ResponseBody
    public R uploadVideo(MultipartFile file, HttpServletRequest request)throws Exception{
        if (!file.isEmpty()) {
            String storePath= path+"/video";//存放我们上传的文件路径
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String fileName = new Date().getTime()+"."+suffix;
            File filepath = new File(storePath, fileName);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();//如果目录不存在，创建目录
            }
            try {
                file.transferTo(new File(storePath+File.separator+fileName));//把文件写入目标文件地址
            } catch (Exception e) {
                e.printStackTrace();
                return R.error();
            }
            Map<String,String> map=new HashMap<>();
            map.put("src",imgUrl+"video/"+fileName);
            return R.ok().put("data",map).put("code",0);
        }else {
            return R.error();
        }
    }
    @RequestMapping(value="/uploadImgs",method= RequestMethod.POST)
    @ResponseBody
    public R uploadImgs(MultipartFile file, HttpServletRequest request)throws Exception{
        if (!file.isEmpty()) {
            String storePath= path+"/imgs";//存放我们上传的文件路径
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String fileName = new Date().getTime()+"."+suffix;
            File filepath = new File(storePath, fileName);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();//如果目录不存在，创建目录
            }
            try {
                file.transferTo(new File(storePath+File.separator+fileName));//把文件写入目标文件地址
            } catch (Exception e) {
                e.printStackTrace();
                return R.error();
            }
            Map<String,String> map=new HashMap<>();
            map.put("src",imgUrl+"imgs/"+fileName);
            return R.ok().put("data",map).put("code",0);
        }else {
            return R.error();
        }
    }
}

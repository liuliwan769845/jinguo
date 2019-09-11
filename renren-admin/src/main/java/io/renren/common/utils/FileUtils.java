package io.renren.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件图片上传工具包
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static List<String> filesUpload(MultipartFile[] files) {
        List<String> list = new ArrayList<String>();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                MultipartFile multipartFile = files[i];
                // 保存文件
                String upload = upload(multipartFile);
                list.add(upload);
            }
        }
        return list;
    }

    /**
     * @param file 文件
     * @return
     */
    public static String upload(MultipartFile file) {
        //获取主机端口
        String post = "8080";
        //获取本机ip
        String host = null;
        //图片存放根路径
        // todo 文件存放路径 记得更改
        String rootPath = "C:\\Users\\Administrator\\Desktop\\data";
        //图片存放根目录下的子目录
        String sonPath = "/img/";
        //获取图片链接
        String imgPath;
        //返回上传的文件是否为空，即没有选择任何文件，或者所选文件没有内容。
        //防止上传空文件导致奔溃
        if (file.isEmpty()) {
            logger.error("文件夹不能为空");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fileAdd = sdf.format(new Date());
        //获取本机IP
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("get server host Exception e:", e);
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 设置文件上传后的路径
        String filePath = rootPath + sonPath;
        String fileName2 = FileNameUtils.getFileName(fileName);
        logger.info("上传的文件路径" + filePath);
        logger.info("整个图片路径：" + host + ":" + post + sonPath + fileName2);
        //创建文件路径
        File dest = new File(filePath + fileName2);
        logger.info("创建文件路径" + dest);
        imgPath = (sonPath + fileName2).toString();
//        imgPath = (dest).toString();
        logger.info("imgPath" + imgPath);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            //假如文件不存在即重新创建新的文件已防止异常发生
            dest.getParentFile().mkdirs();
        }
        try {
            //保存文件
        	file.transferTo(dest);
            return imgPath;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "失败";
        } catch (IOException e) {
            e.printStackTrace();
            return "失败";
        }
    }
}

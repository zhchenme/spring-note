package com.jas.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;


/**
 * @author Jas
 * @create 2018-04-24 20:23
 **/
@Controller
public class FileController {
    
    @RequestMapping("/upload")
    public String upload(HttpServletRequest request, String description, MultipartFile file) throws Exception {
        if(!file.isEmpty()) {
            // 定义文件上传的路径
            String path = request.getServletContext().getRealPath("/images/");
            // 获得上传文件名
            String fileName = file.getOriginalFilename();
            File filepath = new File(path, fileName);
            
            // 判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            // 将上传文件保存到目标文件中
            file.transferTo(new File(path + File.separator + fileName));
            // 将上传图片描述信息与文件名保存在转发域中，用于下载
           request.setAttribute("description", description);
           request.setAttribute("fileName", fileName);
            return "success";
        } else {
            return "error";
        }
    }

    @RequestMapping(value="/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request, String fileName)throws Exception {
        // 获得下载文件路径
        String path = request.getServletContext().getRealPath("/images/");
        // 对中文文件名进行转码
        fileName = new String(fileName.getBytes("iso-8859-1"),"UTF-8");
        File file = new File(path + File.separator + fileName);
        
        // 文件下载的时候将文件名转码成浏览器可以识别的 ASCII
        String downLoadFileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", downLoadFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }
}

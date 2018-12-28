package com.zh.crm.entity;

import com.google.common.io.FileBackedOutputStream;
import com.zh.crm.util.FileUploadProperteis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {




    public static String dateToString(Date date, String format){
        if(date!=null){
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }else{
            return "";
        }
    }

    public static  void saveFile(String savePath,MultipartFile upload) {
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(savePath));
            out.write(upload.getBytes());
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

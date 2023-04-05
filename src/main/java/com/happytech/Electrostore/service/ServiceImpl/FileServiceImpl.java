package com.happytech.Electrostore.service.ServiceImpl;

import com.happytech.Electrostore.exceptions.BadApiRequest;
import com.happytech.Electrostore.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import static java.nio.file.Files.*;

@Service
public class FileServiceImpl implements FileService {


   private Logger logger = LoggerFactory.getLogger(FileService.class);
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        String originalFilename = file.getOriginalFilename();
        logger.info("filename : {} ",originalFilename);

        String filename = UUID.randomUUID().toString();

        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = filename+extension;
        String fullPathWithFileName = path + fileNameWithExtension;

        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpeg") || extension.equalsIgnoreCase(".jpg")){

            //file save
            File folder = new File(path);

            if(!folder.exists()){
                //create folder path
                folder.mkdirs();
            }
            //uploads
            copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;

        }else {
            throw new BadApiRequest("File name with this "+extension+" not allowed !!");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String fullPath = path + File.separator+ name ;

        InputStream inputStream = new FileInputStream(fullPath);


        return inputStream;
    }
}

package com.bc.pmpheep.ueditor.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bc.pmpheep.back.util.FileUpload;
import com.bc.pmpheep.general.bean.ImageType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.ueditor.PathFormat;
import com.bc.pmpheep.ueditor.define.AppInfo;
import com.bc.pmpheep.ueditor.define.BaseState;
import com.bc.pmpheep.ueditor.define.FileType;
import com.bc.pmpheep.ueditor.define.State;

public class BinaryUploader {

    @Autowired
    FileService    fileService;
    @Resource
    GridFsTemplate gridFsTemplate;

    public State save(HttpServletRequest request, Map<String, Object> conf) {
        FileItemStream fileStream = null;
        boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

        if (!ServletFileUpload.isMultipartContent(request)) {
            return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
        }

        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

        if (isAjaxUpload) {
            upload.setHeaderEncoding("UTF-8");
        }

        try {
            FileItemIterator iterator = upload.getItemIterator(request);

            while (iterator.hasNext()) {
                fileStream = iterator.next();

                if (!fileStream.isFormField())
                    break;
                fileStream = null;
            }

            if (fileStream == null) {
                return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
            }

            String savePath = (String) conf.get("savePath");
            String originFileName = fileStream.getName();
            String suffix = FileType.getSuffixByFilename(originFileName);

            originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
            savePath = savePath + suffix;

            long maxSize = ((Long) conf.get("maxSize")).longValue();

            if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
                return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
            }

            savePath = PathFormat.parse(savePath, originFileName);

            String physicalPath = (String) conf.get("rootPath") + savePath;

            InputStream is = fileStream.openStream();
            State storageState = StorageManager.saveFileByInputStream(is, physicalPath, maxSize);
            is.close();

            if (storageState.isSuccess()) {
                ApplicationContext ctx =
                WebApplicationContextUtils.getWebApplicationContext(request.getSession()
                                                                           .getServletContext());
                FileService fileService = (FileService) ctx.getBean("fileService");
                File file = FileUpload.getFileByFilePath(physicalPath);
                String picId = fileService.saveLocalFile(file, ImageType.SYS_MESSAGE, 1L);
                storageState.putInfo("url", "/image/" + picId);
                // storageState.putInfo("url", PathFormat.format(savePath));
                storageState.putInfo("type", suffix);
                storageState.putInfo("original", originFileName + suffix);
            }

            return storageState;
        } catch (FileUploadException e) {
            return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
        } catch (IOException e) {
            return new BaseState(false, AppInfo.IO_ERROR);
        }

    }

    private static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);

        return list.contains(type);
    }
}
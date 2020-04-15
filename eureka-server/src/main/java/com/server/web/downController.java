package com.server.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;

/**
 * @Date 2019/8/13 10:14
 */
public class downController {

    /**
     * @return org.springframework.http.ResponseEntity<byte   [   ]>
     * @Description 文件下载样例
     * @Date 2019/8/13 10:30
     * @Param [id, response]
     **/
    @GetMapping(value = "/file-download")
    public ResponseEntity<byte[]> batchDownloadReport(String id, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String fileName = System.currentTimeMillis() + ".xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.setCharacterEncoding("utf-8");
        byte[] bytes = null;
        try {
            /*File file = new File();
            if (Objects.nonNull(file)) {
                bytes = FileUtils.readFileToByteArray(file);
                FileUtils.deleteDirectory(file.getParentFile());
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    /**
     * @Description 文件上传
     * @Date 2019/8/13 10:34
     * @Param [multipartFile]
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     **/
    @PostMapping("/upload")
    public ResponseEntity<String> createInfoImportRecord(@RequestBody MultipartFile multipartFile) throws URISyntaxException {
        return ResponseEntity.ok().body("");

    }

}

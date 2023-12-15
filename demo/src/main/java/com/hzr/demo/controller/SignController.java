package com.hzr.demo.controller;import com.hzr.demo.common.BaseConfig;import com.hzr.demo.entity.FileEntity;import com.hzr.demo.service.FileService;import org.apache.commons.io.FileUtils;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.core.io.InputStreamResource;import org.springframework.http.HttpHeaders;import org.springframework.http.MediaType;import org.springframework.http.ResponseEntity;import org.springframework.stereotype.Controller;import org.springframework.ui.Model;import org.springframework.web.bind.annotation.*;import org.springframework.web.multipart.MultipartFile;import java.io.File;import java.io.FileInputStream;import java.io.IOException;import java.time.LocalDateTime;import java.util.List;/** * @Classname demoController * @Author: hzr * @Description TODO * @Version 1.0.0 * @Date 2023/12/6 10:56 * @Created by 22906 */@Controllerpublic class SignController {    @Autowired    private FileService fileService;    @Autowired    private BaseConfig baseConfig;    @GetMapping("/")    public String index() {        return "redirect:/view";    }    @RequestMapping("/view")    public String index(Model model) {        List<FileEntity> all = fileService.findAll();        model.addAttribute("data",all);//        model.addAttribute("data","SpringBoot 成功集成 Thymeleaf 模版！");        return "index";    }    @RequestMapping("/upload")    public String upload(@RequestPart("file") MultipartFile file, Model model) throws IOException {        FileEntity fileEntity = new FileEntity();        String fileName = file.getOriginalFilename();        File from = new File(baseConfig.getUploadPath() + fileName);        File to = new File(baseConfig.getFileSignPath() + fileName);        FileUtils.copyInputStreamToFile(file.getInputStream(), from);        fileEntity.setFileName(fileName.substring(0, fileName.lastIndexOf(".")));        fileEntity.setFilePath(from.getPath());        fileEntity.setFileSignPath(to.getPath());        fileEntity.setFileSize(file.getSize() / 1024 + "KB");        fileEntity.setCreateTime(LocalDateTime.now());        fileEntity.setFileType(fileName.substring(fileName.lastIndexOf(".")));        fileService.save(fileEntity);        List<FileEntity> all = fileService.findAll();        model.addAttribute("data",all);        return "redirect:/view";    }    @GetMapping(value = "/download/{id}")    public ResponseEntity<InputStreamResource> download(@PathVariable String id) throws IOException {        FileEntity signPath = fileService.findById(id);        File file = new File(signPath.getFilePath());        MediaType mediaType = MediaType.parseMediaType("application/pdf");        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));        return ResponseEntity.ok()                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())                .contentType(mediaType)                .contentLength(file.length())                .body(resource);    }    @GetMapping(value = "/delete/{id}")    public String delete(@PathVariable String id) throws IOException {        fileService.delByld(id);        return "redirect:/view";    }}
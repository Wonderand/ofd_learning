package com.hzr.demo.controller;import com.hzr.demo.common.CommonDto;import com.hzr.demo.common.FileSingUtils;import com.hzr.demo.entity.FileEntity;import com.hzr.demo.service.FileService;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;import org.springframework.ui.Model;import org.springframework.web.bind.annotation.*;import java.io.File;import java.io.IOException;import java.security.GeneralSecurityException;import java.security.SignatureException;/** * @Classname OfdviewController * @Author: hzr * @Description TODO * @Version 1.0.0 * @Date 2023/12/6 18:33 * @Created by 22906 */@Controllerpublic class OfdviewController {    @Autowired    private FileService fileService;    @Autowired    private FileSingUtils fileSingUtils;    @RequestMapping("/ofdview/{id}")    public String ofd(Model model, @PathVariable String id) {        FileEntity byId = fileService.findById(id);        model.addAttribute("file", byId);        return "ofdview";    }    @PostMapping(value = "/ofd/sign/")    public String sign(@RequestBody CommonDto dto, Model model) {        FileEntity byId = fileService.findById(dto.getId());        Integer page = dto.getPage();        Double pagex = dto.getPageX();        Double pagey = dto.getPageY();        try {            fileSingUtils.setFileSing(new File(byId.getFilePath()), new File(byId.getFileSignPath()),page,pagex,pagey);        } catch (GeneralSecurityException e) {            model.addAttribute("error", "签名已被保护，文档不允许追加签名");            e.printStackTrace();        } catch (IOException e) {            throw new RuntimeException(e);        }        byId.setFilePath(byId.getFileSignPath());        model.addAttribute("file", byId);        return "redirect:/ofdview"+"/"+dto.getId();    }}
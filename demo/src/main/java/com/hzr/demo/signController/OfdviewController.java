package com.hzr.demo.signController;import com.hzr.demo.common.FileSingUtils;import com.hzr.demo.entity.FileEntity;import com.hzr.demo.service.FileService;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;import org.springframework.ui.Model;import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.PathVariable;import org.springframework.web.bind.annotation.RequestMapping;import java.io.File;import java.io.IOException;import java.security.GeneralSecurityException;/** * @Classname viewController * @Author: hzr * @Description TODO * @Version 1.0.0 * @Date 2023/12/6 18:33 * @Created by 22906 */@Controllerpublic class OfdviewController {    @Autowired    private FileService fileService;    @RequestMapping("/ofdview/{id}")    public String ofd(Model model,@PathVariable String id) {        FileEntity byId = fileService.findById(id);        model.addAttribute("file",byId);        return "ofdview";    }    @GetMapping(value = "/ofd/sign/{id}")    public String sign(@PathVariable String id,Model model) throws IOException, GeneralSecurityException {        FileEntity byId = fileService.findById(id);        FileSingUtils.setFileSing(new File(byId.getFilePath()),new File(byId.getFileSignPath()));        byId.setFilePath(byId.getFileSignPath());        model.addAttribute("file",byId);        return "ofdview";    }}
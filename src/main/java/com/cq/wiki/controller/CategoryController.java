package com.cq.wiki.controller;

import com.cq.wiki.req.CategoryQueryReq;
import com.cq.wiki.req.CategorySaveReq;
import com.cq.wiki.resp.CategoryQueryResp;
import com.cq.wiki.resp.CommonResp;
import com.cq.wiki.resp.PageResp;
import com.cq.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq req) {
        CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>();
        PageResp<CategoryQueryResp> list = categoryService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq req) {
        CommonResp resp = new CommonResp<>();
        categoryService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr){
        CommonResp resp = new CommonResp<>();
        List<String> list = Arrays.asList(idsStr.split(","));
        categoryService.delete(list);
        return resp;
    }

    @GetMapping("/all")
    public CommonResp allBook() {
        CommonResp<List<CategoryQueryResp>> resp = new CommonResp<>();
        List<CategoryQueryResp> allBook = categoryService.allBook();
        resp.setContent(allBook);
        return resp;
    }
}

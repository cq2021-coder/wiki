package com.cq.wiki.controller;

import com.cq.wiki.req.EbookQueryReq;
import com.cq.wiki.req.EbookSaveReq;
import com.cq.wiki.resp.CommonResp;
import com.cq.wiki.resp.EbookQueryResp;
import com.cq.wiki.resp.PageResp;
import com.cq.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(@Valid EbookQueryReq req) {
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody EbookSaveReq req) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp = new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }

    @GetMapping("/all")
    public CommonResp allBook() {
        CommonResp<List<EbookQueryResp>> resp = new CommonResp<>();
        List<EbookQueryResp> allBook = ebookService.allBook();
        resp.setContent(allBook);
        return resp;
    }
}

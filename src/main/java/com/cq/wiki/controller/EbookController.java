package com.cq.wiki.controller;

import com.cq.wiki.req.EbookReq;
import com.cq.wiki.resp.CommonResp;
import com.cq.wiki.resp.EbookResp;
import com.cq.wiki.resp.PageResp;
import com.cq.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookReq req) {
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<>();
        PageResp<EbookResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }
    @GetMapping("/all")
    public CommonResp allBook() {
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<>();
        PageResp<EbookResp> allBook = ebookService.allBook();
        resp.setContent(allBook);
        return resp;
    }
}

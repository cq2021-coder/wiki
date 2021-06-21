package com.cq.wiki.controller;

import com.cq.wiki.req.UserQueryReq;
import com.cq.wiki.req.UserSaveReq;
import com.cq.wiki.resp.CommonResp;
import com.cq.wiki.resp.UserQueryResp;
import com.cq.wiki.resp.PageResp;
import com.cq.wiki.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public CommonResp list(@Valid UserQueryReq req) {
        CommonResp<PageResp<UserQueryResp>> resp = new CommonResp<>();
        PageResp<UserQueryResp> list = userService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody UserSaveReq req) {
        CommonResp resp = new CommonResp<>();
        userService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp = new CommonResp<>();
        userService.delete(id);
        return resp;
    }

    @GetMapping("/all")
    public CommonResp allBook() {
        CommonResp<List<UserQueryResp>> resp = new CommonResp<>();
        List<UserQueryResp> allBook = userService.allBook();
        resp.setContent(allBook);
        return resp;
    }
}

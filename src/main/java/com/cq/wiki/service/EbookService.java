package com.cq.wiki.service;

import com.cq.wiki.domain.Ebook;
import com.cq.wiki.domain.EbookExample;
import com.cq.wiki.mapper.EbookMapper;
import com.cq.wiki.req.EbookQueryReq;
import com.cq.wiki.req.EbookSaveReq;
import com.cq.wiki.resp.EbookQueryResp;
import com.cq.wiki.resp.PageResp;
import com.cq.wiki.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource
    private EbookMapper ebookMapper;

    public PageResp<EbookQueryResp> list(EbookQueryReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        /*List<EbookResp> respList = new ArrayList<>();
        for (Ebook ebook : ebookList) {
            EbookResp ebookResp = CopyUtil.copy(ebook,EbookResp.class);
            respList.add(ebookResp);
        }*/

        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(CopyUtil.copyList(ebookList, EbookQueryResp.class));

        return pageResp;
    }

    public PageResp<EbookQueryResp> allBook(){
        EbookExample ebookExample = new EbookExample();
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setList(CopyUtil.copyList(ebookList, EbookQueryResp.class));

        return pageResp;
    }

    /**
     * 保存
     */
    public void save(EbookSaveReq req){
        Ebook ebook = CopyUtil.copy(req,Ebook.class);
        if (ObjectUtils.isEmpty(req.getId())){
            //新增
            ebookMapper.insert(ebook);
        }
        else {
            //更新
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

}

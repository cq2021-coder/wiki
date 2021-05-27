package com.cq.wiki.service;

import com.cq.wiki.domain.Ebook;
import com.cq.wiki.domain.EbookExample;
import com.cq.wiki.mapper.EbookMapper;
import com.cq.wiki.req.EbookReq;
import com.cq.wiki.resp.EbookResp;
import com.cq.wiki.util.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        /*List<EbookResp> respList = new ArrayList<>();
        for (Ebook ebook : ebookList) {
            EbookResp ebookResp = CopyUtil.copy(ebook,EbookResp.class);
            respList.add(ebookResp);
        }*/
        List<EbookResp> respList = CopyUtil.copyList(ebookList, EbookResp.class);
        return respList;
    }
}

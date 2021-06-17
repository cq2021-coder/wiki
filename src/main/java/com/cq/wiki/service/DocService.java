package com.cq.wiki.service;

import com.cq.wiki.domain.Doc;
import com.cq.wiki.domain.DocExample;
import com.cq.wiki.mapper.DocMapper;
import com.cq.wiki.req.DocQueryReq;
import com.cq.wiki.req.DocSaveReq;
import com.cq.wiki.resp.DocQueryResp;
import com.cq.wiki.resp.PageResp;
import com.cq.wiki.util.CopyUtil;
import com.cq.wiki.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocService {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<DocQueryResp> list(DocQueryReq req) {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(CopyUtil.copyList(docList, DocQueryResp.class));

        return pageResp;
    }

    public List<DocQueryResp> allBook(){
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");

        return CopyUtil.copyList(docMapper.selectByExample(docExample),DocQueryResp.class);
    }

    /**
     * 保存
     */
    public void save(DocSaveReq req){
        Doc doc = CopyUtil.copy(req,Doc.class);
        if (ObjectUtils.isEmpty(req.getId())){
            //新增
            doc.setId(snowFlake.nextId()/10000);
            docMapper.insert(doc);
        }
        else {
            //更新
            docMapper.updateByPrimaryKey(doc);
        }
    }

    /**
     * 删除
     */
    public void delete(Long id){
        docMapper.deleteByPrimaryKey(id);
    }

}

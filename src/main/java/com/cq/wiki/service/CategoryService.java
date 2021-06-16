package com.cq.wiki.service;

import com.cq.wiki.domain.Category;
import com.cq.wiki.domain.CategoryExample;
import com.cq.wiki.mapper.CategoryMapper;
import com.cq.wiki.req.CategoryQueryReq;
import com.cq.wiki.req.CategorySaveReq;
import com.cq.wiki.resp.CategoryQueryResp;
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
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        /*List<CategoryResp> respList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryResp categoryResp = CopyUtil.copy(category,CategoryResp.class);
            respList.add(categoryResp);
        }*/

        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(CopyUtil.copyList(categoryList, CategoryQueryResp.class));

        return pageResp;
    }

    public PageResp<CategoryQueryResp> allBook(){
        CategoryExample categoryExample = new CategoryExample();
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setList(CopyUtil.copyList(categoryList, CategoryQueryResp.class));

        return pageResp;
    }

    /**
     * 保存
     */
    public void save(CategorySaveReq req){
        Category category = CopyUtil.copy(req,Category.class);
        if (ObjectUtils.isEmpty(req.getId())){
            //新增
            category.setId(snowFlake.nextId()/10000);
            categoryMapper.insert(category);
        }
        else {
            //更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    /**
     * 删除
     */
    public void delete(Long id){
        categoryMapper.deleteByPrimaryKey(id);
    }

}

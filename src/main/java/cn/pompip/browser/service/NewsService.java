package cn.pompip.browser.service;


import cn.pompip.browser.dao.NewDao;
import cn.pompip.browser.model.NewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewDao newDao;


    public NewBean getById(long id) {
        return newDao.getOne(id);
    }


    public List<NewBean> pageList(NewBean t, int pageNum, int pageSize) {
        return newDao.findAll(Example.of(t),PageRequest.of(pageNum-1,pageSize,Sort.Direction.DESC,"publishTime")).getContent();
//        return newDao.findAllByNewsType(t.getNewsType());
    }


    public int insertNewsBatch(List<NewBean> list) {
        for (NewBean newBean : list) {
            if ( !newDao.existsByUrlmd5(newBean.getUrlmd5())){
                newDao.save(newBean);
            }
        }

        return 1;
    }


    public int insertVideoBatch(List<NewBean> list) {
         newDao.saveAll(list);
        return 1;
    }

}

package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    //查询
    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    //新增
    public void save(Spit spit) {
        if (StringUtils.isEmpty(spit.get_id())) {
            long id = idWorker.nextId();
            spit.set_id("" + id);
        }
        //设置默认值
        spit.setThumbup(0);
        spit.setComment(0);
        spit.setVisits(0);
        spit.setShare(0);
        spit.setState("1");
        spit.setPublishtime(new Date());

        //如果有parentid则修改父吐槽的回复数量
        if (!StringUtils.isEmpty(spit.getParentid())){

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }

        spitDao.save(spit);
    }

    //修改
    public void updateById(Spit spit) {
        spitDao.save(spit);
    }

    //删除
    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    public Page<Spit> comment(String parentId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentId, pageRequest);
    }

    public void thumbup(String spitId){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }

    public void thumbupOld(String spitId) {
        //性能较低，需要先查询出结果再进行下一步操作
        spitDao.findById(spitId).ifPresent(spit -> {
                    spit.setThumbup(Optional.ofNullable(spit.getThumbup()).orElse(0) + 1);
                    spitDao.save(spit);
                }
        );
    }
}

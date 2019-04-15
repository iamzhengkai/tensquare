package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping
    public Result<List<Spit>> findAll(){
        List<Spit> spits = spitService.findAll();
        return new Result<>(true, StatusCode.OK,"查询成功",spits);
    }

    @GetMapping("/{spitId}")
    public Result<Spit> findById(@PathVariable String spitId){
        Spit spit = spitService.findById(spitId);
        return new Result<>(true, StatusCode.OK,"查询成功",spit);
    }

    @PostMapping
    public Result save(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true,StatusCode.OK,"保存成功");
    }

    @PutMapping("/{spitId}")
    public Result updateById(@PathVariable String spitId,@RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.updateById(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @DeleteMapping("/{spitId}")
    public Result deleteById(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    ///spit/comment/{parentid}/{page}/{size}
    //根据上级ID查询吐槽数据（分页）
    @GetMapping("/comment/{parentid}/{page}/{size}")
    public Result<PageResult<Spit>> comment(@PathVariable String parentid,
                                            @PathVariable Integer page,
                                            @PathVariable Integer size){
        Page<Spit> pageData =  spitService.comment(parentid,page,size);
        PageResult<Spit> pageResult = new PageResult<>(pageData.getTotalElements(),pageData.getContent());
        return new Result<>(true,StatusCode.OK,"查询成功",pageResult);
    }
    ///spit/thumbup/{spitId}
    @PutMapping("/thumbup/{spitId}")
    public Result thumbup(@PathVariable String spitId){
        String userId = "1018";

        if(null != redisTemplate.opsForValue().get(userId + ":" + spitId)){
            return new Result(false,StatusCode.REPERROR,"您已经点过赞，不能重复点赞");
        }
        redisTemplate.opsForValue().set(userId + ":" + spitId,"1");
        spitService.thumbup(spitId);
        return new Result(true,StatusCode.OK,"点赞成功");
    }
}

package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/label")
@CrossOrigin
public class LabelController {
    @Autowired
    private LabelService labelService;

    @GetMapping("/{id}")
    public Result<Label> findById(@PathVariable String id) {
        Label label = labelService.findById(id);
        return new Result<>(true, StatusCode.OK, "查询成功", label);
    }

    @GetMapping
    public Result<List<Label>> findAll() {
        List<Label> labels = labelService.findAll();
        return new Result<>(true, StatusCode.OK, "查询成功", labels);
    }

    @PostMapping
    public Result save(@RequestBody Label label) {
        labelService.save(label);
        return new Result(true, StatusCode.OK, "新增成功");
    }

    @PutMapping("/{id}")
    public Result updateById(@PathVariable String id, @RequestBody Label label) {
        label.setId(id);
        labelService.updateById(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable String id) {
        labelService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @PostMapping("/search")
    public Result<List<Label>> search(@RequestBody Label label) {
        List<Label> labels = labelService.findByWhere(label);
        return new Result<>(true, StatusCode.OK, "查询成功", labels);
    }

    @PostMapping("/search/{page}/{size}")
    public Result<PageResult<Label>> search(@RequestBody Label label,
                                                @PathVariable Integer page,
                                                @PathVariable Integer size) {
        Page<Label> pageData = labelService.findByWhere(label, page, size);
        PageResult<Label> pageResult = new PageResult<>(pageData.getTotalElements(), pageData.getContent());
        return new Result<>(true, StatusCode.OK, "查询成功", pageResult);
    }
}

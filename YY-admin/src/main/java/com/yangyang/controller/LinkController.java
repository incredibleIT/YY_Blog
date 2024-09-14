package com.yangyang.controller;


import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.Link;
import com.yangyang.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class LinkController {

    @Autowired
    private LinkService linkService;


    @GetMapping("/content/link/list")
    public ResponseResult getLinkListPage(Integer pageNum, Integer pageSize, String name, String status) {
        return linkService.getLinkListPage(pageNum, pageSize, name, status);
    }

    @PostMapping("/content/link")
    public ResponseResult addLink(@RequestBody Link link) {
        linkService.save(link);

        return ResponseResult.okResult();
    }

    @GetMapping("/content/link/{id}")
    public ResponseResult getLinkById(@PathVariable Long id) {

        Link link = linkService.getById(id);

        return ResponseResult.okResult(link);
    }


    @PutMapping("/content/link")
    public ResponseResult updateLink(@RequestBody Link link) {
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/content/link/{id}")
    public ResponseResult deleteLink(@PathVariable Long id) {
        linkService.removeById(id);
        return ResponseResult.okResult();
    }
}
package com.example.newcrownspringboot.controller;

import com.example.newcrownspringboot.entity.AcidTest;
import com.example.newcrownspringboot.service.AcidTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/acid")
public class AcidTestController {
    @Autowired
    AcidTestService acidTestService;

    @GetMapping()
    public List<AcidTest> findAll(){
        return acidTestService.list();
    }

}

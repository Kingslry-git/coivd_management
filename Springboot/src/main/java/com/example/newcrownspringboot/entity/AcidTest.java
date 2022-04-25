package com.example.newcrownspringboot.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "nucleicacidtest")
@Data
public class AcidTest {

    private String id;
    private String startTime;
    private String endTime;
    private String address;
}

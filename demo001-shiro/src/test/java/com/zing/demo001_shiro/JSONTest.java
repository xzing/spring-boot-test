package com.zing.demo001_shiro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * @author zing create at 2020/2/19 11:47 上午
 * @version 0.0.1
 */
public class JSONTest {
    @Test
    public void testJsonTrans() {
        String jstr = "{\"1\":[\"a\",\"b\",\"c\"],\"2\":[\"d\",\"e\",\"f\"]}";

        Map<String, List<String>> data = JSON.parseObject(jstr,
                new TypeReference<Map<String, List<String>>>() {
                }, Feature.OrderedField);

        System.out.println(data.keySet());
    }
}

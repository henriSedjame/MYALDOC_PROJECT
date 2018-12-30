package org.myaldoc.configuration.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

/**
 * @Project MYALDOC_PORJECT
 * @Author Henri Joel SEDJAME
 * @Date 29/12/2018
 * @Class purposes : .......
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ConfigurationServiceApplication.class})
@SpringBootTest
public class ConfigurationServiceApplicationTest {
    private static final String TEST_NAME = "test.name";
    private static final String TEST_VALUE = "test value";

    @Autowired
    RestTemplate template;

    @Test
    public void contextLoads() throws JSONException {
        try {
            String result = template.getForObject("http://localhost:8888/test/master", String.class);
            JSONObject json = new JSONObject(result);
            JSONArray propertySources = (JSONArray) json.get("propertySources");
            JSONObject source1 = propertySources.getJSONObject(0);
            JSONObject source = source1.getJSONObject("source");
            assertNotNull(source);
            Object value = source.get(TEST_NAME);
            assertNotNull(value);
            assertEquals(TEST_VALUE, value.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
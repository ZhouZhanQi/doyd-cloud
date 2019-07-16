package com.doyd.configserver;

import com.doyd.configserver.client.BootAdminApiClient;
import com.doyd.core.util.JacksonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DoydConfigServerApplicationTests {

    @Autowired
    private BootAdminApiClient bootAdminApiClient;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testApplications(){
        bootAdminApiClient.listApps();
    }
}

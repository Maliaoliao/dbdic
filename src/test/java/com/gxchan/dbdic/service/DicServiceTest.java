package com.gxchan.dbdic.service;

import org.junit.Test;

public class DicServiceTest {
    //private DicService service = new DicServiceImpl();
    private DicService service = new GroovyServiceImpl();

    @Test
    public void testGenerate() {
        service.generate();
    }

}

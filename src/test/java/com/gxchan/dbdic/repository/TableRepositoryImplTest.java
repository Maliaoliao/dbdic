package com.gxchan.dbdic.repository;

import static org.junit.Assert.*;

import org.junit.Test;

public class TableRepositoryImplTest {

    @Test
    public void testQuery() {
        TableRepositoryImpl rep = new TableRepositoryImpl();
        rep.query();
    }

}

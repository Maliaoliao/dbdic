package com.gxchan.dbdic.entity;

import java.util.List;

public class ExportFile {

    private String name;
    private List<String> lines;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

}

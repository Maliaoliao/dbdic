package com.gxchan.dbdic.service;

import com.gxchan.dbdic.entity.Column;
import com.gxchan.dbdic.entity.Config;
import com.gxchan.dbdic.entity.Index;
import com.gxchan.dbdic.entity.Table;
import com.gxchan.dbdic.repository.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroovyServiceImpl implements DicService {
    private TableRepository tableRepo;
    private ColumnRepository columnRepo;
    private IndexRepository indexRepo;
    private SequenceRepository sequenceRepo;
    private Config config;

    public GroovyServiceImpl() {
        tableRepo = new TableRepositoryImpl();
        columnRepo = new ColumnRepositoryImpl();
        indexRepo = new IndexRepositoryImpl();
        sequenceRepo = new SequenceRepositoryImpl();
        config = Config.getInstance();
    }

    public void generate() {
        List<String> sequences = sequenceRepo.query();
        List<Index> indexes = indexRepo.query();
        List<Table> tables = tableRepo.query();
        tables.parallelStream().forEach(t -> {
            List<Column> columns = columnRepo.query(t.getName());
            t.setColumns(columns);
            List<Index> indices = indexes.stream().filter(i -> i.getTableName().equals(t.getName())).collect(Collectors.toList());
            t.setIndexes(indices);
        });
        Template temp = getTemplate();
        File tmp = null;
        try {
            tmp = new File(config.getTargetDir(), "table-migration.groovy");
            FileWriter fw = new FileWriter(tmp);
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("tables", tables);
            root.put("sequences", sequences);
            temp.process(root, fw);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        } finally  {
            if(tmp != null && tmp.exists()) {
                //tmp.deleteOnExit();
            }
        }
    }

    protected Template getTemplate() {
        Template temp = null;
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_21);
            cfg.setDirectoryForTemplateLoading(new File(config.getTemplateDir()));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setClassicCompatible(true);
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            temp = cfg.getTemplate("table-migration.tpl");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return temp;
    }
}

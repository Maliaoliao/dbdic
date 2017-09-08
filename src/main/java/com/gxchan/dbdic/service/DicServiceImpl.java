package com.gxchan.dbdic.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import com.gxchan.dbdic.entity.Column;
import com.gxchan.dbdic.entity.Config;
import com.gxchan.dbdic.entity.Table;
import com.gxchan.dbdic.repository.ColumnRepository;
import com.gxchan.dbdic.repository.ColumnRepositoryImpl;
import com.gxchan.dbdic.repository.TableRepository;
import com.gxchan.dbdic.repository.TableRepositoryImpl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class DicServiceImpl implements DicService {
    private TableRepository tableRepo;
    private ColumnRepository columnRepo;
    private Config config;

    public DicServiceImpl() {
        tableRepo = new TableRepositoryImpl();
        columnRepo = new ColumnRepositoryImpl();
        config = Config.getInstance();
    }

    public void generate() {
        List<Table> tables = tableRepo.query();
        tables.parallelStream().forEach(t -> {
            List<Column> columns = columnRepo.query(t.getName());
            t.setColumns(columns);
        });
        Template temp = getTemplate();
        File tmp = null;
        try {
            tmp = new File(config.getTargetDir(), "temp.xml");
            FileWriter fw = new FileWriter(tmp);
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("tables", tables);
            temp.process(root, fw);
            // 将xml转换成docx
            File docx = new File(config.getTargetDir(), config.getOutputFile());
            WordprocessingMLPackage wmlPackage = Docx4J.load(tmp);
            Docx4J.save(wmlPackage, docx, Docx4J.FLAG_SAVE_ZIP_FILE);
        } catch (IOException | TemplateException | Docx4JException e) {
            e.printStackTrace();
        } finally  {
            if(tmp != null && tmp.exists()) {
                tmp.deleteOnExit();
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
            temp = cfg.getTemplate("template.xml");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return temp;
    }
}

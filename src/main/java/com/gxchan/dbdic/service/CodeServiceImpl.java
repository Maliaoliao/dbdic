package com.gxchan.dbdic.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import com.gxchan.dbdic.entity.Config;
import com.gxchan.dbdic.entity.ExportFile;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class CodeServiceImpl implements DicService {
    private Config config;

    public CodeServiceImpl() {
        config = Config.getInstance();
    }

    public void generate() {

        Template temp = getTemplate();
        File tmp = null;
        try {
            List<ExportFile> result = new ArrayList<>();
            File projectDir = new File("/Users/genson/development/workspace-idea-sohu/shbm-parent/shbm/src/main/java");
            Collection<File> list = FileUtils.listFiles(projectDir, new String[] { "java" }, true);
            int i = 0;
            Collections.shuffle((List<File>) list);
            for (File file : list) {
                ExportFile ef = new ExportFile();
                ef.setName(file.getName());
                ef.setLines(FileUtils.readLines(file, "UTF-8"));
                result.add(ef);
                if (i++ > 200) {
                    break;
                }
            }

            tmp = new File(config.getTargetDir(), "temp.xml");
            FileWriter fw = new FileWriter(tmp);
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("files", result);
            temp.process(root, fw);
            // 将xml转换成docx
            File docx = new File(config.getTargetDir(), config.getOutputFile());
            WordprocessingMLPackage wmlPackage = Docx4J.load(tmp);
            Docx4J.save(wmlPackage, docx, Docx4J.FLAG_SAVE_ZIP_FILE);
        } catch (IOException | TemplateException | Docx4JException e) {
            e.printStackTrace();
        } finally {
            if (tmp != null && tmp.exists()) {
                // tmp.deleteOnExit();
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
            temp = cfg.getTemplate("template_code.xml");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return temp;
    }
}

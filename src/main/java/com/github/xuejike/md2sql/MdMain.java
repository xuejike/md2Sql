package com.github.xuejike.md2sql;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrSpliter;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author xuejike
 * @date 2020/11/16
 */
public class MdMain {

    private static GroupTemplate gt;

    public static void main(String[] args) throws IOException {
        if (args.length==0){
            System.out.println("请输入Md文件");
            return;
        }
        initTemplate();
        String mdFile = args[0];

        String mdTable = FileUtil.readUtf8String(new File(mdFile));
//        System.out.println(mdTable);
        String[] lines = mdTable.replaceAll("\r","").split("\n");
        StringBuilder sb = new StringBuilder();
        String lastTable ="";
        String titleLast = "";
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith("#")){
                titleLast = lastTable;
                lastTable = line.replaceAll("#","").replaceAll(" ","");
                sb.append("-- ").append(line).append("\n");
            }
            List<String> resultFindAll = ReUtil.findAll("\\s*-+\\s*\\|\\s*-+\\s*", line, 0, new ArrayList<String>());

            if ( resultFindAll.size() >0){
                // 数据库表开始
                i = parseTable(lines, i+1, lastTable,titleLast, sb);
            }
        }

        FileUtil.writeString(sb.toString(),new File(mdFile+".sql"),"utf8");
    }

    public static int parseTable(String[] lines,Integer lineIndex,String tableLine,String titleTable,StringBuilder sb){
        System.out.println("生成Table = "+tableLine);
        int i = lineIndex;
        TableInfo tableInfo = new TableInfo();
        tableInfo.setName(tableLine);
        tableInfo.setComment(titleTable);
        tableInfo.setColumnList(new LinkedList<>());
        for (i = lineIndex; i < lines.length; i++) {

            String line = lines[i];
            if (line.startsWith("#")){
                sb.append("\n").append(genSql(tableInfo)).append("\n");
                return i;
            }
            List<String> list = StrSpliter.split(line, '|', true, false);
            while (list.size()>0 && StrUtil.isEmpty(list.get(0))){
                list.remove(0);
            }
            if (list.size() < 4){
                sb.append("\n").append(genSql(tableInfo)).append("\n");
                return i;
            }

            TableColumn tableColumn = new TableColumn();
            tableColumn.setName(list.get(0));
            tableColumn.setType(list.get(1));
            String extStr = list.get(2);
            List<String> extList = StrSpliter.split(extStr, ',', true, true);
            tableColumn.setExtMap(new HashMap<>());
            for (String ext : extList) {
                List<String> extItem = StrSpliter.split(ext, '@', true, true);
                if (extItem.size()>1){
                    tableColumn.getExtMap().put(extItem.get(0),extItem.get(1));
                }else{
                    tableColumn.getExtMap().put(extItem.get(0),"");
                }
            }
            if (tableColumn.getExtMap().containsKey("key")){
                tableInfo.setKey(tableColumn.getName());
            }
            tableColumn.setComment(list.get(3));
            if (StrUtil.isNotBlank(tableColumn.getName())){
                tableInfo.getColumnList().add(tableColumn);
            }



        }
        sb.append("\n").append(genSql(tableInfo)).append("\n");

        return i;
    }

    public static String genSql(TableInfo tableInfo){

        Template template = gt.getTemplate("sql.btl");
        template.binding("table",tableInfo);
        return template.render();
    }

    public static void initTemplate() throws IOException {
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        gt = new GroupTemplate(resourceLoader, cfg);
    }
}

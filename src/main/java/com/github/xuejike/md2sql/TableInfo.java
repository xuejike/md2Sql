package com.github.xuejike.md2sql;

import java.util.List;

/**
 * @author xuejike
 * @date 2020/11/16
 */
public class TableInfo {
    private String name;
    private List<TableColumn> columnList;
    private String key;
    private String comment;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TableColumn> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<TableColumn> columnList) {
        this.columnList = columnList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

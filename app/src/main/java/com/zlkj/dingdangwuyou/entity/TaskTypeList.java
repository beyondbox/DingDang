package com.zlkj.dingdangwuyou.entity;

import com.zlkj.dingdangwuyou.utils.Const;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务类型集合
 * Created by Botx on 2016/11/23.
 */

public class TaskTypeList {

    public static List<TaskType> list;
    static {
        list = new ArrayList<TaskType>();

        TaskType taskType1 = new TaskType();
        taskType1.setId(Const.TASK_TYPE_WORK);
        taskType1.setName("工作类");

        TaskType taskType2 = new TaskType();
        taskType2.setId(Const.TASK_TYPE_LIFE);
        taskType2.setName("生活类");

        TaskType taskType3 = new TaskType();
        taskType3.setId(Const.TASK_TYPE_STUDY);
        taskType3.setName("学习类");

        TaskType taskType4 = new TaskType();
        taskType4.setId(Const.TASK_TYPE_ALL);
        taskType4.setName("不限");

        list.add(taskType1);
        list.add(taskType2);
        list.add(taskType3);
        list.add(taskType4);
    }

    private TaskTypeList() {
    }

    public static class TaskType {
        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}

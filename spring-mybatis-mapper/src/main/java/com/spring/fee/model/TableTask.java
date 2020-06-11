package com.spring.fee.model;

import java.io.Serializable;
import java.util.Date;

public class TableTask implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_task.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_task.task_type
     *
     * @mbggenerated
     */
    private Integer taskType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_task.task_time
     *
     * @mbggenerated
     */
    private String taskTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_task.run_time
     *
     * @mbggenerated
     */
    private Date runTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table table_task
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_task.id
     *
     * @return the value of table_task.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_task.id
     *
     * @param id the value for table_task.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_task.task_type
     *
     * @return the value of table_task.task_type
     *
     * @mbggenerated
     */
    public Integer getTaskType() {
        return taskType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_task.task_type
     *
     * @param taskType the value for table_task.task_type
     *
     * @mbggenerated
     */
    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_task.task_time
     *
     * @return the value of table_task.task_time
     *
     * @mbggenerated
     */
    public String getTaskTime() {
        return taskTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_task.task_time
     *
     * @param taskTime the value for table_task.task_time
     *
     * @mbggenerated
     */
    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime == null ? null : taskTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_task.run_time
     *
     * @return the value of table_task.run_time
     *
     * @mbggenerated
     */
    public Date getRunTime() {
        return runTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_task.run_time
     *
     * @param runTime the value for table_task.run_time
     *
     * @mbggenerated
     */
    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }
}
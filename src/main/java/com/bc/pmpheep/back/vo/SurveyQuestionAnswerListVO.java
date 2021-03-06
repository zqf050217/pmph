package com.bc.pmpheep.back.vo;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：问题回答实体类
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-20
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Alias("SurveyQuestionAnswerListVO")
public class SurveyQuestionAnswerListVO implements java.io.Serializable {

    // Fields

    //
    private static final long serialVersionUID = 8608362154676560544L;
    /**
     * 主键
     */
    private Long              id;
    /**
     * 用户id
     */
    private Long              userId;
    /**
     * 问题id
     */
    private Long              questionId;
    /**
     * 选项id
     */
    private Long              optionId;
    /**
     * 选项内容
     */
    private String            optionContent;
    /**
     * 附件
     */
    private String            attachment;
    /**
     * 创建时间
     */
    private Timestamp         gmtCreate;

    private String            title;
    /**
     * 用户选择（单选，多选选项）内容
     */
    private String            noteOptionContent;
    /**
     * 单选，多选选项内容
     */
    private String            oneOrManyOptionContent;
    /**
     * 问卷ID
     */
    private Long              surveyId;

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getOptionId() {
        return this.optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public String getOptionContent() {
        return this.optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public String getAttachment() {
        return this.attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Timestamp getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the noteOptionContent
     */
    public String getNoteOptionContent() {
        return noteOptionContent;
    }

    /**
     * @param noteOptionContent the noteOptionContent to set
     */
    public void setNoteOptionContent(String noteOptionContent) {
        this.noteOptionContent = noteOptionContent;
    }

    /**
     * @return the oneOrManyOptionContent
     */
    public String getOneOrManyOptionContent() {
        return oneOrManyOptionContent;
    }

    /**
     * @param oneOrManyOptionContent the oneOrManyOptionContent to set
     */
    public void setOneOrManyOptionContent(String oneOrManyOptionContent) {
        this.oneOrManyOptionContent = oneOrManyOptionContent;
    }

    /**
     * @return the surveyId
     */
    public Long getSurveyId() {
        return surveyId;
    }

    /**
     * @param surveyId the surveyId to set
     */
    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

}
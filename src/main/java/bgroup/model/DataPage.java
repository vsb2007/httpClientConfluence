package bgroup.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class DataPage {
    static final Logger logger = LoggerFactory.getLogger(DataPage.class);

    private Integer id;
    private String pageId;
    private String pageHeader;
    private String pageChance;
    private String pageDamage;
    private String pageDescription;
    private Date pageDateCreate;

    public DataPage() {
    }

    public DataPage(String pageId, String pageHeader, String pageChance, String pageDamage, String pageDescription, Date pageDateCreate) {
        this.pageId = pageId;
        this.pageHeader = pageHeader;
        this.pageChance = pageChance;
        this.pageDamage = pageDamage;
        this.pageDescription = pageDescription;
        this.pageDateCreate = pageDateCreate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPageHeader() {
        return pageHeader;
    }

    public void setPageHeader(String pageHeader) {
        this.pageHeader = pageHeader;
    }

    public String getPageChance() {
        return pageChance;
    }

    public void setPageChance(String pageChance) {
        this.pageChance = pageChance;
    }

    public String getPageDamage() {
        return pageDamage;
    }

    public void setPageDamage(String pageDamage) {
        this.pageDamage = pageDamage;
    }

    public String getPageDescription() {
        return pageDescription;
    }

    public void setPageDescription(String pageDescription) {
        this.pageDescription = pageDescription;
    }

    public Date getPageDateCreate() {
        return pageDateCreate;
    }

    public void setPageDateCreate(Date pageDateCreate) {
        this.pageDateCreate = pageDateCreate;
    }

    @Override
    public String toString() {
        return "id:" + this.id
                + " page_id:" + this.pageId
                + " page_header:" + this.pageHeader
                + " page_chance:" + this.pageChance
                + " page_damage:" + this.pageDamage
                + " page_desc:" + this.pageDescription
                + " page_date_create:" + (this.pageDateCreate != null ? this.pageDateCreate.toString() : "null")
                ;
    }
}
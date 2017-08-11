package bgroup.app;

import bgroup.configuration.DbConfiguration;
import bgroup.mapper.DataPageMapper;
import bgroup.model.DataPage;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

import org.antlr.stringtemplate.language.*;

/**
 * Created by VSB on 09.08.2017.
 * httpClientConfluence
 */
public class MainApp {
    static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(DbConfiguration.class);
        ctx.refresh();

        DataPageMapper dataPageMapper = ctx.getBean(DataPageMapper.class);
        // DataPage dataPage = dataPageMapper.findPageById(1);

        //logger.info("dataPage: {}", dataPage);

        //DataPage dataPage1 = new DataPage("3", null, null, null, null, new Date());
        //dataPageMapper.insertDataPage(dataPage1);
        //logger.info("dataPage1: {}", dataPage1);

        List<DataPage> list = dataPageMapper.findAllPages();

        for (DataPage dataPage2 : list) {
            //logger.info("dataPage: {}", dataPage2);

            StringTemplateGroup group = new StringTemplateGroup("myGroup", "D:\\GitHub\\httpClientConfluence\\src\\main\\templates", DefaultTemplateLexer.class);
            StringTemplate template = group.getInstanceOf("pageTest");
            template.setAttribute("pageHeader", dataPage2.getPageHeader());
            template.setAttribute("pageId", dataPage2.getPageId());
            template.setAttribute("pageChance", dataPage2.getPageChance());
            template.setAttribute("pageDescription", dataPage2.getPageDescription());
            template.setAttribute("pageDamage", dataPage2.getPageDamage());
            template.setAttribute("pageDateCreate", dataPage2.getPageDateCreate());
            String page = template.toString();
            try {
                createPage(dataPage2.getPageHeader(), "TES", page);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //private static final String BASE_URL = "http://localhost:1990/confluence";
    private static final String BASE_URL = "http://ip-addresses:8090";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";
    private static final String ENCODING = "utf-8";

    public static String createContentRestUrl() throws UnsupportedEncodingException {
        return String.format("%s/rest/api/content/?&os_authType=basic&os_username=%s&os_password=%s", BASE_URL, URLEncoder.encode(USERNAME, ENCODING), URLEncoder.encode(PASSWORD, ENCODING));

    }

    public static void createPage(String title, String spaceKey, String value) throws Exception {
        JSONObject newPage = defineConfluencePage(title, spaceKey, value);
        createConfluencePageViaPost(newPage);
    }

    public static void createConfluencePageViaPost(JSONObject newPage) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();

        HttpEntity pageEntity = null;

        try {
            HttpPost postPageRequest = new HttpPost(createContentRestUrl());

            StringEntity entity = new StringEntity(newPage.toString(), ContentType.APPLICATION_JSON);
            postPageRequest.setEntity(entity);

            HttpResponse postPageResponse = client.execute(postPageRequest);
            pageEntity = postPageResponse.getEntity();

            logger.debug("Push Page Request returned: \n " + postPageResponse.getStatusLine().toString());
            Scanner s = new Scanner(pageEntity.getContent()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            logger.info(result);
        } finally {
            EntityUtils.consume(pageEntity);
        }
    }

    public static JSONObject defineConfluencePage(String title, String spaceKey, String value) throws JSONException {
        JSONObject newPage = new JSONObject();
        // "title":"My Awesome Page"
        // "type":"page",
        if (title == null) title = "new Title";
        newPage.put("title", title);
        newPage.put("type", "page");

        //"key": "TESTSPACEKEY"
        JSONObject key = new JSONObject();
        key.put("key", spaceKey);
        newPage.put("space", key);

        //"value": "<p>New page data.</p>",
        JSONObject valueJson = new JSONObject();
        valueJson.put("value", value);

        JSONObject representation = new JSONObject();
        representation.put("representation", "storage");

        JSONObject jsonObjects = new JSONObject();
        jsonObjects.put("value", value);
        jsonObjects.put("representation", "storage");

        JSONObject storage = new JSONObject();
        storage.put("storage", jsonObjects);

        newPage.put("body", storage);

        //logger.debug(newPage.toString());
        return newPage;
    }
}

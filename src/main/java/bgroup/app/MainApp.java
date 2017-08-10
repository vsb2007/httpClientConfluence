package bgroup.app;

import bgroup.configuration.DbConfiguration;
import bgroup.mapper.DataPageMapper;
import bgroup.model.DataPage;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.List;

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

        DataPage dataPage1 = new DataPage("3", null, null, null, null, new Date());
        dataPageMapper.insertDataPage(dataPage1);
        logger.info("dataPage1: {}", dataPage1);

        List<DataPage> list = dataPageMapper.findAllPages();
        for (DataPage dataPage2 : list) {
            logger.info("dataPage: {}", dataPage2);
        }

        HttpClient httpClient = HttpClientBuilder.create().build() ;

    }

}

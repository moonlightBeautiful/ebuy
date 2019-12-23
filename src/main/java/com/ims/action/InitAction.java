package com.ims.action;

import com.ims.entity.*;
import com.ims.service.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

@Component
public class InitAction implements ServletContextListener, ApplicationContextAware {

    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        // TODO Auto-generated method stub
        this.applicationContext = applicationContext;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext application = servletContextEvent.getServletContext();
        ProductBigTypeService productBigTypeService = (ProductBigTypeService) applicationContext.getBean("productBigTypeService");
        List<ProductBigType> bigTypeList = productBigTypeService.findAllBigTypeList();
        application.setAttribute("bigTypeList", bigTypeList);

        TagService tagService = (TagService) applicationContext.getBean("tagService");
        List<Tag> tagList = tagService.findTagList();
        application.setAttribute("tagList", tagList);

        NoticeService noticeService = (NoticeService) applicationContext.getBean("noticeService");
        List<Notice> noticeList = noticeService.findNoticeList(null, new PageBean(1, 7));
        application.setAttribute("noticeList", noticeList);

        NewsService newsService = (NewsService) applicationContext.getBean("newsService");
        List<News> newsList = newsService.findNewsList(null, new PageBean(1, 7));
        application.setAttribute("newsList", newsList);

        ProductService productService = (ProductService) applicationContext.getBean("productService");
        List<Product> specialPriceProductList = productService.findSpecialProductList(null, new PageBean(1, 8));
        application.setAttribute("specialPriceProductList", specialPriceProductList);
        List<Product> hotProductList = productService.findHotProductList(null, new PageBean(1, 6));
        application.setAttribute("hotProductList", hotProductList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

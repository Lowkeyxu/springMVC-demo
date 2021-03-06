/**
 * Copyright &copy; 2012-2014 Simpotech All rights reserved.
 */
package com.xuwc.simpo.common.aware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候取出ApplicaitonContext.
 * 
 */
@Component
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext rootApplicationContext = null;
    private static ApplicationContext springApplicationContext = null;

    private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getRootApplicationContext() {
        return rootApplicationContext;
    }

    public static ApplicationContext getSpringApplicationContext() {
        return springApplicationContext;
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public static void clearHolder() {
        if (logger.isDebugEnabled()) {
            logger.debug("清除SpringContextHolder中的ApplicationContext:" + rootApplicationContext);
            logger.debug("清除SpringContextHolder中的ApplicationContext:" + springApplicationContext);
        }
        rootApplicationContext = null;
        springApplicationContext = null;
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (applicationContext.getParent() == null) {
            SpringContextHolder.rootApplicationContext = applicationContext;
        } else {
            SpringContextHolder.springApplicationContext = applicationContext;
        }
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    public void destroy() throws Exception {
        SpringContextHolder.clearHolder();
    }
}

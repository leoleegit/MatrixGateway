package com.matrix.core.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeansUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringBeansUtil.applicationContext == null){
            SpringBeansUtil.applicationContext  = applicationContext;
        }
    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name){

        if(!getApplicationContext().containsBean(name)){
            return null;
        }

        return getApplicationContext().getBean(name);

    }
    public static <T> T getBean(Class<T> clazz){
        try {
            return getApplicationContext().getBean(clazz);
        } catch (BeansException e) {
            return null;
        }
    }
    public static <T> T getBean(String name, Class<T> clazz){
        if(!getApplicationContext().containsBean(name)){
            return null;
        }
        return getApplicationContext().getBean(name, clazz);
    }

}
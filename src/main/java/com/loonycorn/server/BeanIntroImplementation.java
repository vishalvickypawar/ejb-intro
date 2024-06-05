package com.loonycorn.server;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

@Stateless(name = "FirstBean")
public class BeanIntroImplementation implements BeanIntroInterface {

    @Resource
    private SessionContext context;

    @Override
    public String getMessage() {
        return "Welcome, kid, to the world of EJB!";
    }
}

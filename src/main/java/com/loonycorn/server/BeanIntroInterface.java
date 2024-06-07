package com.loonycorn.server;

import javax.ejb.Remote;

@Remote
public interface BeanIntroInterface {
    String getMessage();
    void setName(String name);
}
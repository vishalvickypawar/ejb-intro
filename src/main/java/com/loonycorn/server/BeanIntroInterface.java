package com.loonycorn.server;

import javax.ejb.Remote;

@Remote
public interface BeanIntroInterface {
    String getMessage();
}
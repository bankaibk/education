package com.hdu.msgservice.service;

import java.util.Map;

public interface MsgService {
    boolean send(Map<String, Object> param, String phone);
}

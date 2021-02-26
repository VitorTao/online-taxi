package com.online.taxi.driver.feign;

import com.online.taxi.common.dto.sms.SmsSendRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-sms")
public interface SmsFeignClient {
  @RequestMapping(value="/send/alisms-template", method = RequestMethod.POST)
  public void getsms(@RequestBody SmsSendRequest smsSendRequest);
}

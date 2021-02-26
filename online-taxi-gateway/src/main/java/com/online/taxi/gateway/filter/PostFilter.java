package com.online.taxi.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class PostFilter extends ZuulFilter {
  @Override
  public String filterType() {
    return FilterConstants.POST_TYPE;
  }

  @Override
  public int filterOrder() {
    return 2;
  }

  @Override
  public boolean shouldFilter() {
    RequestContext requestContext = RequestContext.getCurrentContext();
    return requestContext.sendZuulResponse();
  }

  @Override
  public Object run() throws ZuulException {
    RequestContext currentContext = RequestContext.getCurrentContext();
    String body = null;
    try {
      body = StreamUtils.copyToString(currentContext.getResponseDataStream(), StandardCharsets.UTF_8);
      System.out.println("返回数据："+ body);
    } catch (IOException e) {
      e.printStackTrace();
    }
    currentContext.setResponseBody(body);
    System.out.println("post :"+currentContext.sendZuulResponse());
    return null;
  }
}

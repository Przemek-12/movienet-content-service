package com.content.application.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "video-service")
public interface VideoServiceFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/video/exists")
    boolean videoExistsById(@RequestParam Long videoId);
}

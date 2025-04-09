/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.rest.latest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.infiniteautomation.mango.example.sqlTables.vo.ExampleSiteVO;
import com.infiniteautomation.mango.rest.latest.model.ExampleSiteModelMapping;
import com.infiniteautomation.mango.rest.latest.model.RestModelMapper;
import com.infiniteautomation.mango.rest.latest.websocket.DaoNotificationWebSocketHandler;
import com.infiniteautomation.mango.rest.latest.websocket.WebSocketMapping;
import com.infiniteautomation.mango.spring.events.DaoEvent;
import com.infiniteautomation.mango.spring.service.ExampleSiteService;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

@Component
@WebSocketMapping("/websocket/example-sites")
public class ExampleSiteWebsocketHandler extends DaoNotificationWebSocketHandler<ExampleSiteVO> {

    private final ExampleSiteService service;
    private final ExampleSiteModelMapping mapping;
    private final RestModelMapper mapper;

    @Autowired
    public ExampleSiteWebsocketHandler(MangoWebSocketHandlerConfig config, ExampleSiteService service, ExampleSiteModelMapping mapping, RestModelMapper mapper) {
        super(config);
        this.service = service;
        this.mapping = mapping;
        this.mapper = mapper;
    }

    @Override
    protected boolean hasPermission(PermissionHolder user, ExampleSiteVO vo) {
        return service.hasReadPermission(user, vo);
    }

    @Override
    protected Object createModel(ExampleSiteVO vo, ApplicationEvent event, PermissionHolder user) {
        return mapping.map(vo, user, mapper);
    }

    @Override
    @EventListener
    protected void handleDaoEvent(DaoEvent<? extends ExampleSiteVO> event) {
        this.notify(event);
    }
}

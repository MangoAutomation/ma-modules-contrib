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

import com.infiniteautomation.mango.example.sqlTables.vo.ExampleAssetVO;
import com.infiniteautomation.mango.rest.latest.model.ExampleAssetModelMapping;
import com.infiniteautomation.mango.rest.latest.model.RestModelMapper;
import com.infiniteautomation.mango.rest.latest.websocket.DaoNotificationWebSocketHandler;
import com.infiniteautomation.mango.rest.latest.websocket.WebSocketMapping;
import com.infiniteautomation.mango.spring.events.DaoEvent;
import com.infiniteautomation.mango.spring.service.ExampleAssetService;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

@Component
@WebSocketMapping("/websocket/example-assets")
public class ExampleAssetWebsocketHandler extends DaoNotificationWebSocketHandler<ExampleAssetVO> {

    private final ExampleAssetService service;
    private final ExampleAssetModelMapping mapping;
    private final RestModelMapper mapper;

    @Autowired
    public ExampleAssetWebsocketHandler(ExampleAssetService service, ExampleAssetModelMapping mapping, RestModelMapper mapper) {
        this.service = service;
        this.mapping = mapping;
        this.mapper = mapper;
    }

    @Override
    protected boolean hasPermission(PermissionHolder user, ExampleAssetVO vo) {
        return service.hasReadPermission(user, vo);
    }

    @Override
    protected Object createModel(ExampleAssetVO vo, ApplicationEvent event, PermissionHolder user) {
        return mapping.map(vo, user, mapper);
    }

    @Override
    @EventListener
    protected void handleDaoEvent(DaoEvent<? extends ExampleAssetVO> event) {
        this.notify(event);
    }
}

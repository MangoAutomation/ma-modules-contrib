/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.rest.latest;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jooq.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.infiniteautomation.mango.example.sqlTables.vo.ExampleSiteVO;
import com.infiniteautomation.mango.rest.latest.model.ExampleSiteModel;
import com.infiniteautomation.mango.rest.latest.model.ExampleSiteModelMapping;
import com.infiniteautomation.mango.rest.latest.model.RestModelMapper;
import com.infiniteautomation.mango.rest.latest.model.StreamedArrayWithTotal;
import com.infiniteautomation.mango.rest.latest.model.StreamedVORqlQueryWithTotal;
import com.infiniteautomation.mango.rest.latest.patch.PatchVORequestBody;
import com.infiniteautomation.mango.spring.service.ExampleSiteService;
import com.infiniteautomation.mango.util.RQLUtils;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.jazdw.rql.parser.ASTNode;

@Api(value="Example Sites")
@RestController()
@RequestMapping("/example-sites")
public class ExampleSiteRestController {

    private final ExampleSiteService service;
    private final ExampleSiteModelMapping mapping;
    private final RestModelMapper mapper;

    @Autowired
    public ExampleSiteRestController(ExampleSiteService service, ExampleSiteModelMapping mapping,
                                     RestModelMapper mapper,
                                     @Value("${testing.enabled:false}") boolean testMode) {
        this.service = service;
        this.mapping = mapping;
        this.mapper = mapper;
    }

    @ApiOperation(
            value = "Get by XID",
            notes = "Only items that user has read permission to are returned"
    )
    @RequestMapping(method = RequestMethod.GET, value = "/{xid}")
    public ExampleSiteModel get(
            @ApiParam(value = "Valid XID", required = true, allowMultiple = false)
            @PathVariable String xid,
            @AuthenticationPrincipal PermissionHolder user) {
        return mapping.map(service.get(xid), user, mapper);
    }

    @ApiOperation(
            value = "Query",
            notes = "Use RQL formatted query",
            response=ExampleSiteModel.class,
            responseContainer="List"
    )
    @RequestMapping(method = RequestMethod.GET)
    public StreamedArrayWithTotal queryRQL(
            HttpServletRequest request,
            @AuthenticationPrincipal PermissionHolder user) {

        ASTNode rql = RQLUtils.parseRQLtoAST(request.getQueryString());
        Map<String, Field<?>> fieldMap = new HashMap<>();
        return new StreamedVORqlQueryWithTotal<>(service, rql, null, fieldMap, null, item -> mapping.map(item, user, mapper));
    }

    @ApiOperation(value = "Create a new site")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ExampleSiteModel> create(
            @ApiParam(value = "Site model", required = true)
            @RequestBody(required=true) ExampleSiteModel model,

            @AuthenticationPrincipal PermissionHolder user,
            UriComponentsBuilder builder) {

        ExampleSiteVO vo = service.insert(mapping.unmap(model, user, mapper));

        URI location = builder.path("/example-sites/{xid}").buildAndExpand(vo.getXid()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(mapping.map(vo, user, mapper), headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update an existing item")
    @RequestMapping(method = RequestMethod.PUT, value = "/{xid}")
    public ResponseEntity<ExampleSiteModel> update(
            @PathVariable String xid,

            @ApiParam(value = "Updated equipment model", required = true)
            @RequestBody(required=true) ExampleSiteModel model,

            @AuthenticationPrincipal PermissionHolder user,
            UriComponentsBuilder builder) {

        ExampleSiteVO vo = service.update(xid, mapping.unmap(model, user, mapper));

        URI location = builder.path("/example-sites/{xid}").buildAndExpand(vo.getXid()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(mapping.map(vo, user, mapper), headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Partial update to an existing item")
    @RequestMapping(method = RequestMethod.PATCH, value = "/{xid}")
    public ResponseEntity<ExampleSiteModel> patch(
            @PathVariable String xid,

            @ApiParam(value = "Updated equipment model", required = true)
            @PatchVORequestBody(
                    service=ExampleSiteService.class,
                    modelClass=ExampleSiteModel.class)
                    ExampleSiteModel model,

            @AuthenticationPrincipal PermissionHolder user,
            UriComponentsBuilder builder) {

        ExampleSiteVO vo = service.update(xid, mapping.unmap(model, user, mapper));

        URI location = builder.path("/example-sites/{xid}").buildAndExpand(vo.getXid()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(mapping.map(vo, user, mapper), headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{xid}")
    public ExampleSiteModel delete(
            @ApiParam(value = "Valid XID", required = true, allowMultiple = false)
            @PathVariable String xid,
            @AuthenticationPrincipal PermissionHolder user) {;
        return mapping.map(service.delete(xid), user, mapper);
    }

}

/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.rest.latest;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.jooq.Field;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.infiniteautomation.mango.example.sqlTables.vo.ExampleAccessCardVO;
import com.infiniteautomation.mango.rest.latest.model.ExampleAccessCardModel;
import com.infiniteautomation.mango.rest.latest.model.ExampleAccessCardModelMapping;
import com.infiniteautomation.mango.rest.latest.model.ExampleAssetModel;
import com.infiniteautomation.mango.rest.latest.model.RestModelMapper;
import com.infiniteautomation.mango.rest.latest.model.StreamedArrayWithTotal;
import com.infiniteautomation.mango.rest.latest.model.StreamedVORqlQueryWithTotal;
import com.infiniteautomation.mango.rest.latest.patch.PatchVORequestBody;
import com.infiniteautomation.mango.spring.service.ExampleAccessCardService;
import com.infiniteautomation.mango.util.RQLUtils;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.jazdw.rql.parser.ASTNode;

@Api(value="Example Access Cards")
@RestController()
@RequestMapping("/example-access-cards")
public class ExampleAccessCardRestController {

    private final ExampleAccessCardService service;
    private final ExampleAccessCardModelMapping mapping;
    private final RestModelMapper mapper;

    @Autowired
    public ExampleAccessCardRestController(ExampleAccessCardService service, ExampleAccessCardModelMapping mapping, RestModelMapper mapper) {
        this.service = service;
        this.mapping = mapping;
        this.mapper = mapper;
    }

    @ApiOperation(
            value = "Get by XID",
            notes = "Only items that user has read permission to are returned"
    )
    @RequestMapping(method = RequestMethod.GET, value = "/{xid}")
    public ExampleAccessCardModel get(
            @ApiParam(value = "Valid XID", required = true, allowMultiple = false)
            @PathVariable String xid,
            @AuthenticationPrincipal PermissionHolder user) {
        return mapping.map(service.get(xid), user, mapper);
    }

    @ApiOperation(
            value = "Query",
            notes = "Use RQL formatted query",
            response=ExampleAccessCardModel.class,
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

    @ApiOperation(value = "Create a new item")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ExampleAccessCardModel> create(
            @ApiParam(value = "Model", required = true)
            @RequestBody(required=true) ExampleAssetModel model,

            @AuthenticationPrincipal PermissionHolder user,
            UriComponentsBuilder builder) {

        ExampleAccessCardVO vo = service.insert(mapping.unmap(model, user, mapper));

        URI location = builder.path("/example-access-cards/{xid}").buildAndExpand(vo.getXid()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(mapping.map(vo, user, mapper), headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update an existing item")
    @RequestMapping(method = RequestMethod.PUT, value = "/{xid}")
    public ResponseEntity<ExampleAccessCardModel> update(
            @PathVariable String xid,

            @ApiParam(value = "Updated asset model", required = true)
            @RequestBody(required=true) ExampleAccessCardModel model,

            @AuthenticationPrincipal PermissionHolder user,
            UriComponentsBuilder builder) {

        ExampleAccessCardVO vo = service.update(xid, mapping.unmap(model, user, mapper));

        URI location = builder.path("/example-access-cards/{xid}").buildAndExpand(vo.getXid()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(mapping.map(vo, user, mapper), headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Partial update to an existing item")
    @RequestMapping(method = RequestMethod.PATCH, value = "/{xid}")
    public ResponseEntity<ExampleAccessCardModel> patch(
            @PathVariable String xid,

            @ApiParam(value = "Updated model", required = true)
            @PatchVORequestBody(
                    service=ExampleAccessCardService.class,
                    modelClass=ExampleAccessCardModel.class)
                    ExampleAccessCardModel model,

            @AuthenticationPrincipal PermissionHolder user,
            UriComponentsBuilder builder) {

        ExampleAccessCardVO vo = service.update(xid, mapping.unmap(model, user, mapper));

        URI location = builder.path("/example-access-cards/{xid}").buildAndExpand(vo.getXid()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(mapping.map(vo, user, mapper), headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{xid}")
    public ExampleAccessCardModel delete(
            @ApiParam(value = "Valid XID", required = true, allowMultiple = false)
            @PathVariable String xid,
            @AuthenticationPrincipal PermissionHolder user) {;
        return mapping.map(service.delete(xid), user, mapper);
    }

    @ApiOperation(value = "Add a card to a site")
    @RequestMapping(method = RequestMethod.PUT, value = "/add-card-to-site/{siteXid}/{cardXid}")
    public ResponseEntity<Void> addCardToSite(
            @PathVariable String siteXid,
            @PathVariable String cardXid,
            @AuthenticationPrincipal PermissionHolder user,
            UriComponentsBuilder builder) {

        service.addAccessCardToSite(siteXid, cardXid);

        URI location = builder.path("/example-access-cards/site-cards/{siteXid}").buildAndExpand(siteXid).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Get all access cards for a site",
            notes = "Only items that user has read permission to are returned"
    )
    @RequestMapping(method = RequestMethod.GET, value = "/site-cards/{siteXid}")
    public List<ExampleAccessCardModel> getCardsForSite(
            @ApiParam(value = "Valid Site XID", required = true, allowMultiple = false)
            @PathVariable String siteXid,
            @AuthenticationPrincipal PermissionHolder user) {
        List<ExampleAccessCardVO> siteCards = service.getForSite(siteXid);
        return siteCards.stream().map(vo -> mapping.map(vo, user, mapper)).collect(Collectors.toList());
    }
}

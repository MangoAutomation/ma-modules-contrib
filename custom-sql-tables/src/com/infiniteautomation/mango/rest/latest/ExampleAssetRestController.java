/*
 * Copyright (C) 2021 Radix IoT LLC. All rights reserved.
 * @Author Terry Packer
 *
 */

package com.infiniteautomation.mango.rest.latest;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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

import com.infiniteautomation.mango.example.sqlTables.vo.ExampleAssetVO;
import com.infiniteautomation.mango.rest.latest.model.ExampleAssetModel;
import com.infiniteautomation.mango.rest.latest.model.ExampleAssetModelMapping;
import com.infiniteautomation.mango.rest.latest.model.ListWithTotal;
import com.infiniteautomation.mango.rest.latest.model.RestModelMapper;
import com.infiniteautomation.mango.rest.latest.model.StreamedArrayWithTotal;
import com.infiniteautomation.mango.rest.latest.model.StreamedVORqlQueryWithTotal;
import com.infiniteautomation.mango.rest.latest.patch.PatchVORequestBody;
import com.infiniteautomation.mango.spring.service.ExampleAssetService;
import com.infiniteautomation.mango.util.RQLUtils;
import com.serotonin.m2m2.vo.permission.PermissionHolder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import net.jazdw.rql.parser.ASTNode;

@Tag(name="Example Sites")
@RestController()
@RequestMapping("/example-assets")
public class ExampleAssetRestController {

    private final ExampleAssetService service;
    private final ExampleAssetModelMapping mapping;
    private final RestModelMapper mapper;

    @Autowired
    public ExampleAssetRestController(ExampleAssetService service, ExampleAssetModelMapping mapping, RestModelMapper mapper) {
        this.service = service;
        this.mapping = mapping;
        this.mapper = mapper;
    }

    /**
     * For Swagger documentation use only.
     */
    private interface ExampleAssetQueryResponse extends ListWithTotal<ExampleAssetModel> {
    }

    @Operation(
            summary = "Get by XID",
            description = "Only items that user has read permission to are returned"
    )
    @RequestMapping(method = RequestMethod.GET, value = "/{xid}")
    public ExampleAssetModel get(
            @Parameter(description = "Valid XID", required = true)
            @PathVariable String xid,
            @AuthenticationPrincipal PermissionHolder user) {
        return mapping.map(service.get(xid), user, mapper);
    }

    @Operation(
            summary = "Query",
            description = "Use RQL formatted query"
    )
    @RequestMapping(method = RequestMethod.GET)
    @ApiResponse(content = @Content(schema = @Schema(implementation = ExampleAssetQueryResponse.class)))
    public StreamedArrayWithTotal queryRQL(
            HttpServletRequest request,
            @AuthenticationPrincipal PermissionHolder user) {

        ASTNode rql = RQLUtils.parseRQLtoAST(request.getQueryString());
        Map<String, Field<?>> fieldMap = new HashMap<>();
        return new StreamedVORqlQueryWithTotal<>(service, rql, null, fieldMap, null, item -> mapping.map(item, user, mapper));
    }

    @Operation(summary = "Create a new asset")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ExampleAssetModel> create(
            @Parameter(description = "Asset model", required = true)
            @RequestBody(required=true) ExampleAssetModel model,

            @AuthenticationPrincipal PermissionHolder user,
            UriComponentsBuilder builder) {

        ExampleAssetVO vo = service.insert(mapping.unmap(model, user, mapper));

        URI location = builder.path("/example-assets/{xid}").buildAndExpand(vo.getXid()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(mapping.map(vo, user, mapper), headers, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing item")
    @RequestMapping(method = RequestMethod.PUT, value = "/{xid}")
    public ResponseEntity<ExampleAssetModel> update(
            @PathVariable String xid,

            @Parameter(description = "Updated asset model", required = true)
            @RequestBody(required=true) ExampleAssetModel model,

            @AuthenticationPrincipal PermissionHolder user,
            UriComponentsBuilder builder) {

        ExampleAssetVO vo = service.update(xid, mapping.unmap(model, user, mapper));

        URI location = builder.path("/example-assets/{xid}").buildAndExpand(vo.getXid()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(mapping.map(vo, user, mapper), headers, HttpStatus.OK);
    }

    @Operation(summary = "Partial update to an existing item")
    @RequestMapping(method = RequestMethod.PATCH, value = "/{xid}")
    public ResponseEntity<ExampleAssetModel> patch(
            @PathVariable String xid,

            @Parameter(description = "Updated model", required = true)
            @PatchVORequestBody(
                    service=ExampleAssetService.class,
                    modelClass=ExampleAssetModel.class)
                    ExampleAssetModel model,

            @AuthenticationPrincipal PermissionHolder user,
            UriComponentsBuilder builder) {

        ExampleAssetVO vo = service.update(xid, mapping.unmap(model, user, mapper));

        URI location = builder.path("/example-assets/{xid}").buildAndExpand(vo.getXid()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(mapping.map(vo, user, mapper), headers, HttpStatus.OK);
    }

    @Operation(summary = "Delete")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{xid}")
    public ExampleAssetModel delete(
            @Parameter(description = "Valid XID", required = true)
            @PathVariable String xid,
            @AuthenticationPrincipal PermissionHolder user) {;
        return mapping.map(service.delete(xid), user, mapper);
    }
}

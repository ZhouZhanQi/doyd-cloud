package com.doyd.coordinator.api;

import com.doyd.coordinator.entity.TccRequest;
import com.doyd.coordinator.service.ICoordinateService;
import com.doyd.dps.annotations.Delay;
import com.doyd.dps.annotations.RandomlyThrowsException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author zhouzq
 */
@Api(value = "CoordinateApi", tags = {"资源协调器"})
@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class CoordinateApi {

    private static final String COORDINATOR_URI_PREFIX = "/coordinator";

    @Autowired
    private ICoordinateService coordinateService;

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "确认预留资源", notes = "")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = COORDINATOR_URI_PREFIX + "/confirmation", method = RequestMethod.PUT)
    public void confirm(@Valid @RequestBody TccRequest request, BindingResult result) {
        coordinateService.confirm(request);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "撤销预留资源", notes = "")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = COORDINATOR_URI_PREFIX + "/cancellation", method = RequestMethod.PUT)
    public void cancel(@Valid @RequestBody TccRequest request, BindingResult result) {
        coordinateService.cancel(request);
    }

}

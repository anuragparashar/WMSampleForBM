/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker-com*/
package com.hrdb.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.hrdb.Vacation;
import com.hrdb.service.VacationService;

/**
 * Controller object for domain model class Vacation.
 * @see Vacation
 */
@RestController("hrdb.VacationController")
@Api(value = "VacationController", description = "Exposes APIs to work with Vacation resource.")
@RequestMapping("/hrdb/Vacation")
public class VacationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VacationController.class);

    @Autowired
    @Qualifier("hrdb.VacationService")
    private VacationService vacationService;

    @ApiOperation(value = "Creates a new Vacation instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Vacation createVacation(@RequestBody Vacation vacation) {
        LOGGER.debug("Create Vacation with information: {}", vacation);
        vacation = vacationService.create(vacation);
        LOGGER.debug("Created Vacation with information: {}", vacation);
        return vacation;
    }

    @ApiOperation(value = "Returns the Vacation instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Vacation getVacation(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Getting Vacation with id: {}", id);
        Vacation foundVacation = vacationService.getById(id);
        LOGGER.debug("Vacation details with id: {}", foundVacation);
        return foundVacation;
    }

    @ApiOperation(value = "Updates the Vacation instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Vacation editVacation(@PathVariable("id") Integer id, @RequestBody Vacation vacation) throws EntityNotFoundException {
        LOGGER.debug("Editing Vacation with id: {}", vacation.getId());
        vacation.setId(id);
        vacation = vacationService.update(vacation);
        LOGGER.debug("Vacation details with id: {}", vacation);
        return vacation;
    }

    @ApiOperation(value = "Deletes the Vacation instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteVacation(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Deleting Vacation with id: {}", id);
        Vacation deletedVacation = vacationService.delete(id);
        return deletedVacation != null;
    }

    /**
     * @deprecated Use {@link #findVacations(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of Vacation instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Vacation> searchVacationsByQueryFilters(Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Vacations list");
        return vacationService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the list of Vacation instances matching the search criteria.")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Vacation> findVacations(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Vacations list");
        return vacationService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportVacations(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        return vacationService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns the total count of Vacation instances.")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Long countVacations(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
        LOGGER.debug("counting Vacations");
        return vacationService.count(query);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service VacationService instance
	 */
    protected void setVacationService(VacationService service) {
        this.vacationService = service;
    }
}

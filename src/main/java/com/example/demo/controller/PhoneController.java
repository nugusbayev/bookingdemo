package com.example.demo.controller;

import com.example.demo.dto.PhoneDetailsDTO;
import com.example.demo.common.Api;
import com.example.demo.entity.Phone;
import com.example.demo.exception.InconsistentDataException;
import com.example.demo.exception.PhoneNotFoundException;
import com.example.demo.service.PhoneService;
import com.example.demo.service.impl.PhoneServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Api.V1+"/phone")
public class PhoneController {
    @Autowired
    private PhoneService phoneService;

    @GetMapping()
    @Operation(summary = "Gets list of phones, with optional availability filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned info on found phone" ),
            @ApiResponse(responseCode = "204", description = "No phones found",
                    content = @Content)})
    public ResponseEntity<List<Phone>> findAll(@RequestParam(required = false) Boolean available) {
        List<Phone> phones = phoneService.findAll(available);
        if (phones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(phones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets additional info about phone: FonoAPI integration and information about booking if phone is unavailable")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned info on found phone" ),
            @ApiResponse(responseCode = "404", description = "Phone not found by provided id",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Data consistency problem occured",
                    content = @Content) })
    public ResponseEntity<PhoneDetailsDTO> findOne(@PathVariable Long id) throws PhoneNotFoundException, InconsistentDataException {
        return new ResponseEntity<>(phoneService.findOne(id), HttpStatus.OK);
    }
}

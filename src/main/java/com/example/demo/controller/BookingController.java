package com.example.demo.controller;

import com.example.demo.aop.KeycloakAuth;
import com.example.demo.common.Api;
import com.example.demo.exception.BookingNotFoundException;
import com.example.demo.exception.PhoneAlreadyBookedException;
import com.example.demo.exception.PhoneAlreadyReturnedException;
import com.example.demo.exception.PhoneNotFoundException;
import com.example.demo.service.BookingService;
import com.example.demo.service.impl.BookingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Api.V1+"/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @KeycloakAuth
    @PostMapping("/book/{phoneId}")
    @Operation(summary = "Books phone by provided phone id and makes it unavailable for others")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booked successfully" ),
            @ApiResponse(responseCode = "404", description = "Phone not found by provided id",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Phone is unavailable",
                    content = @Content) })
    public ResponseEntity<Long> book(@Parameter(description = "phone id to be booked") @PathVariable Long phoneId) throws PhoneAlreadyBookedException, PhoneNotFoundException, InterruptedException {
        return new ResponseEntity<>(bookingService.book(phoneId), HttpStatus.OK);
    }
    @KeycloakAuth
    @PostMapping("/return/{id}")
    @Operation(summary = "Returns phone by provided booking id and makes it available for others")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned successfully" ),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to return someone else's phone",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Booking not found by provided id",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "User already returned phone",
                    content = @Content) })
    public ResponseEntity<HttpStatus> returnPhone(@Parameter(description = "booking id") @PathVariable Long id) throws BookingNotFoundException, PhoneAlreadyReturnedException, IllegalAccessException {
        bookingService.returnPhoneByBookingId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

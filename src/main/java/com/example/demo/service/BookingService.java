package com.example.demo.service;

import com.example.demo.exception.BookingNotFoundException;
import com.example.demo.exception.PhoneAlreadyBookedException;
import com.example.demo.exception.PhoneAlreadyReturnedException;
import com.example.demo.exception.PhoneNotFoundException;

public interface BookingService {
    Long book(Long phoneId) throws PhoneNotFoundException, PhoneAlreadyBookedException, InterruptedException;
    void returnPhoneByBookingId(Long id) throws BookingNotFoundException, PhoneAlreadyReturnedException, IllegalAccessException;
}

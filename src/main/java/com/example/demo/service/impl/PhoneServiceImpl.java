package com.example.demo.service.impl;

import com.example.demo.dto.PhoneDetailsDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Phone;
import com.example.demo.exception.InconsistentDataException;
import com.example.demo.exception.PhoneNotFoundException;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.PhoneRepository;
import com.example.demo.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class PhoneServiceImpl implements PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private FonoAPIService fonoAPIService;
    @Autowired
    private BookingRepository bookingRepository;
    public List<Phone> findAll(Boolean available) {
        if(Objects.isNull(available))
            return phoneRepository.findAll();
        else
            return phoneRepository.findByAvailable(available);
    }

    public PhoneDetailsDTO findOne(Long id) throws PhoneNotFoundException, InconsistentDataException {
        Phone phone = phoneRepository.findById(id).orElseThrow(PhoneNotFoundException::new);
        PhoneDetailsDTO.FonoAPIInfo fonoAPIInfo = fonoAPIService.retrieveInfo(phone);
        PhoneDetailsDTO.BookingInfo bookingInfo = null;
        if(!phone.isAvailable()){
            Booking booking = bookingRepository.findByPhone_IdAndAndReturnedAtIsNull(phone.getId()).orElseThrow(InconsistentDataException::new);
            bookingInfo = booking.toBookingInfo();
        }
        return new PhoneDetailsDTO(fonoAPIInfo, bookingInfo);
    }
}
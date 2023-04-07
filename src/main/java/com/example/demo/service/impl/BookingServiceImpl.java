package com.example.demo.service.impl;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Phone;
import com.example.demo.exception.BookingNotFoundException;
import com.example.demo.exception.PhoneAlreadyBookedException;
import com.example.demo.exception.PhoneAlreadyReturnedException;
import com.example.demo.exception.PhoneNotFoundException;
import com.example.demo.model.CurrentUser;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.PhoneRepository;
import com.example.demo.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {
    @Autowired
    private CurrentUser currentUser;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PhoneRepository phoneRepository;

    @Transactional  //we also can use isolation level SERIALIZABLE to avoid concurrent bookings, but hereby we used optimistic lock approach
    public Long book(Long phoneId) throws PhoneNotFoundException, PhoneAlreadyBookedException, InterruptedException {
        Phone phone = phoneRepository.findById(phoneId).orElseThrow(PhoneNotFoundException::new);
        if(!phone.isAvailable()){
            log.error(String.format("Phone with id: %1$d - already booked by others", phoneId));
            throw new PhoneAlreadyBookedException();
        }
        //Optimistic lock with 'available' field was used; Also this can be done by @Version annotation that could throw OptimisticLockException, but as we already have 'available' field, I decided to use it
        //By default POSTGRES uses READ_COMMITTED isolation level
        if(phoneRepository.updateAvailability(phoneId)==0){
            log.error(String.format("Phone with id: %1$d - already booked by others, during current transaction", phoneId));
            throw new PhoneAlreadyBookedException();
        }
        Booking booking = Booking.builder()
                .bookedAt(LocalDateTime.now())
                .bookedBy(currentUser.getId())
                .phone(phone)
                .build();
        bookingRepository.save(booking);
        log.info(String.format("Phone with id: %1$d - successfully booked by %2$s",phoneId, currentUser.getId()));
        return booking.getId();
    }
    @Transactional
    public void returnPhoneByBookingId(Long id) throws BookingNotFoundException, PhoneAlreadyReturnedException, IllegalAccessException {
        Booking booking = bookingRepository.findById(id).orElseThrow(BookingNotFoundException::new);
        if(booking.getReturnedAt()!=null){
            log.error(String.format("Phone was already returned"));
            throw new PhoneAlreadyReturnedException();
        }
        if(booking.getBookedBy()!=null && !booking.getBookedBy().equals(currentUser.getId())){
            log.error(String.format("Unauthorized access, used with id: %2$s tried to return other's phone", currentUser.getId()));
            throw new IllegalAccessException("error.not_authorized_to_return_someones_phone");
        }
        Phone phone = booking.getPhone();
        phone.setAvailable(true);
        phoneRepository.save(phone);
        booking.setReturnedAt(LocalDateTime.now());
        bookingRepository.save(booking);
        log.info(String.format("Phone with id: %1$d - successfully returned by %2$s",  phone.getId(), currentUser.getId()));
    }
}
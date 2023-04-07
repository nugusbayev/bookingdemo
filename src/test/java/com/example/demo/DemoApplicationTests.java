package com.example.demo;

import com.example.demo.entity.Phone;
import com.example.demo.exception.PhoneAlreadyBookedException;
import com.example.demo.exception.PhoneNotFoundException;
import com.example.demo.model.CurrentUser;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.PhoneRepository;
import com.example.demo.service.BookingService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @MockBean
    private CurrentUser currentUser;
    private Phone phone;
    private Long bookingId;

    @BeforeEach
    void fillDB(){
        phone = new Phone();
        phone.setAvailable(true);
        phone.setBrand("samsung");
        phone.setDevice("galaxy a8");
        phone.setSerialNumber("serial");
        phoneRepository.saveAndFlush(phone);
    }
    @AfterEach
    void emptyDB(){
        bookingRepository.deleteById(bookingId);
        phoneRepository.deleteById(phone.getId());
    }
    @Test
    void contextLoads() throws InterruptedException {
        assertEquals(true, phone.isAvailable());
        final ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i=0;i<3;i++) {
            executor.execute(() -> {
                try {
                    Mockito.when(currentUser.getId())
                            .thenReturn("myid");
                    bookingId = bookingService.book(phone.getId());
                } catch (PhoneNotFoundException  | PhoneAlreadyBookedException | InterruptedException e) {
                    e.getMessage();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        Long count = bookingRepository.countByReturnedAtIsNullAndPhone_Id(phone.getId());
        assertEquals(1, count);
    }
}

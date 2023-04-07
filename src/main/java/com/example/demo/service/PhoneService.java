package com.example.demo.service;

import com.example.demo.dto.PhoneDetailsDTO;
import com.example.demo.entity.Phone;
import com.example.demo.exception.*;
import org.springframework.stereotype.Component;

import java.util.List;

public interface PhoneService {
    List<Phone> findAll(Boolean available);
    PhoneDetailsDTO findOne(Long id) throws PhoneNotFoundException, InconsistentDataException;
}

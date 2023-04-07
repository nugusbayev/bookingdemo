package com.example.demo.service.impl;

import com.example.demo.dto.PhoneDetailsDTO;
import com.example.demo.entity.Phone;
import com.example.demo.model.DeviceEntity;
import com.example.demo.model.FonoAPIStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class FonoAPIService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${fono.api.base_url}")
    private String fonoAPIBaseUrl;
    @Value("${fono.api.token}")
    private String fonoAPIToken;
    private final static String FONO_API_GET_DEVICE = "/getdevice";
    private final static int position = 1;
    public PhoneDetailsDTO.FonoAPIInfo retrieveInfo(Phone phone){
        PhoneDetailsDTO.FonoAPIInfo fonoAPIInfo = new PhoneDetailsDTO.FonoAPIInfo(FonoAPIStatus.DOWN);
        try{
            String url = fonoAPIBaseUrl+FONO_API_GET_DEVICE;
            Map<String , Object> mapReq = new HashMap<>();
            mapReq.put("brand", phone.getBrand());
            mapReq.put("device", phone.getDevice());
            mapReq.put("position", position);
            mapReq.put("token", fonoAPIToken);
            //FonoAPI is not available since 2020, nowadays they have migrated to another service - https://techspecs.io/, but it provides different information with different structure and for other purposes
            //Just added this non-working mockup, to show how it could be done
            ResponseEntity<DeviceEntity> prodResponse = restTemplate.postForEntity(url, mapReq, DeviceEntity.class);
            switch (prodResponse.getStatusCode()){
                case OK:
                    DeviceEntity deviceEntity = prodResponse.getBody();
                    fonoAPIInfo.set_2GBands(deviceEntity.get_2GBands());
                    fonoAPIInfo.set_3GBands(deviceEntity.get_3GBands());
                    fonoAPIInfo.set_4GBands(deviceEntity.get_4GBands());
                    fonoAPIInfo.setTechnology(deviceEntity.getTechnology());
                    fonoAPIInfo.setStatus(FonoAPIStatus.UP);
                    break;
                default:
                    fonoAPIInfo.setStatus(FonoAPIStatus.DOWN);
                    break;
            }
        }
        catch (Exception e){
            return new PhoneDetailsDTO.FonoAPIInfo(FonoAPIStatus.DOWN);
        }
        return fonoAPIInfo;
    }
}

package com.mdghub.project.controller;

import com.mdghub.project.dto.AddressDTO;
import com.mdghub.project.model.Address;
import com.mdghub.project.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {
    @Autowired
    private AddressService addressService;


    @PostMapping("/address")
    public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDTO) {
        //System.out.println("hi");
        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<AddressDTO> addAddress(@PathVariable Long addressId) {
        AddressDTO savedAddressDTO = addressService.getAddressById(addressId);
        return ResponseEntity.ok(savedAddressDTO);
    }

    @GetMapping("/address")
    public ResponseEntity<List<AddressDTO>> addAddress() {
        List<AddressDTO> AddressDTO = addressService.getAddressByUser();
        return ResponseEntity.ok(AddressDTO);
    }

    @PutMapping("/address/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId,
                                                    @RequestBody AddressDTO addressDTO) {
        AddressDTO updatedAddressDTO = addressService.updateAddressById(addressId,addressDTO);
        return new ResponseEntity<>(updatedAddressDTO, HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> updateAddress(@PathVariable Long addressId){
        String status = addressService.deleteAddress(addressId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}

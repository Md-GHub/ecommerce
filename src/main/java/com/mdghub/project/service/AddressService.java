package com.mdghub.project.service;

import com.mdghub.project.dto.AddressDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO);

    AddressDTO getAddressById(Long addressId);

    List<AddressDTO> getAddressByUser();

    AddressDTO updateAddressById(Long addressId, AddressDTO addressDTO);

    String deleteAddress(Long addressId);
}

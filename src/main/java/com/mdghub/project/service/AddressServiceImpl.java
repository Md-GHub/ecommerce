package com.mdghub.project.service;

import com.mdghub.project.dto.AddressDTO;
import com.mdghub.project.exceptions.ResourceNotFound;
import com.mdghub.project.model.Address;
import com.mdghub.project.model.Users;
import com.mdghub.project.repository.AddressRepo;
import com.mdghub.project.repository.UserRepo;
import com.mdghub.project.utils.AuthUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AddressRepo addressRepo;
    @Autowired
    AuthUtil authUtil;
    @Autowired
    UserRepo userRepo;



    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {

        Address address = modelMapper.map(addressDTO, Address.class);
        Users user = authUtil.loggedInUser();
        address.setUser(user);
        List<Address> userAddress = user.getAddress();
        userAddress.add(address);
        user.setAddress(userAddress);


        Address saved = addressRepo.save(address);
        return modelMapper.map(saved, AddressDTO.class);
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepo.findById(addressId).get();
        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddressByUser() {
        Users user = authUtil.loggedInUser();
        List<Address> userAddress = user.getAddress();

        // Transform Address list to AddressDTO list
        return userAddress.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public AddressDTO updateAddressById(Long addressId, AddressDTO addressDTO) {
        Address address = addressRepo.findById(addressId).get();
        if(address==null){
            throw new ResourceNotFound("Address","Address Id", addressId);
        }
        address.setStreet(addressDTO.getStreet());
        address.setBuildingName(addressDTO.getBuildingName());
        address.setCity(addressDTO.getCity());
        address.setCountry(addressDTO.getCountry());
        address.setState(addressDTO.getState());
        address.setPincode(addressDTO.getPincode());
        address.setCity(addressDTO.getCity());
        Address saved = addressRepo.save(address);

        return modelMapper.map(saved, AddressDTO.class);
    }

    @Override
    public String deleteAddress(Long addressId) {
        Address addressFromDatabase = addressRepo.findById(addressId)
                .orElseThrow(() -> new ResourceNotFound("Address", "addressId", addressId));

        Users user = addressFromDatabase.getUser();
        user.getAddress().removeIf(address -> address.getAddressId().equals(addressId));
        userRepo.save(user);

        addressRepo.delete(addressFromDatabase);

        return "Address deleted successfully with addressId: " + addressId;
    }


}

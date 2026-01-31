package com.example.flashSaleEngine.dto;

import com.example.flashSaleEngine.model.Product;
import com.example.flashSaleEngine.model.User;

public class DtoMapper {
    public UserResponseDto userToUserResponse(User from){
        UserResponseDto userResponse = new UserResponseDto();

        if(from.getId() != null && !from.getId().isEmpty()) {
            userResponse.setId(from.getId());
        }

        if(from.getName() != null && !from.getName().isEmpty()) {
            userResponse.setName(from.getName());
        }

        if(from.getPhone() != null && !from.getPhone().isEmpty()) {
            userResponse.setPhone(from.getPhone());
        }

        if(from.getEmail() != null && !from.getEmail().isEmpty()){
            userResponse.setEmail(from.getEmail());
        }

        if(from.getAddress() != null && !from.getAddress().isEmpty()){
            userResponse.setAddress(from.getAddress());
        }

        return userResponse;
    }

    public ProductDto productToProductDto(Product product){
        ProductDto productDto = new ProductDto();

        if(product != null){
            if(product.getId() != null && !product.getId().isEmpty()){
                productDto.setId(product.getId());
            }
            if(product.getName() != null && !product.getName().isEmpty()){
                productDto.setName(product.getName());
            }
            if(product.getDescription() != null && !product.getDescription().isEmpty()){
                productDto.setDescription(product.getDescription());
            }

            productDto.setStock(product.getStock());
            productDto.setPrice(product.getPrice());
        }

        return productDto;
    }
}

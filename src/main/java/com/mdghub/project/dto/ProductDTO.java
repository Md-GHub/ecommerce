package com.mdghub.project.dto;

public class ProductDTO {
    private Long productId;
    private String productName;
    private String image;
    private Long quantity;
    private Double price;
    private Double discount;
    private Double specialPrice;

    public ProductDTO() {
    }

    public ProductDTO(Long productId, String productName, String image, Long quantity,
                      Double price, Double discount, Double specialPrice) {
        this.productId = productId;
        this.productName = productName;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.specialPrice = specialPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(Double specialPrice) {
        this.specialPrice = specialPrice;
    }

}

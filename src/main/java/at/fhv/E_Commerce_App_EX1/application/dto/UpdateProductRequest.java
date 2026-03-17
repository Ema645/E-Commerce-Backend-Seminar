package at.fhv.E_Commerce_App_EX1.application.dto;

import java.math.BigDecimal;

public class UpdateProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;

    public UpdateProductRequest() {}

    public UpdateProductRequest(String name, String description, BigDecimal price, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}

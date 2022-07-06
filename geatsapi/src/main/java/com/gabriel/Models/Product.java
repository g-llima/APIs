package com.gabriel.Models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.gabriel.Converters.ListToStringConverter;

import lombok.Data;

@Data
@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    private String product_name;
    private String description;

    @Column
    @Convert(converter = ListToStringConverter.class)
    private List<String> tags;

    private String calories;
    private boolean veg;
    private String img_url;
    private BigDecimal price;
    private int quantity;
}

package com.Project.Project.AppleCouponShop.beans;

import com.Project.Project.AppleCouponShop.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Data // getters and setters , hashCode , equal , toString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Coupon {

    public Coupon(int companyId,
                  int amount,
                  double price,
                  Category category,
                  String title,
                  String description,
                  byte[] image,
                  Date start_date,
                  Date end_date) {
        this.companyId = companyId;
        this.amount = amount;
        this.price = price;
        this.category = category;
        this.title = title;
        this.description = description;
        this.image = image;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    int couponId;
    int companyId;
    private int amount;
    private double price;

    @Enumerated(EnumType.STRING) // as number         // @Enumerated(EnumType.STRING) - string
    private Category category;

    private String title,description;

    @Lob
    private byte[] image;

    private Date start_date, end_date;



}

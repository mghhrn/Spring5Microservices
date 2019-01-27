package com.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class OrderDto {

    private Integer id;
    private String code;
    private Date created;
    List<OrderLineDto> orderLines;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto order = (OrderDto) o;
        return null == id ? code.equals(order.code) : id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return null == id ? Objects.hash(code) : Objects.hash(id);
    }

}

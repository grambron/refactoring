package ru.akirakozov.sd.refactoring.entity;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class Product {

    private String name;

    private long price;

}

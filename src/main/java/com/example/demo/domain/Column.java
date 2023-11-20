package com.example.demo.domain;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Column {
    private List<Card> cards;
}

package com.project.planb.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryType {

    FOOD("식비"),
    TRANSPORT("교통비"),
    SNACKS("간식"),
    HOUSING("주거비"),
    EDUCATION("교육/학습"),
    SHOPPING("쇼핑"),
    HEALTH("의료/건강"),
    HOBBY("취미/여가"),
    UTILITIES("공과금"),
    OTHER("기타");

    final private String categoryName;

}

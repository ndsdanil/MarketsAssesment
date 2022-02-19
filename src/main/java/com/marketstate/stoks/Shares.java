package com.marketstate.stoks;

import java.util.ArrayList;
import java.util.List;

//Класс который хранит информацию полученную скраппингом
public class Shares {
    String exchangeName;
    String stockName;
    float stockPrice$Value;
    float stockPriceRUBValue;
    List<Float> listForMath$ = new ArrayList<Float>();
    List<Float> listForMathRUB = new ArrayList<Float>();
}

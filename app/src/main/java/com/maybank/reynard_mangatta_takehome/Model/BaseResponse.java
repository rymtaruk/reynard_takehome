package com.maybank.reynard_mangatta_takehome.Model;


import java.io.Serializable;
import java.util.List;

/**
 * Created By reynard on 21/12/20.
 */
public class BaseResponse implements Serializable {
    String message;
    List<Items> items;

    public String getMessage() {
        return message;
    }

    public List<Items> getItems() {
        return items;
    }
}

package com.app.exception;

import lombok.NoArgsConstructor;

/**
 * @author Saurabh Vaish
 * @Date 06-10-2021
 */
@NoArgsConstructor
public class PlaceNotFoundException extends RuntimeException{

    public PlaceNotFoundException(String msg){
        super(msg);
    }
}

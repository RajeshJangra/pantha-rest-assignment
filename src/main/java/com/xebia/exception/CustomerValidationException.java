/*
 * Copyright 2015 by Xebia IT Architects,
 *  612, 6th Floor, BPTP Park Centra, Sector 30, Gurgaon - 122001, Haryana, India
 *  All rights reserved.
 *
 *  This software is confidential and proprietary information of Xebia IT Architects ("Confidential Information").
 *  You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement
 *  you entered into with Xebia IT Architects.
 */

package com.xebia.exception;

/**
 * Created by rajeshkumar on 19/07/15.
 */
public class CustomerValidationException extends Exception {

    public CustomerValidationException() {
    }

    public CustomerValidationException(String message) {
        super(message);
    }

    public CustomerValidationException(Throwable cause) {
        super(cause);
    }

    public CustomerValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}

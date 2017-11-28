/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.controller;

/**
 *
 * @author Sabryna
 */
public class ErrorMessage extends Exception {

    public ErrorMessage(String message) {
        super(message);
    }

    public ErrorMessage(String message, Throwable cause) {
        super(message, cause);
    }

}

package com.erickrim.contacts.exceptions;

public class ContactNotFoundException extends Exception {

    public ContactNotFoundException() {
    }

    public ContactNotFoundException(String message) {
        super(message);
    }
}

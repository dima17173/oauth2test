package com.impltech.testoauth.exception;

/**
 * Created by dima.
 * Creation date 15.02.19.
 */
public class LowBalanceException extends RuntimeException {
        public LowBalanceException(String message) {
            super(message);
        }
}

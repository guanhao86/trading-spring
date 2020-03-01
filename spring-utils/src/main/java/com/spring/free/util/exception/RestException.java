package com.spring.free.util.exception;

public class RestException extends RuntimeException {

        private static final long serialVersionUID = -7204994814119867849L;

        private final String code;

        /**
         * @param code
         * @param msg
         */
        public RestException(String code, String msg) {
            super(msg);
            this.code = code;
        }

        /**
         * @return the code
         */
        public String getCode() {
            return code;
        }

    }

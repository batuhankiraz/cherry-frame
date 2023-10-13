package com.cherryframe.cherryframe.storefront.validator;

public interface CPMSValidator<T> {
    boolean validate(final T object);
}

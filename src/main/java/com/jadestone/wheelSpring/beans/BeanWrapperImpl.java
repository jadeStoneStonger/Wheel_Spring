package com.jadestone.wheelSpring.beans;

public class BeanWrapperImpl implements BeanWrapper {

    private Object source;

    public BeanWrapperImpl(Object source){
        this.source = source;
    }

    public Object getPropertyValue(String propertyName) {
        return null;
    }

    /**
     *
     * @param propertyName
     * @param value
     */
    public void setPropertyValue(String propertyName, String value) {

    }
}

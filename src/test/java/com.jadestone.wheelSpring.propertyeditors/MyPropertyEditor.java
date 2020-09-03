package com.jadestone.wheelSpring.propertyeditors;

import org.junit.Test;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class MyPropertyEditor extends PropertyEditorSupport {
    public static class My{
        private Integer age;
        private String name;

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "name = " + name + " age = " + age;
        }
    }

    //aa:11
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] datas = text.split(":");
        My my = new My();
        my.setAge(Integer.valueOf(datas[1]));
        my.setName(datas[0]);
        setValue(my);
    }

    @Test
    public void testCase1(){
        MyPropertyEditor myPropertyEditor = new MyPropertyEditor();
        myPropertyEditor.setAsText("aa:1");
        System.out.println(myPropertyEditor.getAsText());
    }

    @Test
    public void testCase2(){
        LocalDateTime time=LocalDateTime.of(LocalDateTime.now().minus(30, ChronoUnit.DAYS).toLocalDate(), LocalTime.MIN);
        System.out.println(time);

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String strDate2 = dtf2.format(time);

        System.out.println(strDate2);

    }

}

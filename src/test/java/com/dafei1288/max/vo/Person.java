package com.dafei1288.max.vo;

import java.util.Date;
import java.util.Objects;

public class Person implements Comparable<Person> {
    private String firstName;
    private String lastName;
    private Integer age;
    private Date brithday;
    private Double salary;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBrithday() {
        return brithday;
    }

    public void setBrithday(Date brithday) {
        this.brithday = brithday;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Person(){}

    public Person(String firstName, String lastName, Integer age, Date brithday, Double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.brithday = brithday;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", brithday=" + brithday +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(age, person.age) &&
                Objects.equals(brithday, person.brithday) &&
                Objects.equals(salary, person.salary);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstName, lastName, age, brithday, salary);
    }

    @Override
    public int compareTo(Person o) {
        return this.getAge().compareTo(o.getAge());
    }
}

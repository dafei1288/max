package com.dafei1288.max;

import com.dafei1288.max.comparator.SurnameComparator;
import com.dafei1288.max.vo.Person;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

public class TestFilterByVo {

    public static Collection<Person> persons = new ArrayList<>();
    public static Collection<Person> distinctpersons = new ArrayList<>();

    @BeforeClass
    public static void initPersons(){
        persons.add(new Person("张","三",15,new Date(),3000d));
        persons.add(new Person("张","四",17,new Date(),7800d));
        persons.add(new Person("张","五",22,new Date(),13000d));
        persons.add(new Person("张","六",19,new Date(),85000d));
        persons.add(new Person("张","七",25,new Date(),1100d));
        persons.add(new Person("张","八",15,new Date(),3000d));

        persons.add(new Person("李","三",15,new Date(),3000d));
        persons.add(new Person("李","四",17,new Date(),7800d));
        persons.add(new Person("李","五",22,new Date(),13000d));
        persons.add(new Person("李","六",19,new Date(),85000d));
        persons.add(new Person("李","七",25,new Date(),1100d));
        persons.add(new Person("李","八",99,new Date(),3000d));


        persons.add(new Person("王","三",15,new Date(),3000d));
        persons.add(new Person("王","四",17,new Date(),7800d));
        persons.add(new Person("王","五",22,new Date(),13000d));
        persons.add(new Person("王","六",19,new Date(),85000d));
        persons.add(new Person("王","七",25,new Date(),1100d));
        persons.add(new Person("王","八",15,new Date(),3000d));

        distinctpersons.add(new Person("王","三",15,new Date(),3000d));
        distinctpersons.add(new Person("王","三",15,new Date(),3000d));
        distinctpersons.add(new Person("王","三",15,new Date(),3000d));
        distinctpersons.add(new Person("王","四",17,new Date(),7800d));
        distinctpersons.add(new Person("王","五",22,new Date(),13000d));
        distinctpersons.add(new Person("王","六",19,new Date(),85000d));
        distinctpersons.add(new Person("王","六",19,new Date(),85000d));
        distinctpersons.add(new Person("王","六",19,new Date(),85000d));
        distinctpersons.add(new Person("王","七",25,new Date(),1100d));
        distinctpersons.add(new Person("王","八",15,new Date(),3000d));
    }

    @Test
    public void testAll() {

        System.out.println(persons);
        Collection<Person> p1 = CollectOperator.everyFixTo(persons,x->x.getAge()>20);
        System.out.println(p1);

        System.out.println();

        Collection<Person> p2 =CollectOperator.distinct(distinctpersons);
        System.out.println(distinctpersons.size());
        System.out.println(p2.size());
//        p2.stream().forEach(System.out::println);
        CollectOperator.forEach(p2,System.out::println);

        System.out.println();

        Person rr = CollectOperator.max(persons, Comparator.comparing(Person::getAge));
        //persons.stream().max((u1,u2)->{u1.getAge()>u2.getAge();}).get();
        System.out.println(rr);

        Person rr1 = CollectOperator.max(persons, Person::getSalary);
        //persons.stream().max((u1,u2)->{u1.getAge()>u2.getAge();}).get();
        System.out.println(rr1);

        System.out.println();

        rr = CollectOperator.min(persons, Comparator.comparing(Person::getAge));
        //persons.stream().max((u1,u2)->{u1.getAge()>u2.getAge();}).get();
        System.out.println(rr);

        rr1 = CollectOperator.min(persons, Person::getSalary);
        //persons.stream().max((u1,u2)->{u1.getAge()>u2.getAge();}).get();
        System.out.println(rr1);


        System.out.println();

        rr = CollectOperator.min(persons);
        //persons.stream().max((u1,u2)->{u1.getAge()>u2.getAge();}).get();
        System.out.println(rr);

        rr1 = CollectOperator.max(persons);
        //persons.stream().max((u1,u2)->{u1.getAge()>u2.getAge();}).get();
        System.out.println(rr1);


        rr = CollectOperator.min(persons, Comparator.comparing(Person::getAge));
        //persons.stream().max((u1,u2)->{u1.getAge()>u2.getAge();}).get();
        System.out.println(rr);

        //CustomStringComparator

//        rr = CollectOperator.max(persons, SurnameComparator.comparing(Person::getFirstName));
        //persons.stream().max((u1,u2)->{u1.getAge()>u2.getAge();}).get();
//        System.out.println(rr);

//        System.out.println();
//        Collection<Person> p3 = CollectOperator.sorted(persons,SurnameComparator.comparing(Person::getFirstName));
//        CollectOperator.forEach(p3,System.out::println);
    }
}

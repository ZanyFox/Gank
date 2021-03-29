package com.zf.gank;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TestOne {

    class Creature{
        void life(){

        }
    }

    class Person extends Creature{
        void say(){}
    }
    class Student extends Person{
        void study(){}
    }
    class Worker extends Person {
        void work(){

        }
    }

    class Hello<T extends Person>{
        Hello(T t) {

        }
    }

    @Test

    public void testOne() {

        // 协变  子类的泛型也属于泛型类型的子类  Java 不具备

        List<Student> studentList = new ArrayList<>();
        List<Person> people = null;

//        报错
//        people = studentList;

        List<Creature> creatures = new ArrayList<>();
        // 可以放 不能取
        List<? super Person> people1 = creatures;
        people1.add(new Student());

//        报错
//        people.addAll(creatures);

        people.addAll(studentList);
    }
}

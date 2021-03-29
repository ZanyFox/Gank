package com.zf.gank

import com.zf.gank.TestOne.Student
import com.zf.gank.util.SingletonHolder
import org.junit.Test
import com.zf.gank.TestOne.Person

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val list1 = arrayListOf<Person>()
        val list2 = arrayListOf<Student>()

        val list3: List<Person> = list2
//        list1 = list2
        list1.addAll(list2)

        val list5 = ArrayList<Student>()
        var list4: MutableList<out Person> = list5

    }

    open class Animal {
        fun life() {
        }
    }

    class Dog : Pet()
    open class Pet : Animal()

    //  out 用来输出  in 用来输入
    sealed class Hi<out T> {
        data class Success(val msg: String) : Hi<String>()
        data class Faulure(val msg: String) : Hi<Nothing>()
    }

    fun hiTest(hi: Hi<String>) {
        when (hi) {
            is Hi.Faulure -> {
            }
            is Hi.Success -> {
            }
        }
    }

    @Test
    fun singleTest() {
        Manager.getInstance("Hello").say()
        Manager.getInstance("你好").say()
    }
}


class Manager private constructor(private val str: String) {

    fun say() {
        println(str)
    }

    companion object : SingletonHolder<Manager, String>(::Manager)

}
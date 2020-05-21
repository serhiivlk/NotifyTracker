package com.fuun.notihub

import org.junit.Test

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @Test
    fun addition_isCorrect() {
        val millis = 1589959143771
        val systemDefault = ZoneId.systemDefault()
        println(systemDefault)
        val atZone = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault())
        val atZone2 = Instant.ofEpochMilli(millis).atZone(ZoneId.of("Europe/Dublin"))
//        ZoneId.getAvailableZoneIds().forEach {
//            println(it)
//        }

        println(atZone.format(formatter))
        println(atZone2.format(formatter))
    }
}

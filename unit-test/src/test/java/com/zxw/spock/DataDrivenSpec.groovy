package com.zxw.spock

import spock.lang.Specification
import spock.lang.Unroll;

class DataDrivenSpec extends Specification {
    def "maximum of two numbers"() {
        expect:
        Math.max(a, b) == c

        where:
        a << [3, 5, 9]
        b << [7, 4, 9]
        c << [7, 5, 9]
    }

    def "minimum of #a and #b is #c"() {
        expect:
        Math.min(a, b) == c

        where:
        a | b || c
        3 | 7 || 3
        5 | 4 || 4
        9 | 9 || 9
    }

    def "#person.name is a #sex.toLowerCase() person"() {
        expect:
        person.getSex() == sex

        where:
        person                    || sex
        new Person(name: "Fred")  || "Male"
        new Person(name: "Wilma") || "Female"
    }

    static class Person {
        String name
        String getSex() {
            name == "Fred" ? "Male" : "Female"
        }
    }

    @Unroll
    def "身份证号:#idNo 的生日,性别,年龄是:#result"() {
        expect: "when + then 组合"
        IDNumberUtils.getBirAgeSex(idNo) == result

        where: "表格方式测试不同的分支逻辑"
        idNo                 || result
        "310168199809187333" || ["birthday": "1998-09-18", "sex": "男", "age": "22"]
        "320168200212084268" || ["birthday": "2002-12-08", "sex": "女", "age": "18"]
        "330168199301214267" || ["birthday": "1993-01-21", "sex": "女", "age": "27"]
        "411281870628201"    || ["birthday": "1987-06-28", "sex": "男", "age": "33"]
        "427281730307862"    || ["birthday": "1973-03-07", "sex": "女", "age": "47"]
        "479281691111377"    || ["birthday": "1969-11-11", "sex": "男", "age": "51"]
    }
}
class SampleClass {
    String name
    def SampleClass(name) {
        this.name = name
    }

    def showName() {
        println this.name
    }

    // if文サンプル
    def doIfSample(value) {
        if (value == 1.0) {
            println "value is 1.0"
        } else if (value == 1) {
            println "value is 1"
        } else {
            println "value is ???"
        }
    }

    def doListCollectSample() {
        def list1 = [1, 2, 3]
        def list2 = list1.collect {elem ->
            // list自体が渡される。各要素ではない。そこがeachとの違い。
            // 変数はitがデフォルト値
            println "ELEM:" + elem 
            elem + 1
        }
        if ([2, 3, 4] ==  list2) {
            println 'collect result:' + list2
        }
    }

    def doListInjectSample() {
        def list = [1, 2, 3, 4, 5]
        println "SUM:" + list.inject {a ,b -> 
            println "========="
            println "a:" + a
            println "b:" + b
            a + b
        }
    }

    def doListFindSample() {
        def list = [1, 2, 3, 4, 5]
        // 最初に見つかった要素を取得する
        def evenVal = list.find {elem ->
            // 偶数
            println "ELEM:" + elem
            elem % 2 == 0
        }
        println evenVal
        println "========="
        def evenAllVal = list.findAll {elem ->
            println "ELEM:" + elem
            elem % 2 == 0
        }
        println evenAllVal
    }

    def doClosureSample() {
        // closureの定義の仕方は{}
        Closure cls1 = {count ->
            "Hello World[" + count + "]"
        }
        Closure cls2 = {String message ->
            println message
        }
        println cls1(1)
        cls2("closureSample")
        cls2(cls1(2))
        // 入れ子にするのは見づらいので、>>、<<で関数を合成することができる
        Closure mixClosure1 = cls1 >> cls2
        Closure mixClosure2 = cls2 << cls1
        mixClosure1(3)
        mixClosure2.call(4)
        (cls1 >> cls2)(5)
    }

    def doClosureSample2() {
        def clsIns = new ClosureTest("original")
        clsIns.showArguments()

        println "NAME:" + clsIns.showName()

        println "========="
        // closureがdelegateを参照するようにしていれば、
        // そのdelegateを変えることで挙動を変化さえることが可能
        // closure内でdelegateを参照していることが必要
        clsIns.showName.delegate = new ClosureTest("delegate")
        println "NAME:" + clsIns.showName()
    }
    class ClosureTest {
        String name
        def ClosureTest(String name) {
            this.name = name
        }

        Closure showArguments = {
            println "Outer"
            println "this:${this.class.name}"
            println "owner:${owner.class.name}"
            println "delegate:${delegate.class.name}"
            {->
                println "Inner"
                println "this:${this.class.name}"
                println "owner:${owner.class.name}"
                println "delegate:${delegate.class.name}"
            }()
        }

        Closure showName = {->delegate.name}
    }

    def doMapSample() {
        Map map  = [hoge : "123", fuga : 123]
        // 値変更方法1
        println map.hoge
        map["hoge"] = "change"
        println map.hoge
        println "========="
        // 値変更方法2
        println map.fuga
        map.fuga = 987
        println map.fuga
        println "========="
        // エントリー追加＆値変更方法3
        map.put("key", "add")
        println map.key
        map.put("addElem", "addVal")
        println map.addElem
        println "SIZE:" + map.size()
        println "========="
        // ループ
        map.each {entry ->
            println "KEY:${entry.key}, VALUE:${entry.value}"
        }
    }

    def doOptinalSample() {
        String val = null
        Optional opt = Optional.ofNullable(val)
        println toInt(opt)
        opt = Optional.ofNullable("10")
        println toInt(opt)
    }

    Closure toInt = {Optional opt ->
        // メソッド呼び出しする際に、()を省略することが可能
        // opt.map({}).orElse() -> opt.map {}.orElse 
        Integer intVal = opt.map({value ->
            value.toInteger() * 2
        }).orElse(0)
        return intVal
    }

    def doSaveNavigationSample() {
        String val = null
        // ?.でアクセスすることで、nullだった場合に、メソッド呼び出しを行わすに、nullを返却する
        println val?.toInteger()?.class?.name
    }

    class SetterGetterTest {
        String name;
        SetterGetterTest(String name) {
            this.name = name
        }
        def getName() {
            "${name}!!"
        }
        def setName(String name) {
            this.name = name + " bySetter"
        }
    }

    def doSetterGetterSample() {
        def ins = new SetterGetterTest("SetterGetterTest")
        // setter、getterが実装されている場合、直接フィールドアクセスのように実装していても
        // 自動でsetter、getterを利用する
        println ins.name
        ins.name = "changeName"
        println ins.name
    }

    class SetterGetterTest2 {
        private String name
        def SetterGetterTest2(String name) {
            this.name = name
        }

        def getNameEx() {
            this.name
        }
    }
    def doSetterGetterSample2() {
        def ins = new SetterGetterTest2("SetterGetterTest2")
        // privateでも参照できる
        println ins.name
        println ins.getNameEx()
    }
}

def clazz = new SampleClass("test")
println "-------------------"
clazz.showName()
println "-------------------"
clazz.doIfSample(1)
println "-------------------"
clazz.doListCollectSample()
println "-------------------"
clazz.doListInjectSample()
println "-------------------"
clazz.doListFindSample()
println "-------------------"
clazz.doClosureSample()
println "-------------------"
clazz.doClosureSample2()
println "-------------------"
clazz.doMapSample()
println "-------------------"
clazz.doOptinalSample()
println "-------------------"
clazz.doSaveNavigationSample()
println "-------------------"
clazz.doSetterGetterSample()
println "-------------------"
clazz.doSetterGetterSample2()
println "-------------------"
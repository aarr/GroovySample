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
    /*
     * delegateはclosure実行時のコンテキスト
     * この実装ではコンテキストを変えることができていない。。。
     * 一旦保留
     */
    def doClosureSample2() {
        def test = new ClosureTest("original")
        test.showArguments()
        println "========="
        test.exec(test.showName)
        println "========="
        def delegateTest = new ClosureTest("delegate")
        test.showName.delegate = delegateTest;
        test.exec(test.showName)
    }
    class ClosureTest {
        String _name
        ClosureTest(name) {
            this._name = name
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
        Closure exec = {cls ->
            cls()    
        }
        Closure showName = {->
            println this._name
        }
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

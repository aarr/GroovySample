import CategoryTestClass

/**
 * 本クラス実行時には、クラスパスの指定が必要
 * groovy -cp 本ソースの在り処 MetaPrograming.groovy
 * VSCodeのRun Codeでは実行できない。importするCategoryTestClassが見つけられない。
 *
 * 本来存在しないメソッドを追加することが可能（prototypeを追加するイメージ）
 * ２つ方法がある
 * １．metaClassに追加する
 *  -> プログラム実行中は常に利用可能になる。（prototype追加と同じイメージ。影響範囲が大きい）
 * ２．Categoryクラスの利用
 *  -> 利用したい箇所のみ、useブロックを利用して有効にすることが可能。影響範囲を限定できるのでこちらの方が良いと思われる。
 */
class MetaPrograming {
    public static void main(String[] args) {
        String message = "test"
        println message.toString()
        try {
            println message.toStringEx("Call toStringEx")
        } catch (e) {
            println "まだtoStringExは追加されていない"
        }
        addToStringExByMetaClass()
        println message.toStringEx("Call toStringEx")
        println "*" * 30

        try {
            println message.toStringNeo("Call toStringNeo")
        } catch (e) {
            println "toStringNeoは利用できる範囲でない"
        }
        use (CategoryTestClass) {
            println message.toStringNeo("Call toStringNeo")
        }
        println "*" * 30
    }

    static def addToStringExByMetaClass() {
        String.metaClass.toStringEx = {String message -> 
            "Ex:" + message.toString() 
        }
    }
}
// Grabライブラリのダウンロードから設定までを行う。
// 依存関係を記述しつつ、リポジトリも用意する必要がないので、便利。簡単なスクリプトを実装する場合、非常に有用
@Grab(group='org.spockframework', module='spock-core', version='1.0-groovy-2.4')
import spock.lang.*

class FirstSpock extends Specification {

    def "HelloSpock!!"() {
        expect:
        1 == 1
    }

    def "BaySpock!!"() {
        expect:
        1 == 1
    }

    def whenThenTest() {
        // 事前処理
        setup:
        List list = (1..10).toList()
        // 検査
        when:
        list.add(100)
        // 比較
        then:
        list.size == 11
        list.last() == 100
        // whenとthenは必ず一緒に記載される
    }

    def expectTest() {
        // expectはwhen、thenをあわせたようなもの
        // 下に分割した場合の実装を記載
        // また一度検証用の定義がされると、それ以降の検証は動作しない。１メソッド１検証
        expect:
        55 == (1..10).sum()

        when:
        def sum = (1..10).sum()
        then:
        55 == sum
    }

    def cleanupTest() {
        // cleanupはexpectの中で実行時エラーが発生したとしても実行される。finnallyのようなもの
        // 構文エラーなどは全体が実行されない
        setup:
            String message = "test"
        expect:
            aaaa    // undefined
        cleanup:
            println "${message}"
    }

    def whereTest() {
        /// 今回のexpectの検査を、whereで記述した３パターンで実施可能
        expect:
            right + left == result
        
        // 想定結果記載列のみ||で区切る。
        // |でもよいが、慣例として、見やすくするためそのようにする。
        where:
            right|left||result
            1|2||3
            4|5||9
            100|100||201
    }

    @Unroll
    def "unrollWhereTest #leftに#rightを足すと#resultになる"() {
        expect:
            left + right == result

        where:
            right|left||result
            1|2||3
            4|5||10
            10|10||20
            100|100|1
    }

    @Shared
    Date sharedDate = new Date()
    Date normalDate = new Date() 

    def sharedTest() {
        // Sharedアノテーションをつけることで、度のテストでも同じ値を参照することが可能
        // @Unrollを定義しなくても、テストとしては分かれる
        // そのため、Sharedをつけないと、今回のようなテストでは別々の値を取得することとなる。
        // またsetup, cleanupもwhereに記載されて分実施される
        expect:
            sleep(500)
            println "SharedDate:" + sharedDate.getTime()
            println "NormalDate:" + normalDate.getTime()
            a == b
        
        where:
            a|b
            1|1
            2|2
            3|4
    }

    def BDDStyleTest() {
        // setupの別名
        given:"事前準備"
        println "BDD Style Test Start"

        and:"初期値設定"
        num = 1

        when:"10を足したとき"
        num = num + 10

        then:"初期値に10たされた結果となる"
        num == result

        where:
        num|result
        1|11
        10|20
        100|121
    } 

    // フィクスチャーメソッド
    // テスト実施前後に処理を定義可能
    def setupSpec() {
        println "----test all start----"
    }
    def cleanupSpec() {
        println "----test all end----"
    }
    def setup() {
        println "----test unit start----"
    }
    def cleanup() {
        println "----test unit end----"
    }
}
class RegularExpressionTest {

    def testRegularExpression(String _target, String _pattern) {
        // クロージャを記載するときの、パラメータ定義は()で囲うのは構文エラー
        // JavaやJavaScriptとは記載方法が異なる。
        Closure reg = {String target, String pattern ->
        // 文字列から正規表現で一致する内容を抜き出す。groupも利用可能
        (target =~ pattern).each {result -> 
                if (result instanceof List) {
                    // 変数を出力したい場合、ダブルクォーテーションで囲う。
                    // シングルクォーテーションではダメ
                    println "Word : ${result.head()}"
                    println 'Group :'
                    result.tail().eachWithIndex {
                        String group, Integer i ->
                        println " ${i} : ${group}"
                    }
                } else {
                    println result
                }
            }
            println '*' * 30
        }
        reg(_target, _pattern)
    }

    def judegeRegularExpression(_target, _pattern) {
        // ==~：文字列が指定した正規表現と一致するかを確認
        boolean result = (_target ==~ _pattern)
        println "${_target} ==~ ${_pattern} : ${result}"
        println '*' * 30
    }

    def testRegularEx() {
        def texts = [
            "This apple is 300 yen",
            "This Orange IS 150 yen",
            "Is this book 2000 yen? or 1980 yen?",
            "I am koji. yen wo tukau yo",
            "This apple is 3 euro."
        ]

        def withNumbers = texts.findAll {
            String line ->
            (line ==~ /.*[0-9].*/)
        }
        println "withNumbers : ${withNumbers}"
        Integer sum = withNumbers.collect {
            String line ->
            // 正規表現の結果配列を返却
            // 最終行実行結果を結果とするため、returnは記載しなくてもよい
            return (line =~ /[0-9]+/).collect {
                String match ->
                match
            }
        }.flatten().collect {String numStr -> 
            // 型変換
            numStr as Integer 
        }.inject {
            Integer all, Integer next ->
            println "All : ${all}, Next : ${next}"
            all + next
        }
        println "Sum : ${sum}"
        println '*' * 30
    }
}

def ins = new RegularExpressionTest();
ins.testRegularExpression('groovyisgood', /o(.)(.)/)
ins.testRegularExpression("oh!grailsisgod", /.rails/)
ins.testRegularExpression("0c04bf9ec1e7abcc6ba8f53354f22dfc19a2bf90", /(.)\1/)

ins.judegeRegularExpression('abc', /b/)
ins.judegeRegularExpression('abc', /.*b.*/)

ins.testRegularEx()
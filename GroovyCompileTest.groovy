import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@CompileStatic
class GroovyCompileTest {
    public static void main(String[] args) {
        testMethod()
    }

    static def testMethod() {
        println 'Hello'
    }
}
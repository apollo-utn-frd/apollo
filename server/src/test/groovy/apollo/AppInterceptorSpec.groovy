package apollo


import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(AppInterceptor)
class AppInterceptorSpec extends Specification {

    def setup() {
    }

    def cleanup() {

    }

    void "Test app interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"app")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}

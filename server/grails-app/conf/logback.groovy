import grails.util.BuildSettings
import grails.util.Environment

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%level %logger - %msg%n"
    }
}

def targetDir = BuildSettings.TARGET_DIR
if (Environment.isDevelopmentMode() && targetDir != null) {
    appender("FULL_STACKTRACE", FileAppender) {
        file = "${targetDir}/stacktrace.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = "%level %logger - %msg%n"
        }
    }

    /* Apollo */
    logger("apollo", DEBUG, ['STDOUT'], false)

    /* Hibernate SQL */
    // logger("org.hibernate.SQL", DEBUG, ["STDOUT"], false)
    // logger("org.hibernate.type.descriptor.sql.BasicBinder", TRACE, ["STDOUT"], false)

    /* Spring Security */
    // logger("org.springframework.security", DEBUG, ['STDOUT'], false)
    // logger("grails.plugin.springsecurity", DEBUG, ['STDOUT'], false)

    logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)

    logger("org.pac4j", DEBUG, ['STDOUT'], false)
    root(ERROR, ['STDOUT', 'FULL_STACKTRACE'])
}
else {
    root(ERROR, ['STDOUT'])
}

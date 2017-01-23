grails {
    databinding {
        convertEmptyStringsToNull = false
    }

    gorm.default.constraints = {
        coordenada(matches: '^[+-]?[0-9]+\\.[0-9]+$', blank: false)
    }

    plugin {
        springsecurity {
            authority {
                className = 'apollo.Role'
            }

            userLookup {
                userDomainClassName = 'apollo.Usuario'
                authorityJoinClassName = 'apollo.UsuarioRole'
            }

            filterChain {
                chainMap = [
                    [
                        pattern: '/auth/**',
                        filters: 'anonymousAuthenticationFilter,restTokenValidationFilter,restExceptionTranslationFilter,filterInvocationInterceptor'
                    ],
                    [
                        pattern: '/**',
                        filters: 'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
                    ]
                ]
            }

            rest {
                oauth {
                    frontendCallbackUrl = { String tokenValue -> "http://localhost:4200/welcome#${tokenValue}" }

                    google {
                        client = org.pac4j.oauth.client.Google2Client
                        key = '373620657494-ebm7hgktjilhd8jvlqmi6uvfd8q93rs1.apps.googleusercontent.com'
                        secret = 'iVddJvf1OcUG2jBpAP6RTlx3'
                        scope = org.pac4j.oauth.client.Google2Client.Google2Scope.EMAIL_AND_PROFILE
                        //defaultRoles = ['ROLE_USER']
                         defaultRoles = ['ROLE_ADMIN', 'ROLE_USER'] 
                    }
                }

                token {
                    validation {
                        endpointUrl = '/auth/validate'
                        enableAnonymousAccess = true
                    }

                    storage {
                        jwt {
                            secret = 'IG1C9lu45LRb8mdWsaSub6GPCiVDAjDS9lJj5Mp1wiF4jqDjDtZLpS4CHYdeVNm'
                            expiration = 2592000 /* Los tokens expiran luego de un mes. */
                        }
                    }
                }

                login {
                    active = false
                }
            }
        }
    }
}

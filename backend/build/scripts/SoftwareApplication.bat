@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  SoftwareApplication startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and SOFTWARE_APPLICATION_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo. 1>&2
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH. 1>&2
echo. 1>&2
echo Please set the JAVA_HOME variable in your environment to match the 1>&2
echo location of your Java installation. 1>&2

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo. 1>&2
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME% 1>&2
echo. 1>&2
echo Please set the JAVA_HOME variable in your environment to match the 1>&2
echo location of your Java installation. 1>&2

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\SoftwareApplication-0.0.1-SNAPSHOT-plain.jar;%APP_HOME%\lib\spring-boot-starter-web-3.1.0.jar;%APP_HOME%\lib\spring-boot-starter-thymeleaf-3.1.0.jar;%APP_HOME%\lib\spring-boot-starter-data-jpa-3.1.0.jar;%APP_HOME%\lib\spring-boot-starter-security-3.1.0.jar;%APP_HOME%\lib\spring-boot-starter-validation-3.1.0.jar;%APP_HOME%\lib\spring-boot-starter-json-3.1.0.jar;%APP_HOME%\lib\spring-boot-starter-aop-3.1.0.jar;%APP_HOME%\lib\spring-boot-starter-jdbc-3.1.0.jar;%APP_HOME%\lib\spring-boot-starter-3.1.0.jar;%APP_HOME%\lib\springdoc-openapi-starter-webmvc-ui-2.2.0.jar;%APP_HOME%\lib\jjwt-impl-0.12.6.jar;%APP_HOME%\lib\jjwt-jackson-0.12.6.jar;%APP_HOME%\lib\jjwt-api-0.12.6.jar;%APP_HOME%\lib\json-20210307.jar;%APP_HOME%\lib\pkl-codegen-java-0.27.0.jar;%APP_HOME%\lib\spring-boot-starter-tomcat-3.1.0.jar;%APP_HOME%\lib\springdoc-openapi-starter-webmvc-api-2.2.0.jar;%APP_HOME%\lib\spring-webmvc-6.0.9.jar;%APP_HOME%\lib\spring-security-web-6.1.0.jar;%APP_HOME%\lib\spring-web-6.0.9.jar;%APP_HOME%\lib\thymeleaf-spring6-3.1.1.RELEASE.jar;%APP_HOME%\lib\hibernate-core-6.2.2.Final.jar;%APP_HOME%\lib\spring-data-jpa-3.1.0.jar;%APP_HOME%\lib\spring-aspects-6.0.9.jar;%APP_HOME%\lib\springdoc-openapi-starter-common-2.2.0.jar;%APP_HOME%\lib\spring-boot-autoconfigure-3.1.0.jar;%APP_HOME%\lib\spring-boot-3.1.0.jar;%APP_HOME%\lib\spring-boot-starter-logging-3.1.0.jar;%APP_HOME%\lib\jakarta.annotation-api-2.1.1.jar;%APP_HOME%\lib\spring-security-config-6.1.0.jar;%APP_HOME%\lib\spring-security-core-6.1.0.jar;%APP_HOME%\lib\spring-context-6.0.9.jar;%APP_HOME%\lib\spring-aop-6.0.9.jar;%APP_HOME%\lib\spring-orm-6.0.9.jar;%APP_HOME%\lib\spring-jdbc-6.0.9.jar;%APP_HOME%\lib\spring-data-commons-3.1.0.jar;%APP_HOME%\lib\spring-tx-6.0.9.jar;%APP_HOME%\lib\spring-beans-6.0.9.jar;%APP_HOME%\lib\spring-expression-6.0.9.jar;%APP_HOME%\lib\spring-core-6.0.9.jar;%APP_HOME%\lib\swagger-core-jakarta-2.2.15.jar;%APP_HOME%\lib\jackson-datatype-jdk8-2.15.0.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.15.0.jar;%APP_HOME%\lib\jackson-module-parameter-names-2.15.0.jar;%APP_HOME%\lib\jackson-databind-2.15.0.jar;%APP_HOME%\lib\swagger-models-jakarta-2.2.15.jar;%APP_HOME%\lib\jackson-annotations-2.15.0.jar;%APP_HOME%\lib\jackson-core-2.15.0.jar;%APP_HOME%\lib\jackson-dataformat-yaml-2.15.0.jar;%APP_HOME%\lib\snakeyaml-1.33.jar;%APP_HOME%\lib\tomcat-embed-el-10.1.8.jar;%APP_HOME%\lib\hibernate-validator-8.0.0.Final.jar;%APP_HOME%\lib\swagger-ui-5.2.0.jar;%APP_HOME%\lib\mysql-connector-j-8.0.33.jar;%APP_HOME%\lib\pkl-commons-cli-0.27.0.jar;%APP_HOME%\lib\pkl-commons-0.27.0.jar;%APP_HOME%\lib\pkl-core-0.27.0.jar;%APP_HOME%\lib\javapoet-1.13.0.jar;%APP_HOME%\lib\kotlin-stdlib-jdk8-1.8.21.jar;%APP_HOME%\lib\tomcat-embed-websocket-10.1.8.jar;%APP_HOME%\lib\tomcat-embed-core-10.1.8.jar;%APP_HOME%\lib\micrometer-observation-1.11.0.jar;%APP_HOME%\lib\thymeleaf-3.1.1.RELEASE.jar;%APP_HOME%\lib\HikariCP-5.0.1.jar;%APP_HOME%\lib\logback-classic-1.4.7.jar;%APP_HOME%\lib\log4j-to-slf4j-2.20.0.jar;%APP_HOME%\lib\jul-to-slf4j-2.0.7.jar;%APP_HOME%\lib\slf4j-api-2.0.7.jar;%APP_HOME%\lib\aspectjweaver-1.9.19.jar;%APP_HOME%\lib\jakarta.persistence-api-3.1.0.jar;%APP_HOME%\lib\jakarta.transaction-api-2.0.1.jar;%APP_HOME%\lib\jboss-logging-3.5.0.Final.jar;%APP_HOME%\lib\hibernate-commons-annotations-6.0.6.Final.jar;%APP_HOME%\lib\jandex-3.0.5.jar;%APP_HOME%\lib\classmate-1.5.1.jar;%APP_HOME%\lib\byte-buddy-1.14.4.jar;%APP_HOME%\lib\jaxb-runtime-4.0.2.jar;%APP_HOME%\lib\jaxb-core-4.0.2.jar;%APP_HOME%\lib\jakarta.xml.bind-api-4.0.0.jar;%APP_HOME%\lib\jakarta.inject-api-2.0.1.jar;%APP_HOME%\lib\antlr4-runtime-4.10.1.jar;%APP_HOME%\lib\spring-jcl-6.0.9.jar;%APP_HOME%\lib\jakarta.validation-api-3.0.2.jar;%APP_HOME%\lib\antlr4-runtime-4.9.0.jar;%APP_HOME%\lib\msgpack-core-0.9.8.jar;%APP_HOME%\lib\truffle-api-23.0.6.jar;%APP_HOME%\lib\graal-sdk-23.0.6.jar;%APP_HOME%\lib\Paguro-3.10.3.jar;%APP_HOME%\lib\snakeyaml-engine-2.5.jar;%APP_HOME%\lib\kotlin-stdlib-jdk7-1.8.21.jar;%APP_HOME%\lib\kotlin-stdlib-1.8.21.jar;%APP_HOME%\lib\micrometer-commons-1.11.0.jar;%APP_HOME%\lib\attoparser-2.0.6.RELEASE.jar;%APP_HOME%\lib\unbescape-1.1.6.RELEASE.jar;%APP_HOME%\lib\angus-activation-2.0.0.jar;%APP_HOME%\lib\jakarta.activation-api-2.1.2.jar;%APP_HOME%\lib\logback-core-1.4.7.jar;%APP_HOME%\lib\log4j-api-2.20.0.jar;%APP_HOME%\lib\spring-security-crypto-6.1.0.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.8.21.jar;%APP_HOME%\lib\annotations-13.0.jar;%APP_HOME%\lib\clikt-jvm.jar;%APP_HOME%\lib\fastdoubleparser-0.8.0.jar;%APP_HOME%\lib\txw2-4.0.2.jar;%APP_HOME%\lib\istack-commons-runtime-4.1.1.jar;%APP_HOME%\lib\commons-lang3-3.12.0.jar;%APP_HOME%\lib\swagger-annotations-jakarta-2.2.15.jar


@rem Execute SoftwareApplication
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %SOFTWARE_APPLICATION_OPTS%  -classpath "%CLASSPATH%" Software.SoftwareApplication.SoftwareApplication %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable SOFTWARE_APPLICATION_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%SOFTWARE_APPLICATION_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega

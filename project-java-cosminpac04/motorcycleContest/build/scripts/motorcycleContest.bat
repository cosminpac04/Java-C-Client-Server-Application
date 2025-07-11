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
@rem SPDX-License-Identifier: Apache-2.0
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  motorcycleContest startup script for Windows
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

@rem Add default JVM options here. You can also use JAVA_OPTS and MOTORCYCLE_CONTEST_OPTS to pass JVM options to this script.
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

set CLASSPATH=%APP_HOME%\lib\motorcycleContest-1.0-SNAPSHOT.jar;%APP_HOME%\lib\javafx-fxml-21-win.jar;%APP_HOME%\lib\javafx-fxml-21.jar;%APP_HOME%\lib\javafx-web-21-win.jar;%APP_HOME%\lib\javafx-controls-21-win.jar;%APP_HOME%\lib\javafx-controls-21.jar;%APP_HOME%\lib\javafx-media-21-win.jar;%APP_HOME%\lib\javafx-media-21.jar;%APP_HOME%\lib\javafx-swing-21-win.jar;%APP_HOME%\lib\javafx-graphics-21-win.jar;%APP_HOME%\lib\javafx-graphics-21.jar;%APP_HOME%\lib\javafx-base-21-win.jar;%APP_HOME%\lib\javafx-base-21.jar;%APP_HOME%\lib\hibernate-community-dialects-6.4.4.Final.jar;%APP_HOME%\lib\hibernate-core-6.4.4.Final.jar;%APP_HOME%\lib\jboss-logging-3.5.0.Final.jar;%APP_HOME%\lib\hibernate-commons-annotations-6.0.6.Final.jar;%APP_HOME%\lib\jakarta.transaction-api-2.0.1.jar;%APP_HOME%\lib\jakarta.enterprise.cdi-api-4.0.1.jar;%APP_HOME%\lib\jersey-media-json-jackson-3.1.3.jar;%APP_HOME%\lib\jaxb-runtime-4.0.3.jar;%APP_HOME%\lib\jaxb-core-4.0.3.jar;%APP_HOME%\lib\jakarta.xml.bind-api-4.0.0.jar;%APP_HOME%\lib\logback-classic-1.4.12.jar;%APP_HOME%\lib\slf4j-api-2.0.7.jar;%APP_HOME%\lib\controlsfx-11.1.2.jar;%APP_HOME%\lib\jersey-container-grizzly2-http-3.1.3.jar;%APP_HOME%\lib\jersey-container-jdk-http-3.1.3.jar;%APP_HOME%\lib\jersey-server-3.1.3.jar;%APP_HOME%\lib\jersey-hk2-3.1.3.jar;%APP_HOME%\lib\jackson-module-jakarta-xmlbind-annotations-2.15.2.jar;%APP_HOME%\lib\jackson-annotations-2.15.2.jar;%APP_HOME%\lib\jackson-core-2.15.2.jar;%APP_HOME%\lib\jackson-databind-2.15.2.jar;%APP_HOME%\lib\sqlite-jdbc-3.42.0.0.jar;%APP_HOME%\lib\jakarta.persistence-api-3.1.0.jar;%APP_HOME%\lib\jandex-3.1.2.jar;%APP_HOME%\lib\classmate-1.5.1.jar;%APP_HOME%\lib\byte-buddy-1.14.11.jar;%APP_HOME%\lib\jersey-client-3.1.3.jar;%APP_HOME%\lib\jersey-common-3.1.3.jar;%APP_HOME%\lib\jakarta.inject-api-2.0.1.jar;%APP_HOME%\lib\antlr4-runtime-4.13.0.jar;%APP_HOME%\lib\jakarta.enterprise.lang-model-4.0.1.jar;%APP_HOME%\lib\jakarta.annotation-api-2.1.1.jar;%APP_HOME%\lib\jakarta.el-api-5.0.0.jar;%APP_HOME%\lib\jakarta.interceptor-api-2.1.0.jar;%APP_HOME%\lib\angus-activation-2.0.1.jar;%APP_HOME%\lib\jakarta.activation-api-2.1.2.jar;%APP_HOME%\lib\logback-core-1.4.12.jar;%APP_HOME%\lib\jersey-entity-filtering-3.1.3.jar;%APP_HOME%\lib\jakarta.ws.rs-api-3.1.0.jar;%APP_HOME%\lib\jakarta.validation-api-3.0.2.jar;%APP_HOME%\lib\grizzly-http-server-4.0.0.jar;%APP_HOME%\lib\hk2-locator-3.0.4.jar;%APP_HOME%\lib\javassist-3.29.2-GA.jar;%APP_HOME%\lib\txw2-4.0.3.jar;%APP_HOME%\lib\istack-commons-runtime-4.1.2.jar;%APP_HOME%\lib\osgi-resource-locator-1.0.3.jar;%APP_HOME%\lib\grizzly-http-4.0.0.jar;%APP_HOME%\lib\hk2-api-3.0.4.jar;%APP_HOME%\lib\aopalliance-repackaged-3.0.4.jar;%APP_HOME%\lib\hk2-utils-3.0.4.jar;%APP_HOME%\lib\grizzly-framework-4.0.0.jar


@rem Execute motorcycleContest
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %MOTORCYCLE_CONTEST_OPTS%  -classpath "%CLASSPATH%" motorcycle.Main %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable MOTORCYCLE_CONTEST_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%MOTORCYCLE_CONTEST_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega

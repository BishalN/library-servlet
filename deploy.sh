#!/bin/bash

PROJECT_ROOT="$HOME/Desktop/myprojects/7sem/BishalNeupane/LibraryApp"
TOMCAT_WEBAPPS="/opt/homebrew/opt/tomcat@9/libexec/webapps"
SERVLET_JAR="/opt/homebrew/opt/tomcat@9/libexec/lib/servlet-api.jar"
MYSQL_JAR="$PROJECT_ROOT/WebContent/WEB-INF/lib/mysql-connector-java-8.0.33.jar"
WAR_PATH="$HOME/Desktop/myprojects/7sem/LibraryApp.war"

echo "Compiling Java files..."
mkdir -p "$PROJECT_ROOT/WebContent/WEB-INF/classes"
javac -cp "$SERVLET_JAR:$MYSQL_JAR" -d "$PROJECT_ROOT/WebContent/WEB-INF/classes" "$PROJECT_ROOT/src/"*.java

echo "Packaging WAR file..."
cd "$PROJECT_ROOT/WebContent"
jar -cvf "$WAR_PATH" *

cd "$PROJECT_ROOT"

echo "Removing old deployment..."
rm -rf "$TOMCAT_WEBAPPS/LibraryApp"
rm -f "$TOMCAT_WEBAPPS/LibraryApp.war"

echo "Copying new WAR to Tomcat webapps..."
cp "$WAR_PATH" "$TOMCAT_WEBAPPS/"

echo "Restarting Tomcat..."
brew services restart tomcat@9

echo "Deployment complete! Visit http://localhost:8080/LibraryApp/BookServlet"
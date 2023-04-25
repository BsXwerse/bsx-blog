@echo off
call mvn clean install -U -DskipTests
move .\bsx-blog-admin\target\bsx-blog-admin.zip .
move .\bsx-blog-user\target\bsx-blog-user.zip .
pause

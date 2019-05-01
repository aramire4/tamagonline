package util

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.MySQLProfile", "com.mysql.cj.jdbc.Driver", 
    "jdbc:mysql://localhost/tamagonline?user=tamagonline&password=BreakTheLegs&nullNamePatternMatchesAll=true&serverTimezone=UTC", 
    "/users/dakins1/webApps/tamagonline/server/app/", 
    "models", Option("tamagonline"), Option("BreakTheLegs"), true, false
  )
}
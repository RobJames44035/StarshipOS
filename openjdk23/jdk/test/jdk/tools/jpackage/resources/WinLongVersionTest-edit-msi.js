/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

var msi;
if (WScript.Arguments.Count() > 0) {
  msi = WScript.Arguments(0)
} else {
  var shell = new ActiveXObject('WScript.Shell')
  msi = shell.ExpandEnvironmentStrings('%JpMsiFile%')
}

var query = "SELECT `UpgradeCode`, `VersionMin`,`VersionMax`,`Language`,`Attributes`,`Remove`,`ActionProperty` FROM Upgrade WHERE `VersionMax` = NULL"

var installer = new ActiveXObject('WindowsInstaller.Installer');
var database = installer.OpenDatabase(msi, 1)
var view = database.OpenView(query);
view.Execute();

try {
  var record = view.Fetch();
  record.StringData(2) = '2.0.0.3'
  record.IntegerData(5) = 257
  view.Modify(6, record)
  view.Modify(3, record)
  database.Commit();
} finally {
   view.Close();
}

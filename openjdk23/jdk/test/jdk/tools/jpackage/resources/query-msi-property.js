/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */


function readMsi(msiPath, callback) {
    var installer = new ActiveXObject('WindowsInstaller.Installer')
    var database = installer.OpenDatabase(msiPath, 0 /* msiOpenDatabaseModeReadOnly */)

    return callback(database)
}


function queryAllProperties(db) {
    var reply = {}

    var view = db.OpenView("SELECT `Property`, `Value` FROM Property")
    view.Execute()

    try {
        while(true) {
            var record = view.Fetch()
            if (!record) {
                break
            }

            var name = record.StringData(1)
            var value = record.StringData(2)

            reply[name] = value
        }
    } finally {
        view.Close()
    }

    return reply
}


(function () {
    var msi = WScript.arguments(0)
    var propName = WScript.arguments(1)

    var props = readMsi(msi, queryAllProperties)
    WScript.Echo(props[propName])
})()

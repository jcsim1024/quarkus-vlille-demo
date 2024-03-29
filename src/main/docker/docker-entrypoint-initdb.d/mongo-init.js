
db.auth('root', 'example')


db = db.getSiblingDB('vlilledb')

db.createUser({
    user: 'vlille-usr',
    pwd: 'vlille-psswd',
    roles: [
        {
            role: 'dbOwner',
            db: 'vlilledb',
        },
    ],
});

db.createCollection('vlillecol');

db.vlillecol.insertMany([
    {"records":{"datasetid":"vlille-realtime","recordid":"3f443706e549c832710c84005d00723d5a476544","fields":{"nbvelosdispo":4,"nbplacesdispo":20,"libelle":43,"adresse":"3 PLACE SEBASTOPOL","nom":"THEATRE SEBASTOPOL","etat":"EN SERVICE","commune":"LILLE","etatconnexion":"CONNECTED","type":"AVEC TPE","geo":[50.629044,3.058288],"localisation":[50.629044,3.058288],"datemiseajour":"2022-05-25T23:42:21+00:00"},"geometry":{"type":"Point","coordinates":[3.058288,50.629044]},"record_timestamp":"2022-05-25T23:48:04.106Z"},"retrivalTime":{"dayOfMonth":25,"dayOfWeek":"WEDNESDAY","dayOfYear":145,"hour":23,"minute":48,"month":"MAY","monthValue":5,"nano":303769830,"offset":{"rules":{"fixedOffset":true,"transitionRules":[],"transitions":[]},"totalSeconds":0},"second":11,"year":2022}}
    ,{"records":{"datasetid":"vlille-realtime","recordid":"6f55bf2493bbf51f0490ee50987490d05135c4e6","fields":{"nbvelosdispo":2,"nbplacesdispo":16,"libelle":66,"adresse":"35 RUE EDOUARD DELESALLE","nom":"DELESALLE MEDIATHEQUE","etat":"EN SERVICE","commune":"LILLE","etatconnexion":"CONNECTED","type":"AVEC TPE","geo":[50.63237,3.066273],"localisation":[50.63237,3.066273],"datemiseajour":"2022-05-25T23:42:21+00:00"},"geometry":{"type":"Point","coordinates":[3.066273,50.63237]},"record_timestamp":"2022-05-25T23:48:04.106Z"},"retrivalTime":{"dayOfMonth":25,"dayOfWeek":"WEDNESDAY","dayOfYear":145,"hour":23,"minute":48,"month":"MAY","monthValue":5,"nano":303769830,"offset":{"rules":{"fixedOffset":true,"transitionRules":[],"transitions":[]},"totalSeconds":0},"second":11,"year":2022}}
]);

db = db.getSiblingDB('admin')

db.grantRolesToUser("root", [{role:"dbOwner",db:"vlilledb"}])

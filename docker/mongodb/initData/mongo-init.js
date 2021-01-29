db = db.getSiblingDB('investments-portfolio');
db.createUser(
    {
        user: 'app-investments-portfolio',
        pwd: 'DwY4(;8A#qpVR%\'U',
        roles: [
            {
                role: 'readWrite', db: 'investments-portfolio'
            }
        ]
    }
);

db = db.getSiblingDB('investments-portfolio-dev');
db.createUser(
    {
        user: 'app-dev-investments-portfolio',
        pwd: '2$We(E=9g?D2S2tC',
        roles: [
            {
                role: 'readWrite', db: 'investments-portfolio-dev'
            }
        ]
    }
);


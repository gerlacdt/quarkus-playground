1. install postgresql
> sudo apt-get install postgresql -y

2. login and set password for postres user
> sudo -u postgres psql
> \password
> \q

3. Change security settings, so user can login with password (default
only system user works), set Method to "md5" everywhere
> sudo emacs -q -nw /etc/postgresql/11/main/pg_hba.conf

4. create user (if not existing)
> psql -U postgres -f create-user.sql

5. create db with permissions (creates mydb and mydb_test)
> psql -U postgres -f create-db.sql

5. create tables
see flyway

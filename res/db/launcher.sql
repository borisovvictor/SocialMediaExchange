connect 'localhost:C:\Dropbox\!study\spbstu\sem10\software_arch\SocialMediaExchange\res\db\EXCHANGEDB.FDB'  user 'SYSDBA' password 'masterkey';
drop database;
commit;

create database 'localhost:C:\Dropbox\!study\spbstu\sem10\software_arch\SocialMediaExchange\res\db\EXCHANGEDB.fdb' user 'SYSDBA' password 'masterkey';
commit;

connect 'localhost:C:\Dropbox\!study\spbstu\sem10\software_arch\SocialMediaExchange\res\db\EXCHANGEDB.fdb'  user 'SYSDBA' password 'masterkey';

input "C:\Dropbox\!study\spbstu\sem10\software_arch\SocialMediaExchange\res\db\db_create.sql";
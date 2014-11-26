# ece356

### Setup Instructions:
1. Ensure mysql-server's credentials are a follows:
  * **Username**: `root`
  * **Password**: `root`
2. Change directories to `hospital/sql/`
3. Create tables:
  * Run shell command: `cat create_schemas.sql | mysql -u root -proot`
4. Insert dummy data (one staff, username `staff1`, password `staff1`):
  * Run shell command: `cat insert_dummy_data.sql | mysql -u root -proot`
5. Create stored procedures:
  * Run shell command: `cat SP_*.sql | mysql -u root -proot`
6. Create triggers:
  * Run shell command: `cat TR_AfterInsertVisitSchema.sql | mysql -u root -proot`
  * Run shell command: `cat TR_AfterUpdateVisitSchema.sql | mysql -u root -proot`
  * Run shell command: `cat TR_BeforeDeleteVisitSchema.sql | mysql -u root -proot`



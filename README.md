# SQLAPI - Create queries faster and more securely!

In this API you have access to several ready-to-use sql drivers, where you can create queries in a simple way and not worry about compatibility issues.

### Motivation
This API was actually based on another one (which for some reason was removed from github), which was more limited and had connection pooling and usability issues.

Also, I wanted an API to remove the need to have to rewrite all the database connection code every time I create a new project.

## Get started
This API has many methods that can be found on the [Wiki](https://github.com/SrBalbucio/SQLAPI/wiki).

Quick Code (sqlite):
```java
SqliteConfig config = new SqliteConfig(File: databaseFile);
ISQL instance = new SQLiteInstance(SqliteConfig: config);
// instance.createTable(String: tableName, String: columns);
instance.createTable("Produtos", "name VARCHAR(255), price DOUBLE");
// instance.insert(String: columns, String: values, String: tableName);
instance.insert("name, price", "'Smartphone Salung 5', '5200'", "Produtos");
```
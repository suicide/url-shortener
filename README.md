# URL shortener

## Description

The app creates a short url for a given long url. The urls are stored in a mysql database.

The long url is md5 hashed and afterwards base62 encoded to provide a nice looking hash.
The base62 encoded string is afterwards cut off to 5+ letters. In case of hash collisions
the hash string is extended by addition chars of the original long encoded hash.


## Build

Build with java8 and gradle:

```bash
./gradlew clean build
```

## Start

The app needs a running mysql instance. A VagrantFile is provided that starts a ubuntu and
installs a mysql server:

```bash
vagrant up
```

See `create.sql` for the database table being used.

To start the actual app you either start the Main class `my.test.shortener.Main` or run:
 
```bash
./build/libs/url-shortener-1.0-SNAPSHOT.jar
```

## Testing / Usage

create short url:
```bash
curl -v  -X POST -d "http://example.com/" http://localhost:8080/api/v1
```
the retrieval url is emitted in the `Location` response header.

retrieve long url:
```bash
curl http://localhost:8080/api/v1/abcdefg -I
```


# URL shortener

## Description

## Build

## Start

## Testing

create short url:
```
curl -v  -X POST -d "http://google.com/" -H "Content-Type: text/plain" http://localhost:8080/api/v1
```

retrieve long url:
```
curl http://localhost:8080/api/v1/abcdefg -I
```


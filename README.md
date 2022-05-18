# Rest endpoints
## Users
- Path: `/users` (GET)
- Response example:
```
[
    {
        "username": "fdigiorgio",
        "fullName": "Francisco Di Giorgio",
        "mail": "fdigiorgio@frba.utn.edu.ar"
    }
]
```

- Path: `/users/private` (GET)
- Response example:
```
{
    "username": "fdigiorgio",
    "fullName": "Francisco Di Giorgio",
    "mail": "fdigiorgio@frba.utn.edu.ar"
}
```

- Path: `/users/login` (POST)
- Request example:
```
{
    "userName": "fakeUsername",
    "password": "fakePassword"
}
```
- Response example:
```
{
    "token": "fakeToken"
}
```

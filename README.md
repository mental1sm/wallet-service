```agsl
$ git clone https://github.com/mental1ism/wallet-service.git
```
```agsl
$ cd wallet-service
```
```agsl
$ git checkout homework4
```
```agsl
$ mvn clean package
```
```agsl
Copy .war file from ./target to ./docker
```
```agsl
$ cd docker
```
```agsl
$ docker-compose up
```
```agsl
Don't forget to copy your client secret and puplic rsa key to application.yml
```
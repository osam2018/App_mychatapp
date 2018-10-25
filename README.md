# MyChatApp

Socket.io를 활용한 모바일 & 웹 채팅 애플리케이션.

### 서버 (`_server/`)

```
$ cd _server
$ npm start
```

* 앱 또는 브라우저를 이용해 접속
* 앱 접속 전 `ChatApplication.java` 파일 IO.socket URL을 호스트 주소(포트 3000)로 변경

## 알려진 버그

* 일부 이벤트가 안드로이드로 emit되지 않는 현상

* 앱에서 메모 액티비티를 켰다가 메인 액티비티로 돌아가면 새 소켓이 연결되는 현상

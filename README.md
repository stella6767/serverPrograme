# JSP 서버프로그램 구현 포트폴리오

## 환경

- windows10
- jdk1.8
- tomcat9.0
- sts tool
- mysql8.0
- postman
- lombok
- 인코딩 utf-8
- git

##요구사항

- User 테이블을 생성한다. (id, username, password, email, role)
- MVC 서버를 구축한다. (userList.jsp, login.jsp, join.jsp 필요)
- (role 종류 = user, admin)
- 회원가입, 로그인 기능을 포함한다.
- 일반 user가 로그인하면 자신의 정보만 삭제할 수 있다.
- 관리자 admin으로 로그인하면 모든 정보를 삭제할 수 있다.
- 부트스트랩을 이용하여 디자인한다.


##평가요소

- Apache Tomcat WAS를 설치하고 웹 포트번호는 8000로 설정되었다.
- WAS의 utf-8 설정을 위한 필터가 적용되었다.
- WAS의 세션 타임아웃 시간이 30분으로 설정되었다.
- model 패키지에 beans 클래스가 작성되었다.
- DAO(Data Access Object) 클래스가 작성되었다.
- DAO 클래스에서 Connection이 완료된 객체를 종료시켰다.
- Injection 공격에 대비되었다.
- 세션을 이용한 로그인 기반 서비스가 구현되었다.
- 관리자 화면에서 모든 게시물을 삭제할 수 있는 권한을 부여하였다.
- JDBC를 이용한 데이터 처리가 가능하다.



## MySQL 데이터베이스 생성 및 사용자 생성

```sql
create user 'kang'@'%' identified by 'bitc5600';
GRANT ALL PRIVILEGES ON *.* TO 'kang'@'%';
create database kang;
```

## MySQL 테이블 생성

- bloguser 사용자로 접속
- use blog; 데이터 베이스 선택

```sql
use kang;

CREATE TABLE user(
    id int primary key auto_increment,
    username varchar(100) not null unique,
    password varchar(100) not null,
    email varchar(100) not null,
    userRole varchar(20),
    createDate timestamp
) engine=InnoDB default charset=utf8;

insert into user(username,password,email,userRole,createDate) values('admin','1234','admin@gmail.com','admin',now());
```

##DB 설정

``` web.xml
	<description>MySQL Test App</description>
	<resource-ref>
		<description>DB Connection</description>
		<res-ref-name>jdbc/TestDB</res-ref-name>
		<res-type>javax.sql.DataSource</res-type>
		<res-auth>Container</res-auth>
	</resource-ref>
```

```context.xml

<Context>
	<Resource name="jdbc/TestDB" auth="Container"
		type="javax.sql.DataSource" maxTotal="100" maxIdle="30"
		maxWaitMillis="10000" username="kang" password="bitc5600"
		driverClassName="com.mysql.cj.jdbc.Driver"
		url="jdbc:mysql://localhost:3306/kang?serverTimezone=Asia/Seoul" />
</Context> 

```

##필터 설정

```스크립트 방어, 인코딩 설정
	<filter>
		<filter-name>charConfig</filter-name>
		<filter-class>com.cos.kang.config.CharConfig</filter-class>
	</filter>
	
	<filter>
		<filter-name>xssEscapeServletFilter</filter-name>
		<filter-class>com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter</filter-class>
	</filter>
	


	<filter-mapping>
		<filter-name>charConfig</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>

	<filter-mapping>
		<filter-name>xssEscapeServletFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	
```

## index 페이지
```
<%

/* 	RequestDispatcher dis = request.getRequestDispatcher("user?cmd=list&page=0");
	dis.forward(request, response); */
	/* 디스패처 만들면 안 통하는 이유? */
			
/* 	디스패처든 sendREdirect이든 식별자를 찾을 수 있음. 단지 필터를 타냐 안 타냐의 차이일뿐.
	근데 이건 왜 안 되지....*/
	
	response.sendRedirect("/kang/user?cmd=userList&page=0");
%>

```





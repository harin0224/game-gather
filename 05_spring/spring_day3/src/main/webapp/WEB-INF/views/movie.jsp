<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<table border="1">
    <thead>
        <tr>
            <th>항목명</th>
            <th>값</th>
        </tr>
    </thead>
    <tbody>
        <tr style="background: lime">
         
        <%-- ${출력하고속성명} : 세션, 모델만 출력 가능 --%>
            <td>영화제목</td>
            <td>영화가격</td>
        </tr>
        <!-- 모델속성값 꺼내서 프린트 -->
        <tr style="background: pink">
            <td>${vo.title}</td>
            <td>${vo.price}</td>
        </tr>

    </tbody>
</table>
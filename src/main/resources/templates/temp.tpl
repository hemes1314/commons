<h3 style="margin:0px;" th:utext="${title}"></h3>
<div th:each="imageData,userStat:${images}">
    <h4 style="margin:0px;" th:utext="${imageData.name}"></h4>
    <img style="display:block;width:100%;" th:src="${imageData.path}"/><!--style="display:block;margin-bottom:5px;"-->
</div>
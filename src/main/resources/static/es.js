$(document).ready(
    function () {
    $("#search").on("click", function () {
        getData();
    });
})

function render(data) {
    console.log(data)
    $("#results").empty();
    $("#total").text(data.length);
    if(data.length > 0){
        let results = "";
        for(let i = 0; i < data.length; i++){
            results = results + "<div><span>"+(i+1)+".</span><div style='display: contents;'>HeadLine:"+ data[i].headLine+"["+data[i].id+"]</div>"
            + "<div>DateLine:" + data[i].dateLine + "</div>"
            +"<div>Text:" + data[i].content +"</div></div>"
        }

        $("#results").append($(results));
        $("#results").parent().show();
    }
}

function getData() {
    $(".content").show();
    $.get({
        url:"http://127.0.0.1:8080/article/search?queryText=" + $("#searchText").val().trim(),
        contentType:'application/json',
        async:false,
        success:function (data) {
            render(data)
        }
    });
}






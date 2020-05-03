//$(document).ready(function(){
//	alert("abc");
//});

$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
	// 서버로 데이터를 전송하는 기본 기능을 막는다.
	e.preventDefault();

	var queryString = $(".answer-write").serialize();
	console.log(queryString);

	var url = $(".answer-write").attr("action");
	console.log(url);

	$.ajax({
		type : 'post',
		url : url,
		// 기본적으로 getter메소드만 json 데이터로 바뀌도록 설정된다.
		// json의 기본 라이브러리 jackson의 형식이 이러하다. 따라서 jackson 라이브러리를 활용하던지 getter를
		// 생성한다..
		data : queryString,
		dataType : 'json',
		error : onError,
		success : onSuccess
	});
}

function onError() {

}

function onSuccess(data, status) {
	console.log(data);
	var answerTemplate = $("#answerTemplate").html();
	var template = answerTemplate.format(data.writer.userId,
			data.formattedCreateDate, data.contents, data.question.id, data.id);
	$(".qna-comment-slipp-articles").prepend(template);

	$("textarea[name=contents]").val("");
}

$(document).on('click', '.link-delete-article2', deleteAnswer);

function deleteAnswer(e) {
	//사이트가 다른 페이지로 이동하지 못하도록 설정한다.
	e.preventDefault();

	//"삭제"를 클릭 했을 때
	var deleteBtn = $(this);
	var url = deleteBtn.attr("href");
	console.log(url);

	$.ajax({
		type : 'delete',
		url : url,
		dataType : 'json',
		error : function(xhr, status) {
			console.log("error");
		},
		success : function(data, status) {
			console.log(data);
			if (data.valid) {
				deleteBtn.closest("article").remove();
			} else {
				alert(data.errorMessage);
			}
		}
	});
}

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};
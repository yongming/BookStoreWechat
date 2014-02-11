$(document).ready(function(){
	loadLock=false;
	curPage=1;
	getDataAfter(true);
	getData();
	$("#laodmore").click(getData);
});
var getData=function(){
	if(loadLock) return;
	getDataBefore();
	$.getJSON("/book/search_ajax/"+keyword+"&"+curPage+"?wechatId="+wechatId,function(result){
		if(typeof result.ret === 'undefined' || result.ret!=0){
			alert("读取数据错误。请重试");
			getDataAfter(false);
			return;
		}
		$("#count").text(result.resultTotal);
		for(var i=0; i<result.data.length; i++){
			var book=result.data[i];
			var str=[];
			str.push("<div class='sing' data-isbn='"+book.isbn+"'>");
			str.push("<div class='intro'><strong>"+book.title+"</strong>");
			str.push("<i>作者: "+book.author+"</i><span>出版: "+book.publisher+"</span></div>");
			str.push("<div class='pic'><img src='"+book.mediumImages+"'/></div></div>");
			$("#datalist").append(str.join(''));
		}
		curPage++;
		getDataAfter(result.more);
	});
};
var getDataBefore=function(){
	loadLock=true;
	$("#laodmore>img").show();
	$("#laodmore>span").text('正在加载...');
};
var getDataAfter=function(hasMore){
	$("#laodmore>img").hide()
	$("#laodmore>span").text(hasMore?'加载更多':"没有更多了");
	loadLock=!hasMore;
	$(".sing").click(function(){
		window.location.href='/book/isbn/'+$(this).attr("data-isbn")+"?wechatId="+wechatId;
	});
};
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
	$.getJSON("/store/list_ajax?lat="+local_lat+"&lon="+local_lon+"&page="+curPage+"&wechatId="+wechatId,function(result){
		if(typeof result.ret === 'undefined' || result.ret!=0){
			alert("读取数据错误。请重试");
			getDataAfter(false);
			return;
		}
		for(var i=0; i<result.data.length; i++){
			var store=result.data[i];
			var str=[];
			str.push("<div class='sing' data-storeid='"+store.storeId+"'>");
			str.push("<div class='intro'><strong>"+store.storeName+"</strong>");
			str.push("<i>"+store.storePlace+"</i><span>与您相距: "+store.storeDist+"</span></div>");
			str.push("<div class='pic'><img src='"+store.storePicture+"'/></div></div>");
			$("#datalist").append(str.join(''));
		}
		getDataAfter(result.more);
		curPage++;
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
		window.location.href='/store/detail/'+$(this).attr("data-storeid")+"?wechatId="+wechatId;
	});
};
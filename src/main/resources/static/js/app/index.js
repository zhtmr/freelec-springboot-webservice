// 여러 사람이 참여하는 프로젝트에서는 중복된 함수 이름이 자주 발생할 수 있음
// index.js 만의 유효범위(scope)를 만들어 사용함.
var index = {
    init : function(){
        var _this = this;
        $('#btn-save').on('click',function(){
            _this.save();
        });
    },
    save : function(){
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
};

index.init();
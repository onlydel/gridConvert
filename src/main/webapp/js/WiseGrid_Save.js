/**
 * Created by BONG on 2015-7월-22일 /022.
 */
function init() {
    var GridObj = document.WiseGrid;
    setHeader(GridObj);
    setProperty(GridObj);

    GridObj.strBgImage = "/images/unipost_logo_white.gif";
}

function setHeader(GridObj) {

    //그리드에 컬럼을 등록한다.
    GridObj.AddHeader("CRUD",			"구분",			"t_text", 		8, 		40,		true);
    GridObj.AddHeader("ITEM_FLAG", 		"자재구분",		"t_combo", 		10, 	90,		true);
    GridObj.AddHeader("VENDOR_NAME", 	"제조회사",		"t_text", 		50, 	80,		true);
    GridObj.AddHeader("ITEM_CODE", 		"제품코드",		"t_imagetext", 	20, 	100,	true);
    GridObj.AddHeader("ITEM_NAME", 		"제품명", 		"t_text", 		500, 	150,	true);
    GridObj.AddHeader("SPECIFICATION", 	"규격", 			"t_text", 		2000, 	200,	true);
    GridObj.AddHeader("UNIT", 			"단위",			"t_combo",		10, 	50,		true);
    GridObj.AddHeader("PRICE", 			"출고가", 		"t_number", 	22.3, 	80,		true);
    GridObj.AddHeader("STOCK",			"재고량", 		"t_number", 	22,		60,		true);
    GridObj.AddHeader("ADD_DATE",		"등록일", 		"t_date", 		8,		85,		true);
    GridObj.AddHeader("CHANGE_DATE",	"수정일", 		"t_date", 		8,		85,		true);
    GridObj.AddHeader("SEQ_NO",			"SEQ_NO", 		"t_text", 		8,		85,		true);

    //AddHeader를 완료한 후 헤더를 그리드에 바인딩한다
    GridObj.BoundHeader();

    //저장모드를 사용해 서버사이드와 통신한다.
    GridObj.SetCRUDMode("CRUD", "생성", "수정", "삭제");

    GridObj.SetColHide("SEQ_NO", true);

    GridObj.AddComboListValue("ITEM_FLAG", "프로세서", "CPU");
    GridObj.AddComboListValue("ITEM_FLAG", "메모리", "MEM");
    GridObj.AddComboListValue("ITEM_FLAG", "메인보드", "MAB");
    GridObj.AddComboListValue("ITEM_FLAG", "그래픽카드", "VID");
    GridObj.AddComboListValue("ITEM_FLAG", "모니터", "MOR");
    GridObj.AddComboListValue("ITEM_FLAG", "하드디스크", "HDD");
    GridObj.AddComboListValue("ITEM_FLAG", "CDROM", "CDR");
    GridObj.AddComboListValue("ITEM_FLAG", "키보드", "KEY");
    GridObj.AddComboListValue("ITEM_FLAG", "마우스", "MOU");

    GridObj.AddComboListValue("UNIT", "EA", "EA");

    GridObj.AddImageList("ITEM_CODE", "/images/bt_search.gif");
    GridObj.SetColCellFgColor("ITEM_CODE", "0|0|255");
    GridObj.bNullValueNumberFormat=true;
    GridObj.SetNumberFormat("PRICE", "#,##0.00");
    GridObj.SetNumberFormat("STOCK", "#,##0");
    GridObj.SetDateFormat("ADD_DATE", "yyyy/MM/dd");
    GridObj.SetDateFormat("CHANGE_DATE", "yyyy/MM/dd");

}

/*  일반조회 */
function doQuery() {
    var GridObj = document.WiseGrid;
    var servlet_url = "/servlet/wisegrid.sample.ExampleSave";

    GridObj.SetParam("mode", "search");
    GridObj.SetParam("from_date", $('#from_date').val());
    GridObj.SetParam("to_date", $('#to_date').val());
    GridObj.SetParam("item_flag", $('#item_flag').val())
    GridObj.SetParam("item_name", $('#item_name').val())
    GridObj.DoQuery(servlet_url);
}

/* 삭제 */
function doDelete() {
    var GridObj = document.WiseGrid;
    GridObj.DeleteRow(GridObj.GetActiveRowIndex());
}

/* 행추가 */
function doLineInsert() {
    var GridObj = document.WiseGrid;

    GridObj.AddRow();
    GridObj.SetCellValue('PRICE', GridObj.GetActiveRowIndex(), 0);
    GridObj.SetCellValue('STOCK', GridObj.GetActiveRowIndex(), 0);
    GridObj.SetCellImage('ITEM_CODE', GridObj.GetActiveRowIndex(), 0);
    GridObj.SetCellActivation("ITEM_CODE", GridObj.GetActiveRowIndex(), "edit");
}

/* 저장 */
function doSave() {
    var GridObj = document.WiseGrid;
    var servlet_url = "/servlet/wisegrid.sample.ExampleSave";

    GridObj.SetParam("mode", "save");
    GridObj.DoQuery(servlet_url, "CRUD");
}

/* 저장모드에서 저장 플래그를 모두 삭제하고 초기 데이터로 롤백한다. */
function doSaveCancel() {
    var GridObj = document.WiseGrid;

    if(confirm("저장 플래그를 모두 초기화 합니다"))
        GridObj.CancelCRUD();
}

/* 서버와의 통신이 정상적으로 완료되면 발생한다. */
function GridEndQuery() {
    var GridObj = document.WiseGrid;
    var mode = GridObj.GetParam("mode");

    if(mode == "search") {
        if(GridObj.GetStatus() == "true") 	{
            document.all.message.style.display = "none";
            document.form.confirm.value = null;
        } else	{
            var error_msg = GridObj.GetMessage();
            alert(error_msg);
        }
    }

    if(mode == "save") {
        if(GridObj.GetStatus() == "true") 	{
            var saveData = GridObj.GetParam("saveData");
            document.all.message.style.display="";
            document.form.confirm.value = saveData;
        } else	{
            var error_msg = GridObj.GetMessage();
            alert(error_msg);
        }
    }
}

function characterCheck($el, e) {

    var inputStr = $el.val();

    if (/^[0-9]*$/.test(inputStr) == false) {
        alert('숫자만 입력이 가능합니다.');
        $el.val('');
        e.preventDefault();
        $el.focus();
    }
}

function lenthCheck($el, e) {

    var inputStr = $el.val();
    var textLength = inputStr.length;

    if (textLength > 8) {
        alert('8자리까지 입력이 가능합니다.');
        $el.val('');
        e.preventDefault();
        $el.focus();
        return;
    }
}

function dateCheck(date) {

    var msg = '';

    if(date.length < 8) {
        msg = '날짜는 8자리까지 입력이 가능합니다. (예)20150701'
        return msg;
    }

    var year = date.substr(0, 4);
    var month = date.substr(4, 2);
    var day = date.substr(6, 2);

    if(parseFloat(year) < 2000 && parseFloat(year) <= 4000) {
        msg = '년도 입력에 적합하지 않습니다.';
        return msg;
    }

    if(parseFloat(month) > 12 || parseFloat(month) <= 0) {
        msg = '월 입력에 적합하지 않습니다.';
        return msg;
    }

    var tempDate = new Date(year, month, 0);
    var endDay = tempDate.getDate();


    if(!(0 < parseFloat(day) && parseFloat(day) <= endDay)) {
        msg = '일자가  존재하지 않습니다.';
        return msg;
    }

    return msg;
}

$(function() {

    $('#from_date').on('keypress, keydown, keyup', function (e) {
        lenthCheck($(this), e)
        characterCheck($(this), e);
    });

    $('#to_date').on('keypress, keydown, keyup', function (e) {
        lenthCheck($(this), e)
        characterCheck($(this), e);
    });

    $('#from_date').on('blur', function() {
        var date = $(this).val();
        if(date.length !== 0) {
            var msg = dateCheck(date);
            if(msg !== '') {
                alert(msg);
                $(this).val('').focus();
            }
        }
    });

    $('#to_date').on('blur', function() {
        var date = $(this).val();
        if(date.length !== 0) {
            var msg = dateCheck(date);
            if(msg !== '') {
                alert(msg);
                $(this).val('').focus();
            }
        }
    });
});
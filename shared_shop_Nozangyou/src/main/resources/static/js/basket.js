/**
 *	かいものかご内の注文個数の変更用Javascript
 *	変更は表示のみ
 *	注文登録用コントローラーに送る個数データは、フォーム内のhiddenタイプ要素のvalueに格納
 *
 *	@param count	注文個数を表示している<td>要素
 *	 →@param num	表示されている注文個数
 *	@param name		注文個数を表示している<td>要素のid
 *	@param lastNum	nameから1文字目を取り除いた文字列 = 商品のアイテムid
 *	@param targetId1	注文登録用の注文個数の送信データを格納している要素<input type="hidden">のid
 *	@param targetId2	商品の在庫が格納されている要素<input type="hidden">（テーブル内）のid
 *	@param elm1		注文登録用の注文個数の送信データを格納している要素
 *	@param elm2		商品の在庫が格納されている要素
 *	@param num2		注文登録用の注文個数の送信データ
 *	@param num3		商品の在庫
 */
function addNum(count) {
	var num = count.innerText;
	var name = count.id;
	var lastNum = name.substr(1);
	var targetId1 = "b" + lastNum;
	var targetId2 = "c" + lastNum;
	var elm1 = document.getElementById(targetId1);
	var elm2 = document.getElementById(targetId2);
    var num2 = Number(elm1.value);
    var num3 = Number(elm2.value);
    num = Number(num);
    if (num3 > num) {
        num++;
	    num2++;
    }

	count.innerText = num;
    elm1.value = num2;
}

function subtractNum(count) {
	var num = count.innerText;
	var name = count.id;
	var lastNum = name.substr(1);
	var targetId = "b" + lastNum;
	var elm = document.getElementById(targetId);
    var num2 = Number(elm.value);
    num = Number(num);
    if (num > 1) {
        num--;
	    num2--;
    }

	count.innerText = num;
    elm.value = num2;
}

/**
 * 買い物かごのページリンク用
 * @author 伊藤
 */
function pageLink(page) {
	var form = document.getElementById('setOrderNum');
	var url = 'http://localhost:' + location.port + '/shared_shop/basket/index/' + page.value;
	form.action = url;
}
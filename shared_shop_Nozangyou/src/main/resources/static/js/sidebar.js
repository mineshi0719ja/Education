/**
 * 同時検索のvalidator
 * @author 伊藤
 */
function checkSelectedNumber(){
	var checkedMethod = Number(0);
	var category = document.getElementById('category');
	var price = document.getElementById('price');
	var keyword = document.getElementById('keyword');
	var alertMessage = '検索条件は<br />2つ以上選択してください';

	if(category.checked){
		checkedMethod++;
	}
	if(price.checked){
		checkedMethod++;
		var priceMin = document.getElementById('priceMin').value;
		var priceMax = document.getElementById('priceMax').value;
		if(priceMin == 0 && priceMax == 0){
			alertMessage = '価格帯をチェックした際には<br />検索条件の指定が必要です';
			document.getElementById('searchAlert').innerHTML = alertMessage;
			return false;
		}else if(priceMin < 0 || priceMax < 0){
			alertMessage = '負の値は指定できません';
			document.getElementById('searchAlert').innerHTML = alertMessage;
			return false;
		}
	}
	if(keyword.checked){
		checkedMethod++;
	}

	if(checkedMethod < 2){
		document.getElementById('searchAlert').innerHTML = alertMessage;
		return false;
	}

	return true;
}
function payChange(){
	radio = document.getElementsByName('payMethod');
	if(radio[0].checked) {
		document.getElementById('card').style.display = "block";
	}else {
		document.getElementById('card').style.display = "none";
	}
}
window.onload = payChange;
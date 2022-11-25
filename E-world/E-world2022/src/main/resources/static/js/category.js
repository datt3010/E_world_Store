function readURL(input) {
  if (input.files && input.files[0]) {
    var reader = new FileReader();

    reader.onload = function (e) {
      $('#image').attr('src', e.target.result).width(200).height(200);
    };

    reader.readAsDataURL(input.files[0]);
  }
}

function hide() {
    var p = document.getElementById('val-password');
    p.setAttribute('type', 'text');
}

function show() {
    var p = document.getElementById('val-password');
    p.setAttribute('type', 'password');
}

var pwShown = 0;

document.getElementById("eye").addEventListener("click", function () {
    if (pwShown == 0) {
        pwShown = 1;	
				document.getElementById("eye").classList.add("fa-eye-slash");
				document.getElementById("eye").classList.remove("fa-eye");
        show();
    } else {
        pwShown = 0;
			  document.getElementById("eye").classList.add("fa-eye");
				document.getElementById("eye").classList.remove("fa-eye-slash");
        hide();
    }
}, false);
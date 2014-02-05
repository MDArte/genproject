$(document).ready(function(){
	$('#resolucaoDetalhaResolucaoUC').replaceWith('<textarea id="code">' + $('#resolucaoDetalhaResolucaoUC').html() + '</textarea>');
	var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
		lineNumbers: true,
		extraKeys: {"Ctrl-Space": "autocomplete"}
	}); 
});

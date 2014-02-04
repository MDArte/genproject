$(document).ready(function(){
	var editor = CodeMirror.fromTextArea(document.getElementById("resolucaoAdicionaResolucaoUC"), {
		lineNumbers: true,
		extraKeys: {"Ctrl-Space": "autocomplete"}
	}); 
});
